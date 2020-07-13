package com.example.class1.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
//import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


enum class MarsApiFilter(val value: String){
    SHOW_RENT("rent"),
    SHOW_BUY("buy"),
    SHOW_ALL("all")
}

private val retrofit = Retrofit.Builder()
//        .addConverterFactory(ScalarsConverterFactory.create())  //标准化数据
        .addConverterFactory(MoshiConverterFactory.create()) //moshi
        .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke()) // 携程
        .baseUrl(Uris.MARS_URL)
        .build()

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

interface MarsApiService {
    @GET("realestate")//value 是基于uri的 相当于uri/value
//    fun getProperties(): Call<String> //标准化
//    fun getProperties(): Call<List<MarsProperty>> // moshi
    fun getPropertiesAsync(@Query("filter") type: String): Deferred<List<MarsProperty>>//携程
}

object MarsApi {
    val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}
