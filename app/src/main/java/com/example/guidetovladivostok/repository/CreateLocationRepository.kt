package com.example.guidetovladivostok.repository

import com.example.guidetovladivostok.entity.MarkerEntity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CreateLocationRepository {

    private val database = Firebase.firestore

    fun putMarker(markerEntity: MarkerEntity): Task<Void>{
        return database
            .collection(Database.MARKERS_TAG)
            .document()
            .set(markerEntity)
    }
}