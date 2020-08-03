package com.example.xlwapp.network.gdg

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


// The alternative URL is for a server with a recent snapshot. If you are having problems
// with the given URL (app crashing), use the alternative.
private const val BASE_URL = "https://developers.google.com/community/gdg/groups/"
private const val NEW_BASE_URL = "https://gdg.community.dev/api/"

object NewGdgApi {
    val retrofitService: NewGdgApiService by lazy { newRetrofit.create(NewGdgApiService::class.java) }
}

interface NewGdgApiService{
    @GET("chapter_region")

    fun getChapters(@Query("chapters") boolean: Boolean):
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
            Deferred<List<NewGdgResponse>>
}


private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val newRetrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(NEW_BASE_URL)
        .build()


interface GdgApiService {
    @GET("directory.json")

    fun getChapters():
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
            Deferred<GdgResponse>
}


private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()


object GdgApi {
    val retrofitService: GdgApiService by lazy { retrofit.create(GdgApiService::class.java) }
}
