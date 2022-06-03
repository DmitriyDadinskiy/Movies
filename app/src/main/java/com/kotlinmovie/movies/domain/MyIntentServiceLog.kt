package com.kotlinmovie.movies.domain

import android.app.IntentService
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import android.os.Environment
import android.provider.ContactsContract
import android.widget.Toast
import java.io.*


const val TAG = "ServiceTAG"
const val MAIN_SERVICE_GET_EVENT = "MyIntentServiceGetEvent"


@RequiresApi(Build.VERSION_CODES.CUPCAKE)
class MyIntentServiceLog : IntentService("MyIntentServiceLog") {

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, " ${intent?.getStringExtra(MAIN_SERVICE_GET_EVENT)}")
        val nameLogFile = "LogFile.txt"
        try {
            val openFile = openFileOutput(nameLogFile, MODE_APPEND)
            val osw = OutputStreamWriter(openFile)
            osw.write("${intent?.getStringExtra(MAIN_SERVICE_GET_EVENT)}")
            osw.appendLine()
            osw.flush()
            osw.close()
            Log.e(TAG, "действие ${intent?.getStringExtra(MAIN_SERVICE_GET_EVENT)} записано")
        } catch (e: Exception) {
            Log.e(TAG, "ОШИБКА!!! файл не записан, $e")
        }
    }

}



