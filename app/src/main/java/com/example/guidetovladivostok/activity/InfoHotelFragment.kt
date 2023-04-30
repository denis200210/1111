package com.example.guidetovladivostok.activity

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.guidetovladivostok.R
import com.example.guidetovladivostok.contract.DrivingRouteContract
import com.example.guidetovladivostok.entity.HotelEntity
import com.yandex.mapkit.geometry.Point

class InfoHotelFragment : Fragment() {

    private lateinit var hotelEntity: HotelEntity
    private lateinit var buttonClose: ImageButton
    private lateinit var buttonGetDirections: Button
    private lateinit var textNameHotel: TextView
    private lateinit var textWorkingTime: TextView
    private lateinit var textAddressHotel: TextView
    private lateinit var textNumberPhone: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hotel_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hotelEntity = if(savedInstanceState == null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getSerializable(KeyValue.KEY_HOTEL_ENTITY, HotelEntity::class.java)!!
            } else{
                arguments?.getSerializable(KeyValue.KEY_HOTEL_ENTITY) as HotelEntity
            }
        } else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                savedInstanceState.getSerializable(KeyValue.KEY_HOTEL_ENTITY, HotelEntity::class.java)!!
            } else{
                savedInstanceState.getSerializable(KeyValue.KEY_HOTEL_ENTITY) as HotelEntity
            }
        }

        buttonClose = view.findViewById(R.id.closeButton)
        buttonGetDirections = view.findViewById(R.id.getDirectionsButton)
        textNameHotel = view.findViewById(R.id.nameHotel)
        textWorkingTime = view.findViewById(R.id.workingTime)
        textAddressHotel = view.findViewById(R.id.addressHotel)
        textNumberPhone = view.findViewById(R.id.phoneNumber)

        val hotelName = hotelEntity.getName()
        val workingTime = hotelEntity.getState()

        textNameHotel.text = "Отель: $hotelName"
        textWorkingTime.text = "Время работы: $workingTime"
        textAddressHotel.text = hotelEntity.getAddress()
        textNumberPhone.text = hotelEntity.getPhone()

        buttonClose.setOnClickListener {
            onClickCloseFragment()
        }

        buttonGetDirections.setOnClickListener {
            onClickGetDirections(hotelEntity.getName(), hotelEntity.getPoint())
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KeyValue.KEY_HOTEL_ENTITY, hotelEntity)
    }

    private fun onClickGetDirections(nameLocation: String, markerPosition: Point){
        (activity as DrivingRouteContract.View).buildDrivingRouter(nameLocation, markerPosition)
        closeFragment()
    }

    private fun onClickCloseFragment(){
        closeFragment()
    }

    private fun closeFragment(){
        activity
            ?.supportFragmentManager
            ?.beginTransaction()
            ?.remove(this)
            ?.commit()
    }
}