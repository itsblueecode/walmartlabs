package com.codingchallenge.walmartlabs.repository

import com.codingchallenge.walmartlabs.model.ApiResult
import com.codingchallenge.walmartlabs.model.Country
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * @Author Dushane Coram
 * @Date 17, November, 2022
 * @Project WalmartLabs
 * @Copyright (c). All rights reserved.
 * @Description Contain all the requests pertaining to the countries list
 */
class CountriesRepository(private val api: CountriesInterface) {

    suspend fun getCountriesRequest() : ApiResult<List<Country>> {
        return withContext(Dispatchers.IO) {
            try {
                val result = api.getCountries()
                ApiResult.Success(result)
            } catch (e: Exception) {
                ApiResult.Error(e)
            }
        }
    }


}