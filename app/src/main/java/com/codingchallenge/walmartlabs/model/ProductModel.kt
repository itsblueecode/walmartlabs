package com.codingchallenge.walmartlabs.model

import com.google.gson.annotations.SerializedName

/**
 * @Author Dushane Coram
 * @Date 10, November, 2022
 * @Project WalmartLabs
 * @Copyright (c). All rights reserved.
 * @Description TODO ADD CLASS DESCRIPTION HERE!!!
 */

sealed class ProductModel {
    data class ProductData(
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
    ) : ProductModel() {
        constructor(product: Product) : this(
            product.productId,
            product.productName,
            product.shortDescription,
            product.longDescription,
            product.price,
            product.productImage,
            product.reviewRating,
            product.reviewCount,
            product.inStock
        )
    }
    class ProductSeparator(val tag: String) : ProductModel()
}
