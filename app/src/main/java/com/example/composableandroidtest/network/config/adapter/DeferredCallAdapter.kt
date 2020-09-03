package com.example.composableandroidtest.network.config.adapter

import com.example.composableandroidtest.network.config.util.ApiResponse
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class DeferredCallAdapter<R>(
    private val responseType: Type
) : CallAdapter<R, Deferred<ApiResponse<R>>> {

    override fun responseType(): Type = responseType

    override fun adapt(call: Call<R>): Deferred<ApiResponse<R>> {
        val deferred = CompletableDeferred<ApiResponse<R>>()

        val callback = object : Callback<R> {
            override fun onFailure(call: Call<R>, t: Throwable) {
                deferred.complete(ApiResponse.create(t))
            }

            override fun onResponse(
                call: Call<R>,
                response: Response<R>
            ) {
                deferred.complete(ApiResponse.create(response))
            }

        }

        call.enqueue(callback)

        return deferred
    }

}