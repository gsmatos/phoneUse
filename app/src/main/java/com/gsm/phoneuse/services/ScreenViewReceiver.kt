package com.gsm.phoneuse.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.gsm.phoneuse.persistence.ScreenTime
import com.gsm.phoneuse.persistence.ScreenTimeDatabase
import com.gsm.phoneuse.persistence.timeKey
import com.gsm.phoneuse.utils.StopWatch
import kotlinx.coroutines.*

class ScreenViewReceiver : BroadcastReceiver() {

    private val stopWatch = StopWatch()

    private val serviceJob = Job()
    private val backgroundScope = CoroutineScope(Dispatchers.Main + serviceJob)

    override fun onReceive(context: Context?, intent: Intent?) {

        var screenOnTime = 0L

        if (context == null || intent == null) {
            return
        }
        when(intent.action){
            Intent.ACTION_SCREEN_ON -> {
                stopWatch.start()
            }
            Intent.ACTION_SCREEN_OFF -> {
                screenOnTime += stopWatch.stop()
            }
        }

        backgroundScope.launch {
            withContext(Dispatchers.IO){
                val dataSource = ScreenTimeDatabase.getInstance(context).sleepDatabaseDao
                 val savedTime = dataSource.getTime()
                 val newTime = (savedTime) + screenOnTime
                dataSource.set(ScreenTime(timeKey, newTime))
            }
        }
    }
}