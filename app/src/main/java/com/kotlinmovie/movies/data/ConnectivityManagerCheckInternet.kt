package com.kotlinmovie.movies.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import android.net.NetworkInfo

import android.net.ConnectivityManager
import androidx.core.content.ContextCompat

import androidx.core.content.ContextCompat.getSystemService


class ConnectivityManagerCheckInternet : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (ConnectivityManager.CONNECTIVITY_ACTION == intent.action) {
            Toast.makeText(context,
                    "internet connection has changed", Toast.LENGTH_SHORT).show()
        }
    }
}

