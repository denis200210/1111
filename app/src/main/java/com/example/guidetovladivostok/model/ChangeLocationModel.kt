package com.example.guidetovladivostok.model

import com.example.guidetovladivostok.dto.MarkerDto
import com.example.guidetovladivostok.exception.ChangeLocationException
import com.example.guidetovladivostok.repository.ChangeLocationRepository

class ChangeLocationModel {

    private val repository = ChangeLocationRepository()

    fun updateMarker(markerDto: MarkerDto){
        repository
            .updateMarker(markerDto)
            .addOnSuccessListener {

            }.addOnFailureListener{
                throw ChangeLocationException("Не удалось изменить локацию.")
            }
    }
}