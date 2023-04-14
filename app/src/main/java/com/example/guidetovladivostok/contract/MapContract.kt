package com.example.guidetovladivostok.contract

interface MapContract {
    interface View<V>{
        fun showAllMarkers(value: V)
    }
    interface Presenter{
        fun showAllMarkers()
    }
    interface Model<CallBack>{
        fun getListLocation(callBack: CallBack)
    }
}