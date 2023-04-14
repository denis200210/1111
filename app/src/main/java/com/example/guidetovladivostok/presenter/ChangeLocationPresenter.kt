package com.example.guidetovladivostok.presenter

import com.example.guidetovladivostok.contract.ChangeLocationContract
import com.example.guidetovladivostok.dto.MarkerDto
import com.example.guidetovladivostok.model.CallBackOnClose
import com.example.guidetovladivostok.model.ChangeLocationModel

class ChangeLocationPresenter
constructor(contractView: ChangeLocationContract.View)
    : ChangeLocationContract.Presenter<MarkerDto>{

    private val contractView: ChangeLocationContract.View
    private val contractModel: ChangeLocationContract.Model<MarkerDto, CallBackOnClose>
    private val callBackOnClose = object : CallBackOnClose{
        override fun onClose() {
            contractView.closeFragment()
        }
    }

    init {
        this.contractView = contractView
        contractModel = ChangeLocationModel()
    }

    override fun buttonChangeLocation(value: MarkerDto) {
        contractModel.updateMarker(value, callBackOnClose)
    }
}