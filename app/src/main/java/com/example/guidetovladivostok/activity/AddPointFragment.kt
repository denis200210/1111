package com.example.guidetovladivostok.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.guidetovladivostok.R

class AddPointFragment : Fragment() {

    private val NONE = "none"

    private var latitude = 0.0
    private var longitude = 0.0
    private lateinit var address: String
    private lateinit var buttonAddLocation: Button
    private lateinit var buttonClose: ImageButton
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_point, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonAddLocation = view.findViewById(R.id.addLocation)
        buttonClose = view.findViewById(R.id.closeButton)
        textView = view.findViewById(R.id.textViewAddress)

        if(savedInstanceState == null) {
            latitude = arguments?.getDouble(KeyValue.KEY_LATITUDE) ?: 0.0
            longitude = arguments?.getDouble(KeyValue.KEY_LONGITUDE) ?: 0.0
            address = arguments?.getString(KeyValue.KEY_ADDRESS, NONE) ?: NONE
        } else{
            latitude = savedInstanceState.getDouble(KeyValue.KEY_LATITUDE)
            longitude = savedInstanceState.getDouble(KeyValue.KEY_LONGITUDE)
            address = savedInstanceState.getString(KeyValue.KEY_ADDRESS, NONE)
        }
        textView.text = address
        buttonClose.setOnClickListener(View.OnClickListener {
            onClickCloseFragment()
        })
        buttonAddLocation.setOnClickListener(View.OnClickListener {
            onClickAddLocation(address, latitude, longitude)
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble(KeyValue.KEY_LATITUDE, latitude)
        outState.putDouble(KeyValue.KEY_LONGITUDE, longitude)
        outState.putString(KeyValue.KEY_ADDRESS, address)
    }

    private fun onClickCloseFragment() {
        activity
            ?.supportFragmentManager
            ?.beginTransaction()
            ?.remove(this)
            ?.commit()
    }

    private fun onClickAddLocation(address: String, latitude: Double, longitude: Double){
        val bundle = Bundle()
        bundle.putString(KeyValue.KEY_ADDRESS, address)
        bundle.putDouble(KeyValue.KEY_LATITUDE, latitude)
        bundle.putDouble(KeyValue.KEY_LONGITUDE, longitude)
        bundle.putBoolean(KeyValue.KEY_IS_CREATE, true)
        activity
            ?.supportFragmentManager
            ?.beginTransaction()
            ?.add(R.id.constraintLayoutMapActivity, CreateOrChangeLocationFragment::class.java, bundle)
            ?.remove(this)
            ?.addToBackStack(null)
            ?.commit()
    }

}