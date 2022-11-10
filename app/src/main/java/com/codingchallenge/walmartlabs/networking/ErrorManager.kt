package com.codingchallenge.walmartlabs.networking

import android.util.Log
import retrofit2.HttpException
import java.net.SocketTimeoutException

/**
 * @Author Dushane Coram
 * @Date 08, November, 2022
 * @Project WalmartLabs
 * @Copyright (c). All rights reserved.
 * @Description TODO ADD CLASS DESCRIPTION HERE!!!
 */
class ErrorManager(e: Exception) {
    var message: String = ""
        private set
    init {
        Log.e(javaClass.name, "Error = ${e.message}")
        message = when (e) {
            is HttpException -> { // HTTP 500
                "The server could not be reached."
            }
            is SocketTimeoutException -> {
                "The request has timed out. Please try again."
            }
            else -> {
                "Something went wrong."
            }
        }
    }
}