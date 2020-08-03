package com.example.xlwapp.network.gdg

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewGdgResponse (
        val id: Int,
        val order: Int,
        val title: String,
        val chapters: List<NewChapter>
) : Parcelable

@Parcelize
data class NewChapter(
        val active: Boolean,
        val city: String,
        val country: String,
        val id: Int,
        val latitude: Double,
        val logo: Picture,
        val longitude: Double,
        val picture: Picture,
        val state: String,
        val title: String,
        val relative_url: String,
        val url: String
) : Parcelable

@Parcelize
data class Picture(
        val thumbnail_height: Int?,
        val thumbnail_width: Int?,
        val url: String?,
        val path: String?,
        val thumbnail_format: String?,
        val thumbnail_url: String?
) : Parcelable