package com.example.composableandroidtest.network.service.backend

import com.example.composableandroidtest.model.Address
import com.example.composableandroidtest.network.config.model.Status
import com.example.composableandroidtest.network.config.util.ApiResponse
import com.example.composableandroidtest.network.config.util.ApiSuccessResponse
import com.example.composableandroidtest.network.service.IBackendAPI
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.withContext
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.get
import org.mockito.Mockito.mock

class BackendServiceTest {

    private lateinit var sub: BackendService

    private lateinit var api: IBackendAPI

    @Before
    fun setUp() {
        api = mock(IBackendAPI::class.java)

        startKoin {
            modules(module {
                single { api }

                factory {
                    BackendService(get())
                }
            })
        }

        sub = get(BackendService::class.java)
    }

    @Test
    fun `when getAddress called and server succeed then flow should be collected`() = runBlockingTest {
        val cep =  "39400349"
        val deferred: CompletableDeferred<ApiResponse<Address>> = CompletableDeferred()
        val response: ApiSuccessResponse<Address> = ApiSuccessResponse(mock(Address::class.java))

        whenever(api.getCep(cep)).thenReturn(deferred).then {
            deferred.complete(response)
        }

        withContext(Dispatchers.Unconfined) {
            sub.getAddress(cep).collect {
                assertThat(it.data, equalTo(response.body))
                assertThat(it.status, equalTo(Status.SUCCESS))
                assertThat(it.message, nullValue())
            }
        }
    }
}