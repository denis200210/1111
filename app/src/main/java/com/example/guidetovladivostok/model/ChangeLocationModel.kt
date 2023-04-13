package com.example.guidetovladivostok.model

import com.example.guidetovladivostok.dto.MarkerDto
import com.example.guidetovladivostok.exception.ChangeLocationException
import com.example.guidetovladivostok.presenter.ChangeLocationContract
import com.example.guidetovladivostok.repository.ChangeLocationRepository

class ChangeLocationModel: ChangeLocationContract.Model<MarkerDto, CallBackOnClose> {

    private val repository = ChangeLocationRepository()

    override fun updateMarker(markerDto: MarkerDto, callBackOnClose: CallBackOnClose){
        repository
            .updateMarker(markerDto)
            .addOnSuccessListener {
                callBackOnClose.onClose()
            }.addOnFailureListener{
                throw ChangeLocationException("Не удалось изменить локацию.")
            }
    }
}