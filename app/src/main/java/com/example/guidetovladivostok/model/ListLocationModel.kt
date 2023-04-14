package com.example.guidetovladivostok.model

import com.example.guidetovladivostok.dto.MarkerDto
import com.example.guidetovladivostok.entity.MarkerEntity
import com.example.guidetovladivostok.exception.ErrorRequestException
import com.example.guidetovladivostok.mapper.MarkerMapper
import com.example.guidetovladivostok.contract.ListLocationContract
import com.example.guidetovladivostok.contract.MapContract
import com.example.guidetovladivostok.repository.GetListLocationRepository

class ListLocationModel
    : ListLocationContract.Model<CallBackHandler<List<MarkerDto>>>,
    MapContract.Model<CallBackHandler<List<MarkerDto>>> {

    private val repository = GetListLocationRepository()
    private val mapper = MarkerMapper()

    override fun getListLocation(callBack: CallBackHandler<List<MarkerDto>>) {
        repository
            .getListLocation()
            .addOnCompleteListener { result ->
                val listDto = result
                    .result
                    .map { value ->
                        mapper.setDocumentId(value.id)
                            .getEntityToDto(value.toObject(MarkerEntity::class.java))
                    }
                    .toList()
                callBack.execute(listDto)
            }
            .addOnFailureListener {
                throw ErrorRequestException("Не удалось выполнить запрос")
            }
    }
}