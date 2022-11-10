package com.codingchallenge.walmartlabs.model

import com.google.gson.annotations.SerializedName

/**
 * @Author Dushane Coram
 * @Date 08, November, 2022
 * @Project WalmartLabs
 * @Copyright (c). All rights reserved.
 * @Description
 */
data class PageResponse(
    @SerializedName("products")
    val products: List<Product>,
    @SerializedName("totalProducts")
    val totalProducts: Int,
    @SerializedName("pageNumber")
    val pageNumber: Int,
    @SerializedName("pageSize")
    val pageSize: Int,
    @SerializedName("statusCode")
    val statusCode: Int,
)
