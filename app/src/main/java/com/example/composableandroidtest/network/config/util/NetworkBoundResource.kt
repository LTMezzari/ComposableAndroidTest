package com.example.composableandroidtest.network.config.util

import com.example.composableandroidtest.network.config.model.Resource
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.FlowCollector

class NetworkBoundResource<ResultType, RequestType> constructor(
        private val collector: FlowCollector<Resource<ResultType>>,
        private val call: Deferred<ApiResponse<RequestType>>,
        private val processResponse: (response: RequestType) -> ResultType
) {

    suspend fun build(): NetworkBoundResource<ResultType, RequestType> {
        collector.emit(Resource.loading())
        fetchFromNetwork()
        return this
    }

    private suspend fun fetchFromNetwork() {
        return when (val result = call.await()) {
            is ApiSuccessResponse -> {
                val process = processResponse(result.body)
                collector.emit(Resource.success(process))
            }
            is ApiEmptyResponse -> {
                collector.emit(Resource.success(null))
            }
            is ApiErrorResponse -> {
                collector.emit(Resource.error(result.error))
            }
        }
    }
}