package com.iskander.memchat.remote.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import javax.inject.Singleton

@Singleton
class NetWorkHandler (private val context: Context){
    val isConnected get() = context.networkInfo?.isConnected
}

val Context.networkInfo: NetworkInfo? get() =
    (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
