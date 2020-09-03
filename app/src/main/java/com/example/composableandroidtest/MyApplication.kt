package com.example.composableandroidtest

import android.app.Application
import com.example.composableandroidtest.di.appModule
import com.example.composableandroidtest.network.module.RetrofitModule
import mezzari.torres.lucas.network.source.Network
import mezzari.torres.lucas.network.source.module.client.LogModule
import mezzari.torres.lucas.network.source.module.retrofit.GsonConverterModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        Network.initialize(
                retrofitLevelModules = arrayListOf(GsonConverterModule(), RetrofitModule()),
                okHttpClientLevelModule = arrayListOf(LogModule())
        )

        startKoin{
            androidLogger(Level.ERROR)
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
}