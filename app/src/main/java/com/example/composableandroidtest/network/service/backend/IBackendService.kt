package com.example.composableandroidtest.network.service.backend

import com.example.composableandroidtest.model.Address
import com.example.composableandroidtest.network.config.model.Resource
import kotlinx.coroutines.flow.Flow

interface IBackendService {
    suspend fun getAddress(cep: String): Flow<Resource<Address>>
}