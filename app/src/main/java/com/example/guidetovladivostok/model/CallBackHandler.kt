package com.example.guidetovladivostok.model

interface CallBackHandler<V> {
    fun execute(value: V)
}