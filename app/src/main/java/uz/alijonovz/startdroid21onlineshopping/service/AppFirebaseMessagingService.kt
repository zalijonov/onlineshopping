package uz.alijonovz.startdroid21onlineshopping.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.DEFAULT_VIBRATE
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import uz.alijonovz.startdroid21onlineshopping.BuildConfig
import uz.alijonovz.startdroid21onlineshopping.R
import uz.alijonovz.startdroid21onlineshopping.screen.MainActivity
import uz.alijonovz.startdroid21onlineshopping.utils.PrefUtils
import java.lang.Exception

class AppFirebaseMessagingService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d("tag-debug : ", token)
        PrefUtils.setFCMToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        try{
            Log.d("tag-debug : body ", remoteMessage?.notification?.body.toString())
            Log.d("tag-debug : title ", remoteMessage?.notification?.title.toString())
            val title = remoteMessage.notification?.title
            val body = remoteMessage.notification?.body
            showMessage(
                title ?: "",
                body ?: ""
            )
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun showMessage(title: String, body:String, id: Long = System.currentTimeMillis(), withSound: Boolean = false){
        val defaultSoundUri: Uri? = if (withSound) RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION) else null
        var intent = Intent(this, MainActivity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val channelId = BuildConfig.APPLICATION_ID
        val builder = NotificationCompat.Builder(this, channelId)
            .setDefaults(DEFAULT_VIBRATE)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setSmallIcon(R.drawable.ic_menu)
            .setLargeIcon(BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ic_menu))
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setColor(Color.parseColor("#FFFFFFFF"))
            .setSound(defaultSoundUri)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(longArrayOf(100, 200, 300, 400, 500))
            .setContentIntent(pendingIntent)

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                channelId,
                "${BuildConfig.APPLICATION_ID} channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(channel)
        }
        manager.notify(id.toInt(), builder.build())
    }
}