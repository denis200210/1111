package com.example.guidetovladivostok.presenter

interface CreateLocationContract {
    interface View{
        fun closeFragment()
    }
    interface Presenter<V>{
        fun buttonCreateLocation(value: V)
    }
    interface Model<V,CallBack>{
        fun putMarker(value: V, callBack: CallBack)
    }
}