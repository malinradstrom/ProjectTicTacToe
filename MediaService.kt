package com.example.projecttictactoe

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
class MediaService : Service() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel() // Skapa kanalen
    }

    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "START" -> {
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.jingle_bells)
                    mediaPlayer?.isLooping = true
                    mediaPlayer?.start()
                }
            }
            "PAUSE" -> {
                mediaPlayer?.pause()
            }
            "RESUME" -> {
                mediaPlayer?.start()
            }
            "STOP" -> {
                stopForeground(true)
                stopSelf()
            }
        }

        val notification = NotificationCompat.Builder(this, "MediaServiceChannel")
            .setContentTitle("Playing Jingle Bells")
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .build()

        startForeground(1, notification)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "MediaServiceChannel"
            val channelName = "Background Music"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(channelId, channelName, importance)
            channel.description = "Plays background music during gameplay"
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }
}

// :)