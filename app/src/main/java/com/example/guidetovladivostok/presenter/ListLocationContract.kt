package com.example.guidetovladivostok.presenter

interface ListLocationContract {
    interface View<V>{
        fun showListLocation(value: V)
    }
    interface Presenter{
        fun showListLocation()
    }
    interface Model<CallBack>{
        fun getListLocation(callBack: CallBack)
    }
}