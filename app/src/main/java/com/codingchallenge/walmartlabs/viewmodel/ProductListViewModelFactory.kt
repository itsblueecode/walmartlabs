package com.codingchallenge.walmartlabs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codingchallenge.walmartlabs.repository.ProductInterface

/**
 * @Author Dushane Coram
 * @Date 08, November, 2022
 * @Project WalmartLabs
 * @Copyright (c). All rights reserved.
 * @Description TODO ADD CLASS DESCRIPTION HERE!!!
 */
class ProductListViewModelFactory (
    private val api: ProductInterface
    ) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) : T {
        return modelClass.getConstructor(ProductInterface::class.java).newInstance(api)
    }
}