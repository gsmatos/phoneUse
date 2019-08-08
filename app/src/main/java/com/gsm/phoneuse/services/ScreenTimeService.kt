package com.gsm.phoneuse.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.gsm.phoneuse.MainActivity
import com.gsm.phoneuse.R


class ScreenTimeService : Service() {
    private val serviceId = 1

    private lateinit var receiver: ScreenViewReceiver

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        val filter = IntentFilter(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        receiver = ScreenViewReceiver()
        registerReceiver(receiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val mainActivityIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, mainActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder: NotificationCompat.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = getString(R.string.notification_channel_screen_time)
            val importance = NotificationManager.IMPORTANCE_LOW
            val channelId = "LOOK_COUNTS_CHANNEL_ID"
            val channel = NotificationChannel(channelId, channelName, importance)
            channel.setShowBadge(false)

            builder = NotificationCompat.Builder(this, channelId)

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        } else {
            builder = NotificationCompat.Builder(this)
        }


        with(builder) {
            setContentTitle(getString(R.string.app_name))
            setContentText(getString(R.string.notification_service_description))
            setWhen(System.currentTimeMillis())
            setContentIntent(pendingIntent)
            priority = NotificationCompat.PRIORITY_DEFAULT
            setVisibility(NotificationCompat.VISIBILITY_SECRET)
        }

        val notification = builder.build()

        startForeground(serviceId, notification)
        return super.onStartCommand(mainActivityIntent, flags, startId)
    }
}