package com.example.stackoverflowclone.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Mehul Bisht on 31-10-2021
 */

interface CoroutineDispatcherProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfirmed: CoroutineDispatcher
}
