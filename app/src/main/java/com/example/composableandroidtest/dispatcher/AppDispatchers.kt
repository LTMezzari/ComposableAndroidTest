package com.example.composableandroidtest.dispatcher

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class AppDispatchers(
    override val io: CoroutineContext = Dispatchers.IO,
    override val main: CoroutineContext = Dispatchers.Main,
): IAppDispatchers