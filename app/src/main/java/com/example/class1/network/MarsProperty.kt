package com.example.xlwapp.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarsProperty(
        val id: String,
        @field:Json(name = "img_src")//用于替换名字  img_src（原Json中的name） 替换成 imgSrcUrl
        val imgSrcUrl: String,
        val type: String,
        val price: Double
) : Parcelable {
        val isRentail
                get() = type == "rent"
}
