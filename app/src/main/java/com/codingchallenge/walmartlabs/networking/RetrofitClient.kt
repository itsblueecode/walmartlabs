package com.codingchallenge.walmartlabs.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @Author Dushane Coram
 * @Date 07, November, 2022
 * @Project WalmartLabs
 * @Copyright (c) 2022. All rights reserved.
 * @Description This is our Retrofit client builder class.
 */
object RetrofitClient {
    fun getInstance(url: String): Retrofit {
        val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}