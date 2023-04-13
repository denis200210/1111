package com.example.guidetovladivostok.exception

/** Исключение: маркер не найден **/
class MarkersNotFoundException
constructor(message: String) : RuntimeException(message) {
}