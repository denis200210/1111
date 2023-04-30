package com.example.guidetovladivostok.entity

import com.yandex.mapkit.geometry.Point
import java.io.Serializable

class HotelEntity: Serializable {
    private val name: String
    private val state: String
    private val address: String
    private val phone: String
    private val point: Point

    constructor(name: String, state: String, address: String, phone: String, point: Point) {
        this.name = name
        this.state = state
        this.address = address
        this.phone = phone
        this.point = point
    }


    fun getName(): String = name
    fun getState(): String = state
    fun getAddress(): String = address
    fun getPhone(): String = phone
    fun getPoint(): Point = point
}