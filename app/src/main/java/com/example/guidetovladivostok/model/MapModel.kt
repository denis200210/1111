package com.example.guidetovladivostok.model

import com.example.guidetovladivostok.dto.MarkerDto
import com.example.guidetovladivostok.entity.MarkerEntity
import com.example.guidetovladivostok.exception.ErrorRequestException
import com.example.guidetovladivostok.exception.MarkersNotFoundException
import com.example.guidetovladivostok.mapper.MarkerMapper
import com.example.guidetovladivostok.repository.MapRepository
import com.google.firebase.firestore.ktx.toObject

class MapModel {

    private val repository = MapRepository()
    private val mapper = MarkerMapper()

    fun getAllMarkers(callBackHandler: CallBackHandler<List<MarkerDto>>){
        repository
            .getAllMarkers()
            .addOnSuccessListener {result ->
                if(result.isEmpty){
                    throw MarkersNotFoundException("Не удалось получить данные.")
                }
                val list = result
                    .map { document -> mapper.setDocumentId(document.id).getEntityToDto(document.toObject<MarkerEntity>()) }
                    .toList()
                callBackHandler.execute(list)
            }.addOnFailureListener {
                throw ErrorRequestException("Не удалось выполнить запрос.")
            }
    }
}