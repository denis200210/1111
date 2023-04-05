package com.example.guidetovladivostok.model

import com.example.guidetovladivostok.entity.MarkerEntity
import com.example.guidetovladivostok.exception.CreateLocationException
import com.example.guidetovladivostok.repository.CreateLocationRepository

class CreateLocationModel {

    private val repository = CreateLocationRepository()

    fun putMarker(markerEntity: MarkerEntity){
        repository
            .putMarker(markerEntity)
            .addOnSuccessListener {

            }.addOnFailureListener{
                throw CreateLocationException("Не удалось добавить локацию.")
            }
    }
}