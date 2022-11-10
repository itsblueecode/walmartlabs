package com.codingchallenge.walmartlabs.model

import com.google.gson.annotations.SerializedName

/**
 * @Author Dushane Coram
 * @Date 07, November, 2022
 * @Project WalmartLabs
 * @Copyright (c) 2022. All rights reserved.
 * @Description
 */
data class Product(
    @SerializedName("productId")
    val productId: String,
    @SerializedName("productName")
    val productName: String,
    @SerializedName("shortDescription")
    val shortDescription: String,
    @SerializedName("longDescription")
    val longDescription: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("productImage")
    val productImage: String,
    @SerializedName("reviewRating")
    val reviewRating: Int,
    @SerializedName("reviewCount")
    val reviewCount: Int,
    @SerializedName("inStock")
    val inStock: Boolean
)
