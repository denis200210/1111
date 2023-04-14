package com.example.guidetovladivostok.presenter

import com.example.guidetovladivostok.contract.ListLocationContract
import com.example.guidetovladivostok.dto.MarkerDto
import com.example.guidetovladivostok.model.CallBackHandler
import com.example.guidetovladivostok.model.ListLocationModel

class ListLocationPresenter: ListLocationContract.Presenter {

    private lateinit var contractView: ListLocationContract.View<List<MarkerDto>>
    private val contractModel: ListLocationContract.Model<CallBackHandler<List<MarkerDto>>>
    private val callBackHandler = object : CallBackHandler<List<MarkerDto>>{
        override fun execute(value: List<MarkerDto>) {
            contractView.showListLocation(value)
        }
    }

    constructor(
        contractView: ListLocationContract.View<List<MarkerDto>>
    ) {
        this.contractView = contractView
        this.contractModel = ListLocationModel()
    }


    override fun showListLocation() {
        contractModel.getListLocation(callBackHandler)
    }
}