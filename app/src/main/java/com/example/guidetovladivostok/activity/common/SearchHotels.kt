package com.example.guidetovladivostok.activity.common

import android.content.Context
import android.widget.Toast
import com.example.guidetovladivostok.R
import com.example.guidetovladivostok.entity.HotelEntity
import com.yandex.mapkit.GeoObjectCollection
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.VisibleRegionUtils
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.search.*
import com.yandex.runtime.Error
import com.yandex.runtime.image.ImageProvider

class SearchHotels : Session.SearchListener {

    private val searchManager: SearchManager
    private val mapView: MapView
    private val context: Context
    private val mapObjects: MapObjectCollection
    private lateinit var session: Session

    private var mapObjectTapListener: MapObjectTapListener

    constructor(mapView: MapView, context: Context, mapObjectTapListener: MapObjectTapListener) {
        this.mapView = mapView
        this.context = context
        this.mapObjectTapListener = mapObjectTapListener
        mapObjects = mapView.map.mapObjects
        searchManager = SearchFactory
            .getInstance()
            .createSearchManager(SearchManagerType.COMBINED)
    }

    fun showHotels() {
        session = searchManager.submit(
            "HOTEL",
            VisibleRegionUtils.toPolygon(mapView.map.visibleRegion),
            SearchOptions(),
            this
        )
    }

    fun hideHotels() {
        mapObjects.clear()
    }

    override fun onSearchResponse(response: Response) {

        for (searchResult: GeoObjectCollection.Item in response.collection.children) {
            val name = searchResult
                .obj
                ?.metadataContainer
                ?.getItem(BusinessObjectMetadata::class.java)
                ?.name

            val address = searchResult
                .obj
                ?.metadataContainer
                ?.getItem(BusinessObjectMetadata::class.java)
                ?.address

            val phone = searchResult
                .obj
                ?.metadataContainer
                ?.getItem(BusinessObjectMetadata::class.java)
                ?.phones

            val workingHours = searchResult
                .obj
                ?.metadataContainer
                ?.getItem(BusinessObjectMetadata::class.java)
                ?.workingHours

            val point = searchResult.obj?.geometry?.get(0)?.point

            if (point != null && name != null && address != null && phone != null && workingHours != null) {
                val marker = mapObjects
                    .addPlacemark(
                        point,
                        ImageProvider.fromResource(context, R.drawable.icon_marker_hotel)
                    )

                val hotelEntity = HotelEntity(
                    name,
                    workingHours.state?.text.toString(),
                    address.formattedAddress,
                    phone[0].formattedNumber,
                    point
                )

                marker.userData = hotelEntity
                marker.addTapListener(mapObjectTapListener)
            }
        }
    }

    override fun onSearchError(error: Error) {
        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
    }
}