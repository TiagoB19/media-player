package com.example.mediaplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar


class MainActivity : AppCompatActivity() {

    private lateinit var btnPlay : Button
    private lateinit var btnPause : Button
    private lateinit var btnStop : Button
    private lateinit var seekControl : SeekBar
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnPlay = findViewById(R.id.btnPlay)
        btnPause = findViewById(R.id.btnPause)
        btnStop = findViewById(R.id.btnStop)
        seekControl = findViewById(R.id.seekControl)

        mediaPlayer = MediaPlayer.create(this, R.raw.heatofthemoment)

        seekControl.min = 0
        seekControl.max = mediaPlayer.duration

        val progressThread = ProgressThread(seekControl, mediaPlayer)

        btnPlay.setOnClickListener {
            if(!mediaPlayer.isPlaying) {
                mediaPlayer.start()
                progressThread.start()
            }
        }

        btnPause.setOnClickListener {
            if(mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                progressThread.stop()
            }
        }

        btnStop.setOnClickListener {
            if(mediaPlayer.isPlaying){
                mediaPlayer.seekTo(0)
                mediaPlayer.stop()
                mediaPlayer.prepare()
                progressThread.stop()
            }
        }

        seekControl.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                if (p0 != null && mediaPlayer.isPlaying) {
                    progressThread.stop()
                }
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                if (p0 != null) {
                    mediaPlayer.seekTo(p0.progress)
                    progressThread.start()
                }
            }
        })
    }
}