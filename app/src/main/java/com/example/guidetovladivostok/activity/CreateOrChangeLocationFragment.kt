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
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class CreateOrChangeLocationFragment : Fragment() {

    private val NONE = "none"

    private var isCreate: Boolean? = null
    private var latitude = 0.0
    private var longitude = 0.0
    private lateinit var address: String
    private lateinit var editTextNameLocation: TextInputEditText
    private lateinit var editTextInformationLocation: TextInputEditText
    private lateinit var textAddress: TextView
    private lateinit var buttonClose: ImageButton
    private lateinit var buttonSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_or_change_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextNameLocation = view.findViewById(R.id.nameLocation)
        editTextInformationLocation = view.findViewById(R.id.informationAboutLocation)
        textAddress = view.findViewById(R.id.addressLocation)
        buttonClose = view.findViewById(R.id.closeButton)
        buttonSave = view.findViewById(R.id.saveButton)

        if (savedInstanceState == null) {
            isCreate = arguments?.getBoolean(KeyValue.KEY_IS_CREATE)
            latitude = arguments?.getDouble(KeyValue.KEY_LATITUDE) ?: 0.0
            longitude = arguments?.getDouble(KeyValue.KEY_LONGITUDE) ?: 0.0
            address = arguments?.getString(KeyValue.KEY_ADDRESS, NONE) ?: NONE
        } else {
            isCreate = savedInstanceState.getBoolean(KeyValue.KEY_IS_CREATE)
            latitude = savedInstanceState.getDouble(KeyValue.KEY_LATITUDE)
            longitude = savedInstanceState.getDouble(KeyValue.KEY_LONGITUDE)
            address = savedInstanceState.getString(KeyValue.KEY_ADDRESS, NONE)
        }

        textAddress.text = address

        buttonClose.setOnClickListener(View.OnClickListener {
            onClickCloseFragment()
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (isCreate != null) {
            outState.putBoolean(KeyValue.KEY_IS_CREATE, isCreate!!)
        }
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

    private fun onClickSave() {

    }

}