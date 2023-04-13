package com.example.guidetovladivostok.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.example.guidetovladivostok.R
import com.example.guidetovladivostok.activity.common.NetworkIsConnectedService

/** Активность окна с информацией о приложении **/
class AboutProgramActivity: AppCompatActivity() {

    private lateinit var constraintLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_program)
        constraintLayout = findViewById(R.id.constraintLayoutAboutActivity)
        isConnected()
    }

    private fun isConnected(){
        val networkIsConnectedService: NetworkIsConnectedService =
            ViewModelProvider(this)[NetworkIsConnectedService::class.java]
        networkIsConnectedService.isConnected(
            networkIsConnectedService,
            this,
            constraintLayout
        )
    }
}