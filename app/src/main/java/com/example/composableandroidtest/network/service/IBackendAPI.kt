package com.example.composableandroidtest.network.service

import com.example.composableandroidtest.model.Address
import com.example.composableandroidtest.network.config.util.ApiResponse
import kotlinx.coroutines.Deferred
import mezzari.torres.lucas.network.annotation.Route
import retrofit2.http.GET
import retrofit2.http.Path

@Route("https://viacep.com.br/ws/")
interface IBackendAPI {
    @GET("{cep}/json")
    fun getCep(
        @Path("cep") cep: String
    ): Deferred<ApiResponse<Address>>
}