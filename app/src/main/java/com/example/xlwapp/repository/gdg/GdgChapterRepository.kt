package com.example.xlwapp.repository.gdg

import android.location.Location
import com.example.xlwapp.network.gdg.*
import kotlinx.coroutines.*

class GdgChapterRepository(gdgApiService: NewGdgApiService){
    /**
     * A single network request, the results won't change. For this lesson we did not add an offline cache for simplicity
     * and the result will be cached in memory.
     */
    private val request = gdgApiService.getChapters(true)

    /**
     * An in-progress (or potentially completed) sort, this may be null or cancelled at any time.
     *
     * If this is non-null, calling await will get the result of the last sorting request.
     *
     * This will be cancelled whenever location changes, as the old results are no longer valid.
     */
    private var inProgressSort: Deferred<SortedData>? = null

    var isFullyInitialized = false
        private set


    /**
     * Get the chapters list for a specified filter.
     *
     * This will be cancel if a new location is sent before the result is available.
     *
     * This works by first waiting for any previously in-progress sorts, and if a sort has not yet started
     * it will start a new sort (which may happen if location is disabled on the device)
     */
    suspend fun getChaptersForFilter(filter: String?): List<NewChapter> {
        val data = sortedData()
        return when(filter) {
            null -> data.chapters
            else -> data.chaptersByRegion.getOrElse(filter) { emptyList() }
        }
    }

    /**
     * Get the filters sorted by distance from the last location.
     *
     * This will cancel if a new location is sent before the result is available.
     *
     * This works by first waiting for any previously in-progress sorts, and if a sort has not yet started
     * it will start a new sort (which may happen if location is disabled on the device)
     */
    suspend fun getFilters(): List<String> = sortedData().filters

    /**
     * Get the computed sorted data after it completes, or start a new sort if none are running.
     *
     * This will always cancel if the location changes while the sort is in progress.
     */
    private suspend fun sortedData(): SortedData = withContext(Dispatchers.Main) {
        // We need to ensure we're on Dispatchers.Main so that this is not running on multiple Dispatchers and we
        // modify the member inProgressSort.

        // Since this was called from viewModelScope, that will always be a simple if check (not expensive), but
        // by specifying the dispatcher we can protect against incorrect usage.

        // if there's currently a sort running (or completed) wait for it to complete and return that value
        // otherwise, start a new sort with no location (the user has likely not given us permission to use location
        // yet)
        inProgressSort?.await() ?: doSortData()
    }

    /**
     * Call this to force a new sort to start.
     *
     * This will start a new coroutine to perform the sort. Future requests to sorted data can use the deferred in
     * [inProgressSort] to get the result of the last sort without sorting the data again. This guards against multiple
     * sorts being performed on the same data, which is inefficient.
     *
     * This will always cancel if the location changes while the sort is in progress.
     *
     * @return the result of the started sort
     */
    private suspend fun doSortData(location: Location? = null): SortedData {
        // since we'll need to launch a new coroutine for the sorting use coroutineScope.
        // coroutineScope will automatically wait for anything started via async {} or await{} in it's block to
        // complete.
        val result = coroutineScope {
            // launch a new coroutine to do the sort (so other requests can wait for this sort to complete)
            val deferred = async { SortedData.from(request.await(), location) }
            // cache the Deferred so any future requests can wait for this sort
            inProgressSort = deferred
            // and return the result of this sort
            deferred.await()
        }
        return result
    }

    /**
     * Call when location changes.
     *
     * This will cancel any previous queries, so it's important to re-request the data after calling this function.
     *
     * @param location the location to sort by
     */
    suspend fun onLocationChanged(location: Location) {
        // We need to ensure we're on Dispatchers.Main so that this is not running on multiple Dispatchers and we
        // modify the member inProgressSort.

        // Since this was called from viewModelScope, that will always be a simple if check (not expensive), but
        // by specifying the dispatcher we can protect against incorrect usage.
        withContext(Dispatchers.Main) {
            isFullyInitialized = true

            // cancel any in progress sorts, their result is not valid anymore.
            inProgressSort?.cancel()

            doSortData(location)
        }
    }

    /**
     * Holds data sorted by the distance from the last location.
     *
     * Note, by convention this class won't sort on the Main thread. This is not a public API and should
     * only be called by [doSortData].
     */
    private class SortedData private constructor(
            val chapters: List<NewChapter>,
            val filters: List<String>,
            val chaptersByRegion: Map<String, List<NewChapter>>
    ) {

        companion object {
            /**
             * Sort the data from a [GdgResponse] by the specified location.
             *
             * @param response the response to sort
             * @param location the location to sort by, if null the data will not be sorted.
             */
            suspend fun from(response: List<NewGdgResponse>, location: Location?): SortedData {
                return withContext(Dispatchers.Default) {
                    // this sorting is too expensive to do on the main thread, so do thread confinement here.
                    val chapters: ArrayList<NewChapter> = ArrayList();
                    for (newGdgResponse in response){
                        for (chapter in newGdgResponse.chapters)
                            chapters.add(chapter)
                    }
                    chapters.sortByDistanceFrom(location)
                    // use distinctBy which will maintain the input order - this will have the effect of making
                    // a filter list sorted by the distance from the current location
                    val filters: List<String> = chapters.map { it.country } .distinctBy { it }
                    // group the chapters by region so that filter queries don't require any work
                    val chaptersByRegion: Map<String, List<NewChapter>> = chapters.groupBy { it.country }
                    // return the sorted result
                    SortedData(chapters, filters, chaptersByRegion)
                }

            }


            /**
             * Sort a list of GdgChapter by their distance from the specified location.
             *
             * @param currentLocation returned list will be sorted by the distance, or unsorted if null
             */
            private fun List<NewChapter>.sortByDistanceFrom(currentLocation: Location?): List<NewChapter> {
                currentLocation ?: return this

                return sortedBy { distanceBetween(it.latitude, it.longitude, currentLocation)}
            }

            /**
             * Calculate the distance (in meters) between a LatLong and a Location.
             */
            private fun distanceBetween(lat: Double, lon: Double, currentLocation: Location): Float {
                val results = FloatArray(3)
                Location.distanceBetween(lat, lon, currentLocation.latitude, currentLocation.longitude, results)
                return results[0]
            }
        }
    }

}