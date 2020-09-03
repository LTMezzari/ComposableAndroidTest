package com.example.composableandroidtest.di

import com.example.composableandroidtest.dispatcher.AppDispatchers
import com.example.composableandroidtest.dispatcher.IAppDispatchers
import com.example.composableandroidtest.flow.MainActivityViewModel
import com.example.composableandroidtest.network.module.RetrofitModule
import com.example.composableandroidtest.network.service.IBackendAPI
import com.example.composableandroidtest.network.service.backend.BackendService
import com.example.composableandroidtest.network.service.backend.IBackendService
import mezzari.torres.lucas.network.source.Network
import mezzari.torres.lucas.network.source.module.client.LogModule
import mezzari.torres.lucas.network.source.module.retrofit.GsonConverterModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val genericModule = module {
    single<IAppDispatchers> { AppDispatchers() }
}

val networkModule = module {
    single {
        Network.initialize(
                retrofitLevelModules = arrayListOf(GsonConverterModule(), RetrofitModule()),
                okHttpClientLevelModule = arrayListOf(LogModule())
        )

        return@single Network
    }

    single { get<Network>().build<IBackendAPI>(IBackendAPI::class) }

    single<IBackendService> { BackendService(get()) }
}

val viewModelModule = module {
    viewModel { MainActivityViewModel(get(), get()) }
}

val appModule = listOf(genericModule, networkModule, viewModelModule)