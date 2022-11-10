package com.codingchallenge.walmartlabs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.codingchallenge.walmartlabs.model.ProductModel
import com.codingchallenge.walmartlabs.repository.ProductInterface
import com.codingchallenge.walmartlabs.repository.ProductsPagingSource
import kotlinx.coroutines.flow.map


/**
 * @Author Dushane Coram
 * @Date 07,November,2022
 * @Project WalmartLabs
 * @Company Blue wolf Applied Technology,
 * @Description TODO ADD CLASS DESCRIPTION HERE!!!
 */
class ProductsListViewModel(
    api: ProductInterface
) : ViewModel() {

    val productsList =
        Pager(
            config = PagingConfig(
                pageSize = ProductsPagingSource.PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ProductsPagingSource(api)
            }
        ).flow
            .map { pagingData ->
                pagingData.map {
                    ProductModel.ProductData(it)
                }.insertSeparators { before, after ->
                    when {
                        before == null -> null
                        after == null -> null
                        else -> ProductModel.ProductSeparator("Separator: $before-$after")
                    }
                }
            }
            .cachedIn(viewModelScope)
}