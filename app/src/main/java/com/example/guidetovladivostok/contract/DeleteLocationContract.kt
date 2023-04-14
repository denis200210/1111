package com.example.guidetovladivostok.contract

interface DeleteLocationContract {
    interface View{
    }
    interface Presenter{
        fun deleteLocation(documentId: String)
    }
    interface Model{
        fun deleteLocation(documentId: String)
    }
}