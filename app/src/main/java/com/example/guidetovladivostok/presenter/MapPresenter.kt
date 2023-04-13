package com.example.guidetovladivostok.presenter

import com.example.guidetovladivostok.dto.MarkerDto
import com.example.guidetovladivostok.model.CallBackHandler
import com.example.guidetovladivostok.model.ListLocationModel

class MapPresenter: MapContract.Presenter {

    private lateinit var contractView: MapContract.View<List<MarkerDto>>
    private val contractModel: MapContract.Model<CallBackHandler<List<MarkerDto>>>
    private val callBackHandler = object : CallBackHandler<List<MarkerDto>>{
        override fun execute(value: List<MarkerDto>) {
            contractView.showAllMarkers(value)
        }
    }

    constructor(contractView: MapContract.View<List<MarkerDto>>){
        this.contractView = contractView
        this.contractModel = ListLocationModel()
    }

    override fun showAllMarkers() {
        contractModel.getListLocation(callBackHandler)
    }
}