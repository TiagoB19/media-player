package com.example.mediaplayer

import android.media.MediaPlayer
import android.widget.SeekBar
import java.util.concurrent.atomic.AtomicBoolean

class ProgressThread(seekBar: SeekBar, mediaPlayer: MediaPlayer) : Runnable {

    lateinit var thread: Thread
    private val sk: SeekBar = seekBar
    private val mp: MediaPlayer = mediaPlayer

    private val running : AtomicBoolean = AtomicBoolean(false)

    fun start(){
        this.thread = Thread(this)
        thread.start()
    }

    fun stop(){
        this.running.set(false)
    }
    override fun run() {
        running.set(true)
        while(running.get()){
            this.sk.progress = mp.currentPosition
            if(sk.progress == sk.max) {
                sk.progress = 0
                stop()
            }
        }
    }
}