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
import com.example.guidetovladivostok.dto.MarkerDto
import com.example.guidetovladivostok.contract.DrivingRouteContract
import com.yandex.mapkit.geometry.Point

/** Класс отвечающий за показ информации о метке **/
class InformationLocationFragment : Fragment() {

    private lateinit var markerDto: MarkerDto
    private lateinit var buttonClose: ImageButton
    private lateinit var buttonMore: Button
    private lateinit var buttonGetDirections: Button
    private lateinit var textNameLocation: TextView
    private lateinit var textAddress: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_information_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        markerDto = if(savedInstanceState == null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getSerializable(KeyValue.KEY_MARKER_DTO, MarkerDto::class.java)!!
            } else{
                arguments?.getSerializable(KeyValue.KEY_MARKER_DTO) as MarkerDto
            }
        } else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                savedInstanceState.getSerializable(KeyValue.KEY_MARKER_DTO, MarkerDto::class.java)!!
            } else{
                savedInstanceState.getSerializable(KeyValue.KEY_MARKER_DTO) as MarkerDto
            }
        }

        buttonClose = view.findViewById(R.id.closeButton)
        buttonMore = view.findViewById(R.id.moreButton)
        buttonGetDirections = view.findViewById(R.id.getDirectionsButton)
        textNameLocation = view.findViewById(R.id.nameLocation)
        textAddress = view.findViewById(R.id.addressLocation)

        textNameLocation.text = markerDto.getNameLocation()
        textAddress.text = markerDto.getAddress()

        buttonClose.setOnClickListener {
            onClickCloseFragment()
        }

        buttonMore.setOnClickListener {
            onClickMore(markerDto)
        }

        buttonGetDirections.setOnClickListener {
            onClickGetDirections(markerDto.getNameLocation(), markerDto.getPoint())
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KeyValue.KEY_MARKER_DTO, markerDto)
    }

    private fun onClickGetDirections(nameLocation: String, markerPosition: Point){
        (activity as DrivingRouteContract.View).buildDrivingRouter(nameLocation, markerPosition)
        closeFragment()
    }

    private fun onClickMore(markerDto: MarkerDto){
        val bundle = Bundle()
        bundle.putSerializable(KeyValue.KEY_MARKER_DTO, markerDto)
        bundle.putBoolean(KeyValue.KEY_IS_CREATE, false)
        activity
            ?.supportFragmentManager
            ?.beginTransaction()
            ?.add(R.id.constraintLayoutMapActivity, CreateOrChangeLocationFragment::class.java, bundle)
            ?.remove(this)
            ?.addToBackStack(null)
            ?.commit()
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