package com.example.guidetovladivostok

import android.content.Context
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider

class UserLocationObject constructor(
    context: Context,
    locationUser: UserLocationLayer
) : UserLocationObjectListener {

    private val context: Context
    private val locationUser: UserLocationLayer

    init {
        this.context = context
        this.locationUser = locationUser
    }

    override fun onObjectAdded(userLocationView: UserLocationView) {
        userLocationView
            .arrow
            .setIcon(ImageProvider.fromResource(context, R.drawable.navigation_arrow))

    }

    override fun onObjectRemoved(p0: UserLocationView) {

    }

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {

    }
}