package com.example.guidetovladivostok.model

import com.example.guidetovladivostok.exception.ErrorRequestException
import com.example.guidetovladivostok.presenter.DeleteLocationContract
import com.example.guidetovladivostok.repository.DeleteLocationRepository

class DeleteLocationModel: DeleteLocationContract.Model {

    private val repository = DeleteLocationRepository()

    override fun deleteLocation(documentId: String) {
        repository
            .deleteLocation(documentId)
            .addOnSuccessListener {  }
            .addOnFailureListener {
                throw ErrorRequestException("Не удалось выполнить запрос")
            }
    }
}