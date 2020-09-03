package com.example.composableandroidtest.dispatcher

import kotlin.coroutines.CoroutineContext

interface IAppDispatchers {
    val io: CoroutineContext
    val main: CoroutineContext
}