package com.example.guidetovladivostok.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PointF
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.guidetovladivostok.R
import com.example.guidetovladivostok.activity.common.DrivingRouteService
import com.example.guidetovladivostok.activity.common.NetworkIsConnectedService
import com.example.guidetovladivostok.activity.common.UserLocationObject
import com.example.guidetovladivostok.dto.MarkerDto
import com.example.guidetovladivostok.presenter.DrivingRouteContract
import com.example.guidetovladivostok.presenter.MapContract
import com.example.guidetovladivostok.presenter.MapPresenter
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.GeoObjectTapListener
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.traffic.TrafficLayer
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.runtime.image.ImageProvider
import java.util.*

/** Активность для регистрации карты и ее показа, главная активность в приложении **/
class MapActivity : AppCompatActivity(), MapContract.View<List<MarkerDto>>, DrivingRouteContract.View {

    private val ACCESS_FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION
    private val ACCESS_COARSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION
    private val PERMISSION_CODE = 200

    private lateinit var traffic: TrafficLayer
    private lateinit var mapView: MapView
    private lateinit var imageButtonTraffic: ImageButton
    private lateinit var imageButtonListLocation: ImageButton
    private lateinit var imageButtonAboutProgram: ImageButton
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var mapKit: MapKit
    private lateinit var locationUser: UserLocationLayer
    private lateinit var locationUserPosition: Point
    private lateinit var userLocationObject: UserLocationObject
    private lateinit var tapGeoObject: GeoObjectTapListener
    private lateinit var inputListener: InputListener
    private lateinit var mapObjects: MapObjectCollection
    private lateinit var locationListener: LocationListener
    private lateinit var presenter: MapContract.Presenter
    private lateinit var contractService: DrivingRouteContract.Service

    private var mapObjectTapListener: MapObjectTapListener = MapObjectTapListener { mapObject, p1 ->
        val markerDto = mapObject.userData as MarkerDto
        startInformationLocationFragment(markerDto)
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitInitializer.initializer()
        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_map)

        hideActionBar()

        if (checkPermission()) {
            init()
            onClick()
            presenter.showAllMarkers()
        }
        isConnected()
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

    private fun hideActionBar() {
        supportActionBar?.hide()
    }

    private fun init() {
        presenter = MapPresenter(this)
        imageButtonTraffic = findViewById(R.id.imageButtonTraffic)
        imageButtonListLocation = findViewById(R.id.imageButtonListLocation)
        imageButtonAboutProgram = findViewById(R.id.imageButtonInfo)
        constraintLayout = findViewById(R.id.constraintLayoutMapActivity)
        mapView = findViewById(R.id.yandexMap)
        mapKit = MapKitFactory.getInstance()
        traffic = mapKit.createTrafficLayer(mapView.mapWindow)
        mapObjects = mapView.map.mapObjects.addCollection()
        contractService = DrivingRouteService(
            this,
            mapObjects
        )
        moveLocationUser()
        labelLocationUser()
        registerOnTabGeoListener()
    }

    private fun registerOnTabGeoListener() {
        tapGeoObject = GeoObjectTapListener { event ->

            val metaData = event
                .geoObject
                .metadataContainer
                .getItem(GeoObjectSelectionMetadata::class.java)

            if (metaData != null) {
                mapView.map.selectGeoObject(metaData.id, metaData.layerId)
                val point = event.geoObject.geometry[0].point
                if (point != null) {
                    val geocoder = Geocoder(this@MapActivity, Locale.getDefault())
                    val address = geocoder.getFromLocation(point.latitude, point.longitude, 1)
                    if (address != null) {
                        startAddPointFragment(point, address[0].getAddressLine(0))
                    }
                }
            }
            metaData != null
        }

        inputListener = object : InputListener {
            override fun onMapTap(map: Map, point: Point) {
                mapView.map.deselectGeoObject()
            }

            override fun onMapLongTap(p0: Map, p1: Point) {
                mapView.map.deselectGeoObject()
            }
        }
        mapView.map.addTapListener(tapGeoObject)
        mapView.map.addInputListener(inputListener)
    }

