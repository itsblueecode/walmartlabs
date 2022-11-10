package com.codingchallenge.walmartlabs.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.codingchallenge.walmartlabs.model.PageResponse
import com.codingchallenge.walmartlabs.model.Product
import kotlinx.coroutines.delay

/**
 * @Author Dushane Coram
 * @Date 08, November, 2022
 * @Project WalmartLabs
 * @Copyright (c). All rights reserved.
 * @Description
 */
class ProductsPagingSource(
    private val api: ProductInterface
) : PagingSource<Int, Product>() {

    companion object {
        private const val INITIAL_PAGE_NUMBER = 1
        const val PAGE_SIZE = 2
    }

    override suspend fun load(params: LoadParams<Int>) : LoadResult<Int, Product> {
        val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
        return try {
//            val response = api.getProductsList(
//                pageNumber = pageNumber,
//                pageSize = PAGE_SIZE
//            )
            val response = getFakeProducts(pageNumber, PAGE_SIZE)
            val products = response.products
            val nextKey =
                if (products.isEmpty()) {
                    null
                } else {
                    if ((pageNumber * PAGE_SIZE) < response.totalProducts) pageNumber + 1 else null
                }
            val preKey = if (pageNumber == INITIAL_PAGE_NUMBER) null else pageNumber - 1

            println("load: pageNumber = $pageNumber")
            println("nextKey = $nextKey")
            println("preKey = $preKey")
            println("products = $products")
            println("====================================")

            LoadResult.Page(
                data = products,
                prevKey = preKey, // could set this to null to only allow forward pagination
                nextKey = nextKey
            )

        } catch (e: Exception) {
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }

    /**
     * The refresh key is used for subsequent calls to PagingSource.Load after the initial load.
     */
    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }

        return null
    }


    private suspend fun getFakeProducts(pageNumber: Int, pageSize: Int): PageResponse {
        delay(2000)
        val products = listOf(
            Product(
                productId = "abc001",
                productName = "Samsung Smart Tv",
                shortDescription = "Include hologram projection",
                longDescription = "The Samsung Smart Tv is the premier television device on the market",
                price = "$1500.75",
                productImage = "/images/image1.jpeg",
                reviewRating = 4,
                reviewCount = 170,
                inStock = true
            ),
            Product(
                productId = "abc002",
                productName = "Element HDTV",
                shortDescription = "Bargain tv that is reliable",
                longDescription = "The Element HDTV is a reliable and affordable tv for the masses.",
                price = "$1100.00",
                productImage = "/images/image2.jpeg",
                reviewRating = 3,
                reviewCount = 46,
                inStock = false
            ),
            Product(
                productId = "abc003",
                productName = "Sony OLED Smart Tv",
                shortDescription = "Ultra thin smart tv with 8k resolution. ",
                longDescription = "The Sony OLED Smart Tv is the future of how we watch our favorite episodes and movies.",
                price = "$2000.00",
                productImage = "/images/image3.jpeg",
                reviewRating = 5,
                reviewCount = 209,
                inStock = true
            ),
            Product(
                productId = "abc003",
                productName = "Toshiba FireTv",
                shortDescription = "Built around streaming.",
                longDescription = "The Toshiba FireTv is the ultimate streaming setup for your living room.",
                price = "$1000.00",
                productImage = "/images/image4.jpeg",
                reviewRating = 3,
                reviewCount = 299,
                inStock = false
            )

        )

        val startIndex = (pageNumber * pageSize) - pageSize
        val endIndex = (startIndex + pageSize) - 1
        val productsList: MutableList<Product> = mutableListOf()

        for (i in startIndex..endIndex) {
            productsList.add(products[i])
        }

        return PageResponse(
            products = productsList,
            totalProducts = products.size,
            pageNumber = pageNumber,
            pageSize = pageSize,
            statusCode = 200
        )
    }

    private suspend fun getFakeProductEmpty(pageNumber: Int, pageSize: Int) : PageResponse {
        delay(2000)
        return PageResponse(
            products = listOf(),
            totalProducts = 0,
            pageNumber = pageNumber,
            pageSize = pageSize,
            statusCode = 200
        )
    }

    @Throws(Exception::class)
    private suspend fun getFakeProductError(pageNumber: Int, pageSize: Int) : PageResponse {
        delay(2000)
        if (pageNumber == 1) {
            throw Exception("Something went wrong. Please try again.")
        }
        return PageResponse(
            products = listOf(),
            totalProducts = 0,
            pageNumber = pageNumber,
            pageSize = pageSize,
            statusCode = 500
        )
    }

}