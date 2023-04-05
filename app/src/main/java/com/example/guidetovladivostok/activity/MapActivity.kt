package com.example.guidetovladivostok.activity

import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.guidetovladivostok.R
import com.example.guidetovladivostok.UserLocationObject
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.GeoObjectTapListener
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.GeoObjectSelectionMetadata
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.traffic.TrafficLayer
import com.yandex.mapkit.user_location.UserLocationLayer
import java.util.*

class MapActivity : AppCompatActivity() {

    private val ACCESS_FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION
    private val ACCESS_COARSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION
    private val PERMISSION_CODE = 200

    private lateinit var traffic: TrafficLayer
    private lateinit var mapView: MapView
    private lateinit var imageButtonTraffic: ImageButton
    private lateinit var imageButtonListLocation: ImageButton
    private lateinit var mapKit: MapKit
    private lateinit var locationUser: UserLocationLayer
    private lateinit var tabGeoObject: GeoObjectTapListener
    private lateinit var inputListener: InputListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        val API_KEY = "ba752f22-60cb-4094-befe-ef011190444a"
        MapKitFactory.setApiKey(API_KEY)
        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_map)

        if (checkPermission()) {
            init()
            onClick()
        }
    }

    override fun onStart() {
        mapView.onStart()
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    private fun init() {
        imageButtonTraffic = findViewById(R.id.imageButtonTraffic)
        mapView = findViewById(R.id.yandexMap)
        mapKit = MapKitFactory.getInstance()
        traffic = mapKit.createTrafficLayer(mapView.mapWindow)
        moveLocationUser()
        labelLocationUser()
        registerOnTabGeoListener()
    }

    private fun registerOnTabGeoListener() {
        tabGeoObject = GeoObjectTapListener { event ->

            val metaData = event
                .geoObject
                .metadataContainer
                .getItem(GeoObjectSelectionMetadata::class.java)

            if (metaData != null) {
                mapView.map.selectGeoObject(metaData.id, metaData.layerId)
                val point = event.geoObject.geometry[0].point
                if(point != null) {
                    val geocoder = Geocoder(this@MapActivity, Locale.getDefault())
                    val address = geocoder.getFromLocation(point.latitude, point.longitude, 1)
                    if (address != null){
                        startAddPointFragment(point, address[0].getAddressLine(0))
                    }
                }
            }
            metaData != null
        }

        inputListener = object : InputListener{
            override fun onMapTap(map: Map, point: Point) {
                mapView.map.deselectGeoObject()
            }

            override fun onMapLongTap(p0: Map, p1: Point) {
                mapView.map.deselectGeoObject()
            }
        }
        mapView.map.addTapListener(tabGeoObject)
        mapView.map.addInputListener(inputListener)
    }

    private fun labelLocationUser() {
        locationUser = mapKit.createUserLocationLayer(mapView.mapWindow)
        locationUser.isVisible = true
        locationUser.setObjectListener(UserLocationObject(this, locationUser))
    }

    private fun moveLocationUser() {
        val locationManager = MapKitFactory.getInstance().createLocationManager()
        locationManager.requestSingleUpdate(object : LocationListener {
            override fun onLocationUpdated(location: Location) {
                mapView
                    .map
                    .move(
                        CameraPosition(location.position, 14.0f, 0.0f, 5.0f),
                        Animation(Animation.Type.SMOOTH, 2f),
                        null
                    )
            }

            override fun onLocationStatusUpdated(p0: LocationStatus) {

            }
        })
    }

    private fun onClick() {
        imageButtonTraffic.setOnClickListener(View.OnClickListener {
            traffic.isTrafficVisible = !traffic.isTrafficVisible
        })
    }

    private fun startAddPointFragment(point: Point, address: String){
        val bundle = Bundle()
        bundle.putString(KeyValue.KEY_ADDRESS, address)
        bundle.putDouble(KeyValue.KEY_LATITUDE, point.latitude)
        bundle.putDouble(KeyValue.KEY_LONGITUDE, point.longitude)
        supportFragmentManager.apply {
            for(fragment: Fragment in fragments){
                beginTransaction().remove(fragment).commit()
            }
        }
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentAddPointOrInformationLocation, AddPointFragment::class.java, bundle)
            .commit()
    }

    private fun startInformationLocationFragment(point: Point, nameLocation: String, address: String){
        val bundle = Bundle()
        bundle.putString(KeyValue.KEY_ADDRESS, address)
        bundle.putDouble(KeyValue.KEY_LATITUDE, point.latitude)
        bundle.putDouble(KeyValue.KEY_LONGITUDE, point.longitude)
        bundle.putString(KeyValue.KEY_NAME_LOCATION, nameLocation)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentAddPointOrInformationLocation, InformationLocationFragment::class.java, bundle)
            .commit()
    }

    private fun checkPermission(): Boolean {
        return if (
            ActivityCompat.checkSelfPermission(applicationContext, ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(applicationContext, ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION),
                PERMISSION_CODE
            )
            false
        } else {
            true
        }
    }

}