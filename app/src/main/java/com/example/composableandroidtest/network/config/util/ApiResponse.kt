package com.example.composableandroidtest.network.config.util

import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response

object ApiGeneralKeys {
    const val errorKey = "message"
}

fun ResponseBody?.getErrorJson(errorKey: String): JSONObject? {
    this?.charStream()?.readText()?.takeIf { it.contains(errorKey) }?.let {
        return JSONObject(it)
    }
    return null
}

sealed class ApiResponse<T> {
    companion object {
        fun <T> create (error: Throwable) : ApiErrorResponse<T> {
            return ApiErrorResponse(error.message)
        }

        fun <T> create (response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body != null && response.code() != 204) {
                    ApiSuccessResponse(body)
                } else {
                    ApiEmptyResponse()
                }
            } else {
                val key = ApiGeneralKeys.errorKey
                val jsonObject = response.errorBody()?.getErrorJson(key)
                val errorMessage =
                    if (jsonObject != null && jsonObject.has(ApiGeneralKeys.errorKey)) {
                        jsonObject[key] as? String
                    } else {
                        null
                    }
                ApiErrorResponse(errorMessage)
            }
        }
    }
}

data class ApiSuccessResponse<T>(val body: T): ApiResponse<T>()
class ApiEmptyResponse<T>: ApiResponse<T>()
data class ApiErrorResponse<T>(val error: String?): ApiResponse<T>()