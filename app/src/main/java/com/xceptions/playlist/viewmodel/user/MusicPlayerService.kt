package com.xceptions.playlist.viewmodel.user

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.xceptions.playlist.R

class MusicPlayerService: Service() {



    companion object {
        const val ACTION_PLAY = "com.xceptions.playlist.ACTION_PLAY"
        const val ACTION_PAUSE = "com.xceptions.playlist.ACTION_PAUSE"
        const val ACTION_PLAY_SONG = "com.xceptions.playlist.ACTION_PLAY_SONG"
        const val EXTRA_SONG_URL = "com.xceptions.playlist.EXTRA_SONG_URL"
        const val EXTRA_BEARER_TOKEN = "com.xceptions.playlist.EXTRA_BEARER_TOKEN"
    }
    private  val NOTIFICATION_ID = 1
    private  val CHANNEL_ID = "MusicPlayerChannel"
    private  var mediaPlayer: MediaPlayer = MediaPlayer()

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showForegroundNotification()
        Log.d("music","started service")
        when (intent?.action) {
            ACTION_PLAY -> {
                val songUrl = intent.getStringExtra(EXTRA_SONG_URL)
                val token = intent.getStringExtra(EXTRA_BEARER_TOKEN)
                if(mediaPlayer.isPlaying){
                    Log.d("music","Already playing , switiching to new song")
                    mediaPlayer.stop()
                }
                mediaPlayer = MediaPlayer().apply {

                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
                    )
                    setOnPreparedListener {
                        it.start()
                    }
                    setOnErrorListener { _, what, extra ->
                        Log.e("music", "MediaPlayer error: what=$what extra=$extra")
                        true
                    }

                    setDataSource(songUrl)

                    prepareAsync()
                }


            }
            ACTION_PAUSE -> {
                mediaPlayer.pause()
            }
            ACTION_PLAY_SONG -> {
                mediaPlayer.start()
            }
            null -> {
                releaseMediaPlayer()
                stopSelf()
            }
        }

        return START_NOT_STICKY
    }


    private fun releaseMediaPlayer() {
        try {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
            }
            mediaPlayer.release()
        } catch (e: Exception) {
            Log.e("music", "Error releasing MediaPlayer: ${e.message}")
        }
    }

    override fun onDestroy() {
        releaseMediaPlayer()
        super.onDestroy()
    }
    override fun onTaskRemoved(rootIntent: Intent?) {
        releaseMediaPlayer()
        stopSelf()
        super.onTaskRemoved(rootIntent)
    }

    private fun showForegroundNotification() {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Music Player")
            .setContentText("Playing music...")
            .setSmallIcon(R.drawable.icon_musical_note)
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Music Player Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }



}