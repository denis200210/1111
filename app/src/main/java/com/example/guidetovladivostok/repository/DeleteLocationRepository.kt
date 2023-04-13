package com.example.guidetovladivostok.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DeleteLocationRepository {

    private val database = Firebase.firestore

    fun deleteLocation(documentId: String): Task<Void>{
        return database
            .collection(Database.MARKERS_TAG)
            .document(documentId)
            .delete()
    }
}