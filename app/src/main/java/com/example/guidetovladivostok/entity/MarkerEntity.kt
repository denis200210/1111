package com.example.guidetovladivostok.entity

import com.yandex.mapkit.geometry.Point

class MarkerEntity()  {

    private lateinit var nameLocation: String
    private lateinit var informationLocation: String
    private lateinit var address: String
    private lateinit var point: Point

    constructor(
        point: Point,
        nameLocation: String,
        informationLocation: String,
        address: String
    ): this() {
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