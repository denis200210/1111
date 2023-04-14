package com.example.guidetovladivostok.presenter

import com.example.guidetovladivostok.contract.CreateLocationContract
import com.example.guidetovladivostok.entity.MarkerEntity
import com.example.guidetovladivostok.model.CallBackOnClose
import com.example.guidetovladivostok.model.CreateLocationModel

class CreateLocationPresenter
constructor(contractView: CreateLocationContract.View)
    : CreateLocationContract.Presenter<MarkerEntity> {

    private val contractView: CreateLocationContract.View
    private val contractModel: CreateLocationContract.Model<MarkerEntity, CallBackOnClose>
    private val callBackOnClose = object : CallBackOnClose{
        override fun onClose() {
            contractView.closeFragment()
        }
    }

    init {
        this.contractView = contractView
        this.contractModel = CreateLocationModel()
    }

    override fun buttonCreateLocation(value: MarkerEntity) {
        contractModel.putMarker(value, callBackOnClose)
    }
}