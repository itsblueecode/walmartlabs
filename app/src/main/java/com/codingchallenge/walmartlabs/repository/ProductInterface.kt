package com.codingchallenge.walmartlabs.repository

import com.codingchallenge.walmartlabs.model.PageResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @Author Dushane Coram
 * @Date 07, November, 2022
 * @Project WalmartLabs
 * @Copyright (c) 2022. All rights reserved.
 * @Description This is our Interface Class Api. It provide retrofit with all the possible functions it can call
 */
interface ProductInterface {

    // fetch the list of products
    @GET("walmartproducts/{pageNumber}/{pageSize}")
    suspend fun getProductsList(
        @Path(value = "pageNumber") pageNumber: Int,
        @Path(value = "pageSize") pageSize: Int
    ) : PageResponse

    companion object {
        const val BASE_URL = "https://mobile-tha-server.firebaseapp.com/"
    }
}