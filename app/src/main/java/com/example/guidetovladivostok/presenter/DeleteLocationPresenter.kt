package com.example.guidetovladivostok.presenter

import com.example.guidetovladivostok.model.DeleteLocationModel

class DeleteLocationPresenter: DeleteLocationContract.Presenter {

    private val contractView: DeleteLocationContract.View
    private val contractModel: DeleteLocationContract.Model

    constructor(contractView: DeleteLocationContract.View){
        this.contractView = contractView
        this.contractModel = DeleteLocationModel()
    }

    override fun deleteLocation(documentId: String) {
        contractModel.deleteLocation(documentId)
    }
}