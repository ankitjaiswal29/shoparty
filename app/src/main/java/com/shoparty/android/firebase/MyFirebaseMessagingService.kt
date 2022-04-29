package com.shoparty.android.firebase

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.shoparty.android.R
import com.shoparty.android.ui.splash.SplashActivity
import com.shoparty.android.utils.PrefManager


class MyFirebaseMessagingService : FirebaseMessagingService()
{
    override fun onNewToken(s: String)
    {
        PrefManager.write(PrefManager.DEVICETOKEN,s)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.e(TAG, ">>>>>" + remoteMessage.data.toString())

      //  PrefManager.putBoolean(PrefManager.IS_NEW_NOTIFICATION,true)
        val intent = Intent(this, SplashActivity::class.java)
         sendNotification(intent,
             remoteMessage.data["title"],
             remoteMessage.data["body"],
             remoteMessage.data["status_code"]
         )
    }

    companion object {
        private val TAG = MyFirebaseMessagingService::class.java.simpleName
        private const val CHANNEL_ID = "144"
    }

    private fun sendNotification(
        intent: Intent?,
        title: String?,
        messageBody: String?,
        code: String?
    ) {
        val notificationBuilder: NotificationCompat.Builder
        val stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addNextIntentWithParentStack(intent!!)
        val pendingIntent = stackBuilder.getPendingIntent(100, PendingIntent.FLAG_UPDATE_CURRENT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = messageBody
            // do something for phones running an SDK before oreo
            notificationBuilder =
                NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_app_icon_round) // Small Icon
                    .setLargeIcon(
                        BitmapFactory.decodeResource(
                            this.resources,
                            R.mipmap.ic_app_icon_round
                        )
                    )
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setStyle(NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager.createNotificationChannel(channel)
            notificationManager.notify(
                System.currentTimeMillis().toInt(),
                notificationBuilder.build()
            )
        } else {
            // do something for phones running an SDK before oreo
            notificationBuilder =
                NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_app_icon_round) // Small Icon
                    .setLargeIcon(
                        BitmapFactory.decodeResource(
                            this.resources,
                            R.mipmap.ic_app_icon_round
                        )
                    )
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setStyle(NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setAutoCancel(true) /*.setNumber(2)
                    .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)*/
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(
                System.currentTimeMillis().toInt(),
                notificationBuilder.build()
            )
        }
    }



}
