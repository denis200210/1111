package com.example.guidetovladivostok.activity.common

import android.content.Context
import android.widget.Toast
import com.example.guidetovladivostok.presenter.DrivingRouteContract
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
import com.yandex.runtime.Error

/** Класс для прокладывания маршрута до локации **/
class DrivingRouteService : DrivingSession.DrivingRouteListener, DrivingRouteContract.Service {


    private lateinit var drivingSession: DrivingSession
    private var context: Context
    private var mapObjects: MapObjectCollection
    private var drivingRouter: DrivingRouter = DirectionsFactory.getInstance().createDrivingRouter()

    constructor(context: Context, mapObjects: MapObjectCollection) {
        this.context = context
        this.mapObjects = mapObjects
    }

    override fun buildDrivingRouter(startPosition: Point, endPosition: Point) {
        val points: MutableList<RequestPoint> = ArrayList()
        points.add(RequestPoint(startPosition, RequestPointType.WAYPOINT, null))
        points.add(RequestPoint(endPosition, RequestPointType.WAYPOINT, null))
        val drivingOptions = DrivingOptions()
        val vehicleOptions = VehicleOptions()
        drivingSession = drivingRouter.requestRoutes(points, drivingOptions, vehicleOptions, this)
    }

    override fun deleteRouter(){
        mapObjects.clear()
    }

    override fun onDrivingRoutes(list: MutableList<DrivingRoute>) {
        if(list.isNotEmpty()) {
            deleteRouter()
            mapObjects.addPolyline(list[0].geometry)
        } else{
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