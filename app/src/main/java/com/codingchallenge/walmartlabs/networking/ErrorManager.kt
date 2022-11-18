package com.codingchallenge.walmartlabs.networking

import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * @Author Dushane Coram
 * @Date 08, November, 2022
 * @Project WalmartLabs
 * @Copyright (c). All rights reserved.
 * @Description Our ErrorManager class
 */
class ErrorManager(e: Exception) {
    var message: String = ""
        private set
    init {
        e.printStackTrace()
        message = when (e) {
            is HttpException -> { // HTTP 500
                "The server could not be reached."
            }
            is SocketTimeoutException -> {
                "The request has timed out. Please try again."
            }
            is ConnectException -> {
                "The request has timed out. Check your internet connection and try again."
            }
            else -> {
                "Something went wrong."
            }
        }
    }
}