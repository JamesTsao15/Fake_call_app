package com.example.fake_call2

import android.app.Activity
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class Full_color_caller_theme_Activity : AppCompatActivity() {
    private lateinit var tv_call_time:TextView
    private lateinit var tv_callerview_name:TextView
    private lateinit var btn_mic:ImageButton
    private lateinit var btn_webcam:ImageButton
    private lateinit var btn_voiceOut:ImageButton
    private lateinit var btn_cacenlphone:ImageButton
    private var isCancel:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        this.window.setBackgroundDrawableResource(R.drawable.caller_theme_dark)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_color_caller_theme)
        supportActionBar?.hide()
        tv_call_time=findViewById(R.id.textView_callTime)
        tv_callerview_name=findViewById(R.id.textView_callerview_Name)
        btn_mic=findViewById(R.id.button_Mic)
        btn_webcam=findViewById(R.id.button_webcam)
        btn_voiceOut=findViewById(R.id.button_voiceOut)
        btn_cacenlphone=findViewById(R.id.imageButton_bright)
    }

    override fun onResume() {
        super.onResume()
        var timercount=0
        val thread=Thread(Runnable {
            while(!isCancel){
                timercount++
                val minute=timercount/60
                val second=timercount-minute*60
                val timer_string=String.format("%02d:%02d",minute,second)
                runOnUiThread{
                   tv_call_time.text=timer_string
                }
                Thread.sleep(1000)
            }
        })
        thread.start()
        btn_cacenlphone.setOnClickListener {
            isCancel=true
            finish()
        }
    }
}