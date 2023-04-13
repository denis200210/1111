package com.example.guidetovladivostok.model

/** Универсальный обработчик обратного вызова **/
interface CallBackHandler<V> {
    fun execute(value: V)
}