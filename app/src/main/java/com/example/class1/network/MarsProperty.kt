package com.example.class1.network

import com.squareup.moshi.Json

data class MarsProperty(
        val id: String,
        @Json(name = "img_src")//用于替换名字  img_src（原Json中的name） 替换成 imgSrcUrl
        val imgSrcUrl: String,
        val type: String,
        val price: Double
)
