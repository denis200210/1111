package com.example.guidetovladivostok

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.core.app.ActivityCompat
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.traffic.TrafficLayer
import com.yandex.mapkit.user_location.UserLocationLayer

class MainActivity : AppCompatActivity() {

    private val TARGET_LOCATION = Point(43.122664, 131.925661)
    private val ACCESS_FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION
    private val ACCESS_COARSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION
    private val PERMISSION_CODE = 200

    private lateinit var mapView: MapView
    private lateinit var imageButtonTraffic: ImageButton
    private lateinit var imageButtonListLocation: ImageButton
    private lateinit var mapKit: MapKit
    private lateinit var traffic: TrafficLayer
    private lateinit var locationUser: UserLocationLayer

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
        mapView
            .map
            .move(
                CameraPosition(TARGET_LOCATION, 12.0f, 0.0f, 5.0f),
                Animation(Animation.Type.SMOOTH, 4f),
                null
            )
        mapKit = MapKitFactory.getInstance()
        traffic = mapKit.createTrafficLayer(mapView.mapWindow)
        locationUser()
    }

    private fun locationUser() {
        locationUser = mapKit.createUserLocationLayer(mapView.mapWindow)
        locationUser.isVisible = true
        locationUser.setObjectListener(UserLocationObject(this, locationUser))
    }

    private fun onClick() {
        imageButtonTraffic.setOnClickListener(View.OnClickListener {
            traffic.isTrafficVisible = !traffic.isTrafficVisible
        })
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