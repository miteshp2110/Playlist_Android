package com.xceptions.playlist.viewmodel.user

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log

class MusicPlayerService: Service() {

    companion object {
        const val ACTION_PLAY = "com.xceptions.playlist.ACTION_PLAY"
        const val ACTION_PAUSE = "com.xceptions.playlist.ACTION_PAUSE"
        const val EXTRA_SONG_URL = "com.xceptions.playlist.EXTRA_SONG_URL"
        const val EXTRA_BEARER_TOKEN = "com.xceptions.playlist.EXTRA_BEARER_TOKEN"
    }
    private lateinit var mediaPlayer: MediaPlayer


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer = MediaPlayer()
        when (intent?.action) {
            ACTION_PLAY -> {
                val songUrl = intent.getStringExtra(EXTRA_SONG_URL)
                val token = intent.getStringExtra(EXTRA_BEARER_TOKEN)
//                playMusic(songUrl, songTitle)
                Log.d("music",songUrl.toString())
            }
            ACTION_PAUSE -> {

            }
        }
        return START_NOT_STICKY
    }

}