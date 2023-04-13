package com.example.guidetovladivostok.activity

import com.yandex.mapkit.MapKitFactory

object MapKitInitializer {

    private var initializer = false

    fun initializer(){
        if (initializer) return
        val API_KEY = "ba752f22-60cb-4094-befe-ef011190444a"
        MapKitFactory.setApiKey(API_KEY)
        initializer = true
    }
}