package com.xceptions.playlist.viewmodel.user

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
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

    private val NOTIFICATION_ID = 1
    private val CHANNEL_ID = "MusicPlayerChannel"
    private var mediaPlayer: MediaPlayer = MediaPlayer()

    // For binding to activity
    private val binder = MusicBinder()

    // For seekbar updates
    private val handler = Handler(Looper.getMainLooper())
    private val updateSeekBarRunnable = object : Runnable {
        override fun run() {
            if (mediaPlayer.isPlaying) {
                val currentPosition = mediaPlayer.currentPosition
                onProgressUpdateListener?.onProgressUpdate(currentPosition, mediaPlayer.duration)
            }
            handler.postDelayed(this, 1000) // Update every second
        }
    }

    // Interface for progress updates
    interface OnProgressUpdateListener {
        fun onProgressUpdate(progress: Int, duration: Int)
    }

    private var onProgressUpdateListener: OnProgressUpdateListener? = null

    // Set listener from activity
    fun setOnProgressUpdateListener(listener: OnProgressUpdateListener?) {
        this.onProgressUpdateListener = listener
        startProgressUpdates()
    }

    private fun startProgressUpdates() {
        handler.removeCallbacks(updateSeekBarRunnable)
        handler.post(updateSeekBarRunnable)
    }

    private fun stopProgressUpdates() {
        handler.removeCallbacks(updateSeekBarRunnable)
    }

    // Binder for communication with UI
    inner class MusicBinder : Binder() {
        fun getService(): MusicPlayerService = this@MusicPlayerService
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
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
                        startProgressUpdates()
                    }
                    setOnErrorListener { _, what, extra ->
                        Log.e("music", "MediaPlayer error: what=$what extra=$extra")
                        true
                    }
                    setOnCompletionListener {
                        stopProgressUpdates()
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
                startProgressUpdates()
            }
            null -> {
                releaseMediaPlayer()
                stopSelf()
            }
        }

        return START_NOT_STICKY
    }

    // Seekbar related methods
    fun seekTo(position: Int) {
        if ((mediaPlayer.isPlaying || isPaused())) {
            mediaPlayer.seekTo(position)
        }
    }


    fun getCurrentPosition(): Int {
        return if ((mediaPlayer.isPlaying || isPaused())) {
            mediaPlayer.currentPosition
        } else {
            0
        }
    }

    fun isPlaying(): Boolean {
        return  mediaPlayer.isPlaying
    }

    private fun isPaused(): Boolean {
        return !mediaPlayer.isPlaying && getCurrentPosition() > 0
    }

    private fun releaseMediaPlayer() {
        try {
            stopProgressUpdates()
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
            }
            mediaPlayer.release()
            Log.d("music", "MediaPlayer released")
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