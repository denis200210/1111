package com.example.guidetovladivostok.presenter

import com.yandex.mapkit.geometry.Point

interface DrivingRouteContract {
    interface View{
        fun buildDrivingRouter(nameLocation: String, endPosition: Point)
        fun deleteRouter()
    }
    interface Service{
        fun buildDrivingRouter(startPosition: Point, endPosition: Point)
        fun deleteRouter()
    }
}