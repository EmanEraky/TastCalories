package com.eman.taskcalories.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val BASE_URL =
        "https://gist.githubusercontent.com/a7med-3laa/19dc3f2c5e0edb57bc7a0bea499dda20/raw/15caa02dd97df273257a7a959330a16f98ee1754/"


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiServices = getRetrofit().create(ApiServices::class.java)

}
