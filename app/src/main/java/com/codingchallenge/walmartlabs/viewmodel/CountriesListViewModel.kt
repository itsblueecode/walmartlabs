package com.codingchallenge.walmartlabs.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingchallenge.walmartlabs.model.ApiResult
import com.codingchallenge.walmartlabs.model.Country
import com.codingchallenge.walmartlabs.repository.CountriesInterface
import com.codingchallenge.walmartlabs.repository.CountriesRepository
import kotlinx.coroutines.launch

/**
 * @Author Dushane Coram
 * @Date 17, November, 2022
 * @Project WalmartLabs
 * @Copyright (c). All rights reserved.
 * @Description
 */
class CountriesListViewModel(
    api: CountriesInterface
) : ViewModel() {

    private val repository = CountriesRepository(api)

    private val _countries = MutableLiveData<ApiResult<List<Country>>>()
    val countries: LiveData<ApiResult<List<Country>>> = _countries

    val isLoading = MutableLiveData<Boolean>()


    /** methods **/


    init {
        isLoading.value = true
    }

    fun getCountries() {
        viewModelScope.launch {
            if (isLoading.value == false) isLoading.value = true
            val response = repository.getCountriesRequest()
            if (response.exception != null) {
                _countries.postValue(ApiResult.Error(response.exception))
            } else {
                _countries.postValue(ApiResult.Success(response.data))
            }
            isLoading.value = false
        }
    }
}