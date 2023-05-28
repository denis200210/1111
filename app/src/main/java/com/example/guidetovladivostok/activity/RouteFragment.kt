package com.example.guidetovladivostok.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.guidetovladivostok.R
import com.example.guidetovladivostok.contract.DrivingRouteContract

/** Класс отвечающий за показ окна с информацией о маршруте **/
class RouteFragment : Fragment() {

    private val NONE = "NONE"

    private lateinit var textNameLocation: TextView
    private lateinit var buttonClose: ImageButton
    private lateinit var nameLocation: String
    private lateinit var intermediateNameLocation: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_route, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textNameLocation = view.findViewById(R.id.nameLocation)
        buttonClose = view.findViewById(R.id.closeButton)

        nameLocation = if (savedInstanceState == null){
            arguments?.getString(KeyValue.KEY_NAME_LOCATION) ?: NONE
        } else {
            savedInstanceState.getString(KeyValue.KEY_NAME_LOCATION, NONE)
        }

        intermediateNameLocation = if (savedInstanceState == null){
            arguments?.getString(KeyValue.KEY_INTERMEDIATE_NAME_LOCATION) ?: NONE
        } else {
            savedInstanceState.getString(KeyValue.KEY_INTERMEDIATE_NAME_LOCATION, NONE)
        }

        if(intermediateNameLocation == NONE){
            textNameLocation.text = "Маршрут до: $nameLocation"
        }else{
            textNameLocation.text = "Маршрут до: $nameLocation\n" +
                    "Через: $intermediateNameLocation"
        }

        buttonClose.setOnClickListener {
            (activity as DrivingRouteContract.View).deleteRouter()
            closeFragment()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KeyValue.KEY_NAME_LOCATION, nameLocation)
    }

    private fun closeFragment(){
        activity
            ?.supportFragmentManager
            ?.beginTransaction()
            ?.remove(this)
            ?.commit()
    }
}