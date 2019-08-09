package com.trile.hometest.store.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

/**
 *
 * @author lmtri
 * @since Aug 06, 2019 at 5:49 PM
 */
interface ApiService {

    @GET(ApiContract.ITEM_NAMES_API_ROUTE)
    fun getItemNames(): Call<List<String>>

    companion object {
        private var INSTANCE: ApiService? = null

        fun getInstance(): ApiService? {
            if (INSTANCE == null) {
                synchronized(ApiService::class) {
                    val builder = OkHttpClient.Builder()
                    builder.readTimeout(45, TimeUnit.SECONDS)

                    // Logging interceptor
                    val httpLoggerInterceptor = HttpLoggingInterceptor()
                    httpLoggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    builder.addNetworkInterceptor(httpLoggerInterceptor)

                    // Check the url
                    var baseURL = ApiContract.DATA_SERVER_URL
                    if (!baseURL.toLowerCase().startsWith("https://")) {
                        baseURL = "https://".plus(baseURL.toLowerCase())
                    }
                    baseURL = if (baseURL.endsWith("/")) baseURL else "$baseURL/"

                    val retrofit = Retrofit.Builder()
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(MoshiConverterFactory.create().withNullSerialization())
                            .baseUrl(baseURL)
                            .client(builder.build())
                            .build()

                    INSTANCE = retrofit.create(ApiService::class.java)
                }
            }
            return INSTANCE
        }
    }
}