package com.example.composableandroidtest.network.module

import com.example.composableandroidtest.network.config.adapter.RetrofitCallAdapterFactory
import mezzari.torres.lucas.network.source.Network
import retrofit2.Retrofit

class RetrofitModule: Network.RetrofitLevelModule {
    override fun onRetrofitBuilderCreated(retrofitBuilder: Retrofit.Builder) {
        retrofitBuilder.addCallAdapterFactory(RetrofitCallAdapterFactory())
    }
}