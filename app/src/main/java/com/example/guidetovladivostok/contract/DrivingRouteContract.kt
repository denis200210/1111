package com.example.guidetovladivostok.contract

import com.yandex.mapkit.geometry.Point

interface DrivingRouteContract {
    interface View{
        fun buildDrivingRouter(nameLocation: String, endPosition: Point)
        fun deleteRouter()
    }
    interface Service{
        fun isLimitSize(): Boolean
        fun isEmptyPositions(): Boolean
        fun isNotEmptyPositions(): Boolean
        fun setPosition(position: Point)
        fun deletePoints()
        fun buildDrivingRouter()
        fun deleteRouter()
    }
}