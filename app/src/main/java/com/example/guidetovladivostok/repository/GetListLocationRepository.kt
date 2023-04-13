package com.example.guidetovladivostok.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GetListLocationRepository {

    private val database = Firebase.firestore

    fun getListLocation(): Task<QuerySnapshot>{
        return database
            .collection(Database.MARKERS_TAG)
            .get()
    }
}