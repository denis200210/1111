package com.example.guidetovladivostok.activity.common

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.guidetovladivostok.R
import com.google.android.material.snackbar.Snackbar

class NetworkIsConnectedService : AndroidViewModel {

    private val mConnected: MutableLiveData<Boolean> = MutableLiveData()
    val VISIBLE_LONG = Snackbar.LENGTH_LONG
    val VISIBLE_SHORT = Snackbar.LENGTH_SHORT
    val NO_CONNECTED_TO_NETWORK = "Нет подключения к интернету"

    constructor(application: Application) : super(application) {
        val connectivityManager: ConnectivityManager = application
            .getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            mConnected.value = true
            return
        }

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        var networkCallBack: ConnectivityManager.NetworkCallback =
            object : ConnectivityManager.NetworkCallback(){
                val availableNetwork: MutableSet<Network> = HashSet()

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    availableNetwork.add(network)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    availableNetwork.remove(network)
                    mConnected.postValue(availableNetwork.isNotEmpty())
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    availableNetwork.clear()
                    mConnected.postValue(availableNetwork.isNotEmpty())
                }
            }

        connectivityManager
            .registerNetworkCallback(networkRequest, networkCallBack)
    }

    fun isConnected(
        networkIsConnectedService: NetworkIsConnectedService,
        activity: AppCompatActivity,
        view: View
    ){
        networkIsConnectedService
            .getConnected()
            .observe(activity){
                networkIsConnectedService.setSnackbar(
                    view,
                    NO_CONNECTED_TO_NETWORK,
                    VISIBLE_LONG
                )
            }

    }

    private fun getConnected(): MutableLiveData<Boolean> = mConnected

    private fun setSnackbar(view: View, message: String, flag: Int){
        Snackbar
            .make(view, message, flag)
            .setBackgroundTint(
                getApplication<Application>().resources.getColor(
                    R.color.color_snackbar_not_connected,
                    null
                )
            ).show()
    }
}