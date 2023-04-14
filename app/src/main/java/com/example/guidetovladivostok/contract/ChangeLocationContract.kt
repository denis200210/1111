package com.example.guidetovladivostok.contract

interface ChangeLocationContract {
    interface View{
        fun closeFragment()
    }
    interface Presenter<V>{
        fun buttonChangeLocation(value: V)
    }
    interface Model<V, CallBack>{
        fun updateMarker(value:V, callBack: CallBack)
    }
}