    private fun labelLocationUser() {
        locationUser = mapKit.createUserLocationLayer(mapView.mapWindow)
        locationUser.isVisible = true
        userLocationObject = UserLocationObject(this, locationUser)
        locationUser.setObjectListener(userLocationObject)
    }

    private fun moveLocationUser() {
        val locationManager = MapKitFactory.getInstance().createLocationManager()
        locationListener = object : LocationListener {
            override fun onLocationUpdated(location: Location) {
                locationUserPosition = location.position
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
        }
        locationManager.requestSingleUpdate(locationListener)
    }

    private fun onClick() {
        imageButtonTraffic.setOnClickListener {
            traffic.isTrafficVisible = !traffic.isTrafficVisible
        }

        imageButtonListLocation.setOnClickListener {
            val intent = Intent(
                this@MapActivity,
                ListLocationActivity::class.java
            )
            startActivity(intent)
        }
        imageButtonAboutProgram.setOnClickListener {
            val intent = Intent(
                this@MapActivity,
                AboutProgramActivity::class.java
            )
            startActivity(intent)
        }
    }

    private fun startAddPointFragment(point: Point, address: String) {
        val bundle = Bundle()
        bundle.putString(KeyValue.KEY_ADDRESS, address)
        bundle.putDouble(KeyValue.KEY_LATITUDE, point.latitude)
        bundle.putDouble(KeyValue.KEY_LONGITUDE, point.longitude)
        removeFragments()
        supportFragmentManager
            .beginTransaction()
            .add(
                R.id.fragmentAddPointOrInformationLocation,
                AddPointFragment::class.java,
                bundle
            ).commit()
    }

    private fun startInformationLocationFragment(markerDto: MarkerDto) {
        val bundle = Bundle()
        bundle.putSerializable(KeyValue.KEY_MARKER_DTO, markerDto)
        removeFragments()
        supportFragmentManager
            .beginTransaction()
            .add(
                R.id.fragmentAddPointOrInformationLocation,
                InformationLocationFragment::class.java,
                bundle
            ).commit()
    }

    private fun removeFragments(){
        supportFragmentManager.apply {
            for (fragment: Fragment in fragments) {
                beginTransaction().remove(fragment).commit()
            }
        }
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

    override fun showAllMarkers(value: List<MarkerDto>) {
        value.stream().forEach { dto -> addMarker(dto) }
    }

    private fun addMarker(dto: MarkerDto) {
        val marker = mapView
            .map
            .mapObjects
            .addPlacemark(
                Point(dto.getLatitude(), dto.getLongitude()),
                ImageProvider.fromResource(this, R.drawable.location_marker),
                IconStyle().setFlat(true).setAnchor(PointF(0.7f, 0.7f))
            )
        marker.userData = dto
        marker.addTapListener(mapObjectTapListener)
    }

    private fun startRouteFragment(nameLocation: String){
        val bundle = Bundle()
        bundle.putString(KeyValue.KEY_NAME_LOCATION, nameLocation)
        supportFragmentManager
            .beginTransaction()
            .add(
                R.id.fragmentAddPointOrInformationLocation,
                RouteFragment::class.java,
                bundle
            ).commit()
    }

    override fun buildDrivingRouter(nameLocation: String, endPosition: Point) {
        startRouteFragment(nameLocation)
        contractService.buildDrivingRouter(locationUserPosition, endPosition)
    }

    override fun deleteRouter() {
        contractService.deleteRouter()
    }

    private fun isConnected(){
        val networkIsConnectedService: NetworkIsConnectedService =
            ViewModelProvider(this)[NetworkIsConnectedService::class.java]
        networkIsConnectedService.isConnected(
            networkIsConnectedService,
            this,
            constraintLayout
        )
    }

}