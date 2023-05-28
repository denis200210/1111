package com.example.guidetovladivostok.activity.common

import android.content.Context
import android.widget.Toast
import com.example.guidetovladivostok.contract.DrivingRouteContract
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.directions.DirectionsFactory
import com.yandex.mapkit.directions.driving.DrivingOptions
import com.yandex.mapkit.directions.driving.DrivingRoute
import com.yandex.mapkit.directions.driving.DrivingRouter
import com.yandex.mapkit.directions.driving.DrivingSession
import com.yandex.mapkit.directions.driving.VehicleOptions
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.PolylineMapObject
import com.yandex.runtime.Error

/** Класс для прокладывания маршрута до локации **/
class DrivingRouteService : DrivingSession.DrivingRouteListener, DrivingRouteContract.Service {

    private val points: MutableList<RequestPoint> = ArrayList()
    private var polyLineDrivingRouter: PolylineMapObject? = null
    private lateinit var drivingSession: DrivingSession
    private var context: Context
    private var mapObjects: MapObjectCollection
    private var drivingRouter: DrivingRouter = DirectionsFactory.getInstance().createDrivingRouter()

    constructor(context: Context, mapObjects: MapObjectCollection) {
        this.context = context
        this.mapObjects = mapObjects
    }

    override fun isLimitSize(): Boolean {
        return points.size >= 3
    }

    override fun isEmptyPositions(): Boolean {
        return points.isEmpty()
    }

    override fun isNotEmptyPositions(): Boolean {
        return points.isNotEmpty()
    }

    override fun setPosition(position: Point){
        points.add(RequestPoint(position, RequestPointType.WAYPOINT, null))
    }

    override fun deletePoints() {
        points.clear()
    }

    override fun buildDrivingRouter() {
        val drivingOptions = DrivingOptions()
        val vehicleOptions = VehicleOptions()
        drivingSession = drivingRouter.requestRoutes(points, drivingOptions, vehicleOptions, this)
    }

    override fun deleteRouter() {
        polyLineDrivingRouter?.parent?.clear()
        polyLineDrivingRouter = null
    }

    override fun onDrivingRoutes(list: MutableList<DrivingRoute>) {
        if (list.isNotEmpty()) {
            deleteRouter()
            polyLineDrivingRouter = mapObjects.addPolyline(list[0].geometry)
        } else {
            Toast
                .makeText(context, "Маршрут не найден", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onDrivingRoutesError(error: Error) {
        Toast
            .makeText(context, "Ошибка построения маршрута", Toast.LENGTH_LONG)
            .show()
    }


}