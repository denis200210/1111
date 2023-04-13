package com.example.guidetovladivostok.dto

import com.yandex.mapkit.geometry.Point
import java.io.Serializable

class MarkerDto constructor(
    documentId: String,
    point: Point,
    nameLocation: String,
    informationLocation: String,
    address: String
): Serializable {

    private var documentId: String
    private var nameLocation: String
    private var informationLocation: String
    private var address: String
    private var point: Point

    init {
        this.documentId = documentId
        this.point = point
        this.nameLocation = nameLocation
        this.informationLocation = informationLocation
        this.address = address
    }

    fun getDocumentId(): String = documentId

    fun getPoint(): Point = point

    fun getLatitude(): Double = point.latitude

    fun getLongitude(): Double = point.longitude

    fun getNameLocation(): String = nameLocation

    fun getInformationLocation(): String = informationLocation

    fun getAddress(): String = address
}