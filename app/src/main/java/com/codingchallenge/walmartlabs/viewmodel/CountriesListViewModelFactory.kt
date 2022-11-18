package com.codingchallenge.walmartlabs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codingchallenge.walmartlabs.repository.CountriesInterface

/**
 * @Author Dushane Coram
 * @Date 17, November, 2022
 * @Project WalmartLabs
 * @Copyright (c). All rights reserved.
 * @Description
 */
class CountriesListViewModelFactory (
    private val api: CountriesInterface
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) : T {
        return modelClass.getConstructor(CountriesInterface::class.java).newInstance(api)
    }
}