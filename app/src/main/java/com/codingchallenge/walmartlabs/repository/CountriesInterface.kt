package com.codingchallenge.walmartlabs.repository

import com.codingchallenge.walmartlabs.model.Country
import retrofit2.http.GET

/**
 * @Author Dushane Coram
 * @Date 17, November, 2022
 * @Project WalmartLabs
 * @Copyright (c). All rights reserved.
 * @Description ..
 */
interface CountriesInterface {

    // fetch the list of countries
    @GET(".")
    suspend fun getCountries(): List<Country>

    companion object {
        const val BASE_URL = "https://gist.githubusercontent.com/peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/countries.json/"
    }

}