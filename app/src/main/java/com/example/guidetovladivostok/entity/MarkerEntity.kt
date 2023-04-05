package com.example.guidetovladivostok.entity

import com.yandex.mapkit.geometry.Point

class MarkerEntity constructor(
    point: Point,
    nameLocation: String,
    informationLocation: String,
    address: String
) {

    private var nameLocation: String
    private var informationLocation: String
    private var address: String
    private var point: Point

    init {
        this.point = point
        this.nameLocation = nameLocation
        this.informationLocation = informationLocation
        this.address = address
    }

    fun getPoint(): Point = point

    fun getLatitude(): Double = point.latitude

    fun getLongitude(): Double = point.longitude

    fun getNameLocation(): String = nameLocation

    fun getInformationLocation(): String = informationLocation

    fun getAddress(): String = address
}