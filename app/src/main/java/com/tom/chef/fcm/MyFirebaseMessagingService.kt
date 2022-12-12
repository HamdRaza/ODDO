package com.tom.chef.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.tom.chef.R
import com.tom.chef.ui.splash.SplashScreen
import com.tom.chef.utils.SharedPreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MyFirebaseMessagingService  : FirebaseMessagingService() {

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        remoteMessage.createANotificationChannel(this,100)

    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    private fun RemoteMessage.createANotificationChannel(context: Context, id:Int){
        this.notification?.let { notification ->
            val createNotification=notification.channelId.createChannelId()
            createNotification.createNotificationChannel(context)
            val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(context, notification.channelId.createChannelId())
                .setContentTitle(notification.title)
                .setContentText(notification.body)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(NotificationCompat.BigTextStyle())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setContentIntent(notificationIntent(this))
                .setAutoCancel(true)
            val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(id, notificationBuilder.build())
        }
    }

    private fun String.createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(this, this, NotificationManager.IMPORTANCE_HIGH)
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun String?.createChannelId():String{
        if(this.isNullOrBlank()){
            return "MyNotifications"
        }
        return this
    }



    fun notificationIntent(remoteMessage: RemoteMessage):PendingIntent{

        val notificationIntent = Intent(this, SplashScreen::class.java)
         notificationIntent.putExtras(remoteMessage.data.getMessageBundle())
         return notificationIntent.getPendingIntent()
    }

    private fun Map<String,String>.getMessageBundle():Bundle{
        val bundle=Bundle()
        bundle.putBoolean("fromNotification",true)
        this.entries.forEach {
            if (it.value.isNotEmpty()){
                bundle.putString(it.key,it.value)
            }
        }
        return bundle
    }

    fun Intent.getPendingIntent():PendingIntent{
       return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(this@MyFirebaseMessagingService, 0, this, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(this@MyFirebaseMessagingService, 0, this, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE )
        }
    }

}