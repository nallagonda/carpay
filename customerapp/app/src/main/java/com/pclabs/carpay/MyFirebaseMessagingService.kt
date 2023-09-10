package com.pclabs.carpay

import android.R
import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.util.Log
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.pclabs.carpay.ui.HomeActivity
import com.pclabs.carpay.ui.LogoutActivity
import java.lang.Exception


class MyFirebaseMessagingService : FirebaseMessagingService() {
    companion object {
        private val TAG = "simpleClassName"

        // The id of the channel.
        private val channelId = "my_channel_01"
        private val notifyID: Int = 1
        private val importance = NotificationManagerCompat.IMPORTANCE_DEFAULT
    }
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Implement your code here
        Log.v("MessageReceived","MessageReceived")
        try {
            val mChannel = NotificationChannelCompat.Builder(channelId, importance).apply {
                setName("my_channel_01") // Must set! Don't remove
                setDescription("my_channel_01")
                setLightsEnabled(true)
                setLightColor(Color.RED)
                setVibrationEnabled(true)
                setVibrationPattern(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400))
            }.build()
            val intent = Intent(applicationContext, HomeActivity::class.java)
            intent.putExtra("key", "value")
            val pendingIntent =
                PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            NotificationManagerCompat.from(applicationContext).createNotificationChannel(mChannel)
            val notification: Notification = NotificationCompat.Builder(applicationContext, channelId)
                .setSmallIcon(R.drawable.ic_menu_call)
                .setContentTitle("dfsdfsdfdsfdsfds***********")
                .setContentText("dsfsdfsdfdsfsdfsdf**************").setContentIntent(pendingIntent)
                .build()
            NotificationManagerCompat.from(applicationContext).notify(notifyID, notification)
        }catch (e:Exception){
            e.printStackTrace()
        }



    }
}