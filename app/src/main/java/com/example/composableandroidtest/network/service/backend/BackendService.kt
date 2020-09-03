package com.example.composableandroidtest.network.service.backend

import com.example.composableandroidtest.model.Address
import com.example.composableandroidtest.network.config.util.NetworkBoundResource
import com.example.composableandroidtest.network.config.model.Resource
import com.example.composableandroidtest.network.service.IBackendAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mezzari.torres.lucas.network.source.Network

class BackendService(
    private val api: IBackendAPI
): IBackendService {
    override suspend fun getAddress(cep: String): Flow<Resource<Address>> {
        return flow {
            NetworkBoundResource(
                collector = this,
                call = api.getCep(cep)
            ) {
                it
            }.build()
        }
    }
}