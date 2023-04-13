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
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.guidetovladivostok.R
import com.example.guidetovladivostok.dto.MarkerDto
import com.example.guidetovladivostok.entity.MarkerEntity
import com.example.guidetovladivostok.presenter.*
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.yandex.mapkit.geometry.Point

class CreateOrChangeLocationFragment
    : Fragment(),
    CreateLocationContract.View,
    ChangeLocationContract.View {

    private val NONE = "none"

    private lateinit var contractPresenterCreate: CreateLocationContract.Presenter<MarkerEntity>
    private lateinit var contractPresenterChange: ChangeLocationContract.Presenter<MarkerDto>
    private var isEdit: Boolean = false
    private var isCreate: Boolean? = null
    private var latitude = 0.0
    private var longitude = 0.0
    private var markerDto: MarkerDto? = null
    private lateinit var address: String
    private lateinit var editTextNameLocation: TextInputEditText
    private lateinit var editTextInformationLocation: TextInputEditText
    private lateinit var textInputNameLocation: TextInputLayout
    private lateinit var textInputInformationLocation: TextInputLayout
    private lateinit var textAddress: TextView
    private lateinit var buttonClose: ImageButton
    private lateinit var buttonEdit: ImageButton
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

        contractPresenterCreate = CreateLocationPresenter(this)
        contractPresenterChange = ChangeLocationPresenter(this)
        editTextNameLocation = view.findViewById(R.id.nameLocation)
        editTextInformationLocation = view.findViewById(R.id.informationAboutLocation)
        textInputNameLocation = view.findViewById(R.id.textInputLayoutNameLocation)
        textInputInformationLocation =
            view.findViewById(R.id.textInputLayoutInformationAboutLocation)
        textAddress = view.findViewById(R.id.addressLocation)
        buttonClose = view.findViewById(R.id.closeButton)
        buttonEdit = view.findViewById(R.id.editButton)
        buttonSave = view.findViewById(R.id.saveButton)

        isCreate = savedInstanceState?.getBoolean(KeyValue.KEY_IS_CREATE)
            ?: arguments?.getBoolean(KeyValue.KEY_IS_CREATE)

        if (isCreate == true) {
            isCreateInit(savedInstanceState)
            buttonSave.setText(R.string.save)
        } else {
            isChangeInit(savedInstanceState)
            buttonSave.setText(R.string.update)
            buttonEdit.isVisible = true
            isNotEdit()
        }

        textAddress.text = address

        buttonClose.setOnClickListener(View.OnClickListener {
            onClickCloseFragment()
        })

        buttonEdit.setOnClickListener(View.OnClickListener {
            if (isEdit) {
                isEdit()
            } else {
                isNotEdit()
            }
        })

        buttonSave.setOnClickListener(View.OnClickListener {
            if (isCreate == true) {
                onClickSaveOrUpdate(true)
            } else {
                onClickSaveOrUpdate(false)
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (isCreate != null) {
            outState.putBoolean(KeyValue.KEY_IS_CREATE, isCreate!!)
            if (isCreate == true) {
                outState.putDouble(KeyValue.KEY_LATITUDE, latitude)
                outState.putDouble(KeyValue.KEY_LONGITUDE, longitude)
                outState.putString(KeyValue.KEY_ADDRESS, address)
            } else {
                outState.putSerializable(KeyValue.KEY_MARKER_DTO, markerDto)
            }
        }
    }

    private fun isNotEdit() {
        isEdit = true
        buttonSave.isVisible = false
        editTextNameLocation.isEnabled = false
        editTextNameLocation.isCursorVisible = false
        editTextNameLocation.isClickable = false
        editTextInformationLocation.isEnabled = false
        editTextInformationLocation.isCursorVisible = false
        editTextInformationLocation.isClickable = false
        textInputNameLocation.boxStrokeWidth = 0
        textInputNameLocation.isCounterEnabled = false
        textInputInformationLocation.boxStrokeWidth = 0
        textInputInformationLocation.isCounterEnabled = false
    }

    private fun isEdit() {
        isEdit = false
        buttonSave.isVisible = true
        editTextNameLocation.isEnabled = true
        editTextNameLocation.isCursorVisible = true
        editTextNameLocation.isClickable = true
        editTextInformationLocation.isEnabled = true
        editTextInformationLocation.isCursorVisible = true
        editTextInformationLocation.isClickable = true
        textInputNameLocation.boxStrokeWidth = 4
        textInputNameLocation.isCounterEnabled = true
        textInputInformationLocation.boxStrokeWidth = 4
        textInputInformationLocation.isCounterEnabled = true
    }

    private fun isCreateInit(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            latitude = arguments?.getDouble(KeyValue.KEY_LATITUDE) ?: 0.0
            longitude = arguments?.getDouble(KeyValue.KEY_LONGITUDE) ?: 0.0
            address = arguments?.getString(KeyValue.KEY_ADDRESS, NONE) ?: NONE
        } else {
            latitude = savedInstanceState.getDouble(KeyValue.KEY_LATITUDE)
            longitude = savedInstanceState.getDouble(KeyValue.KEY_LONGITUDE)
            address = savedInstanceState.getString(KeyValue.KEY_ADDRESS, NONE)
        }
    }

    private fun isChangeInit(savedInstanceState: Bundle?) {
        markerDto = if (savedInstanceState == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getSerializable(KeyValue.KEY_MARKER_DTO, MarkerDto::class.java)
            } else {
                arguments?.getSerializable(KeyValue.KEY_MARKER_DTO) as MarkerDto?
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                savedInstanceState.getSerializable(KeyValue.KEY_MARKER_DTO, MarkerDto::class.java)
            } else {
                savedInstanceState.getSerializable(KeyValue.KEY_MARKER_DTO) as MarkerDto
            }
        }

        latitude = markerDto?.getLatitude() ?: 0.0
        longitude = markerDto?.getLongitude() ?: 0.0
        address = markerDto?.getAddress() ?: NONE

        editTextNameLocation.setText(markerDto?.getNameLocation() ?: NONE)
        editTextInformationLocation.setText(markerDto?.getInformationLocation() ?: NONE)
    }

    private fun onClickCloseFragment() {
        closeFragment()
    }

    private fun onClickSaveOrUpdate(isSave: Boolean) {
        val nameLocation = editTextNameLocation.text.toString().trim()
        val informationLocation = editTextInformationLocation.text.toString().trim()
        if (nameLocation.isNotEmpty() && informationLocation.isNotEmpty()) {
            if (isSave) {
                val markerEntity = MarkerEntity(
                    Point(latitude, longitude),
                    nameLocation,
                    informationLocation,
                    address
                )
                contractPresenterCreate.buttonCreateLocation(markerEntity)
            } else {
                val markerDto = MarkerDto(
                    markerDto!!.getDocumentId(),
                    Point(latitude, longitude),
                    nameLocation,
                    informationLocation,
                    address
                )
                contractPresenterChange.buttonChangeLocation(markerDto)
            }
            activity?.recreate()
            closeFragment()
        } else {
            Toast.makeText(
                context,
                "Вы не ввели название локации или ее описание",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun closeFragment() {
        activity
            ?.supportFragmentManager
            ?.beginTransaction()
            ?.remove(this)
            ?.commit()
    }

}