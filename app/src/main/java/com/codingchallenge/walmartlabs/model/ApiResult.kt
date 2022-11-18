package com.codingchallenge.walmartlabs.model

/**
 * @Author Dushane Coram
 * @Date 17, November, 2022
 * @Project WalmartLabs
 * @Copyright (c). All rights reserved.
 * @Description A Generic class to encapsulate api request results
 */

sealed class ApiResult<T>(
    val data: T? = null,
    val exception: Exception? = null
) {
    class Success<T>(data: T?) : ApiResult<T>(data)
    class Error<T>(exception: Exception, data: T? = null) : ApiResult<T>(data, exception)
}