package com.example.guidetovladivostok.repository

import com.example.guidetovladivostok.dto.MarkerDto
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ChangeLocationRepository {

    private val database = Firebase.firestore

    fun updateMarker(markerDto: MarkerDto): Task<Void> {
        return database
            .collection(Database.MARKERS_TAG)
            .document(markerDto.getDocumentId())
            .update(
                "nameLocation", markerDto.getNameLocation(),
                "informationLocation", markerDto.getInformationLocation()
            )
    }
}