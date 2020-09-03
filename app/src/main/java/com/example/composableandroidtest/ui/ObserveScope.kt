package com.example.composableandroidtest.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.onActive
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

@Composable
fun <T> observe(data: LiveData<T>, block: ObserveScope<T>.() -> Unit) {
    onActive {
        val context = ObserveScope<T>()
        block(context)
        context.onStartBlock()

        val observer = object : Observer<T> {
            override fun onChanged(t: T) {
                val resultScope = ObserveScope.OnResultScope(this, data, t)
                context.onResultBlock(resultScope)
            }
        }

        with(data) {
            observeForever(observer)
            onDispose {
                removeObserver(observer)
            }
        }
    }
}

class ObserveScope<T> {
    internal var onStartBlock: () -> Unit = {}
    fun onStart(block: () -> Unit) {
        onStartBlock = block
    }

    internal var onResultBlock: OnResultScope<T>.() -> Unit = {}
    fun onResult(block: OnResultScope<T>.() -> Unit) {
        onResultBlock = block
    }

    class OnResultScope<T>(
        private val observer: Observer<T>,
        private val data: LiveData<T>,
        val result: T
    ) {
        fun stopObserving() {
            data.removeObserver(observer)
        }
    }
}