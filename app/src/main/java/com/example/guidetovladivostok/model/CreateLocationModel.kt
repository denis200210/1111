package com.example.guidetovladivostok.model

import com.example.guidetovladivostok.entity.MarkerEntity
import com.example.guidetovladivostok.exception.CreateLocationException
import com.example.guidetovladivostok.presenter.CreateLocationContract
import com.example.guidetovladivostok.repository.CreateLocationRepository

class CreateLocationModel : CreateLocationContract.Model<MarkerEntity, CallBackOnClose>{

    private val repository = CreateLocationRepository()

    override fun putMarker(markerEntity: MarkerEntity, callBackOnClose: CallBackOnClose){
        repository
            .putMarker(markerEntity)
            .addOnSuccessListener {
                callBackOnClose.onClose()
            }.addOnFailureListener{
                throw CreateLocationException("Не удалось добавить локацию.")
            }
    }
}