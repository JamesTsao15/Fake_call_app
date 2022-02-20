package com.example.fake_call2

import android.content.Context
import android.content.SharedPreferences
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import java.lang.IllegalStateException

class dark_theme_activity : AppCompatActivity() {
    private lateinit var tv_call_time_dark: TextView
    private lateinit var tv_callerview_name_dark: TextView
    private lateinit var tv_micStatus_dark:TextView
    private lateinit var tv_VoiceOut_dark:TextView
    private lateinit var btn_mic_dark: ImageButton
    private lateinit var btn_webcam_dark: ImageButton
    private lateinit var btn_voiceOut_dark: ImageButton
    private lateinit var btn_cacenlphone_dark: ImageButton
    private val player=MediaPlayer()
    private var isCancel:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dark_theme)
        supportActionBar?.hide()
        val sharedPreferences: SharedPreferences =getSharedPreferences("caller_information", MODE_PRIVATE)
        tv_call_time_dark=findViewById(R.id.textView_timer_dark)
        tv_callerview_name_dark=findViewById(R.id.textView_caller_name_dark)
        tv_micStatus_dark=findViewById(R.id.textView_mic_dark)
        tv_VoiceOut_dark=findViewById(R.id.textView_voice_Out_dark)
        btn_mic_dark=findViewById(R.id.imagebtn_mic_dark)
        btn_webcam_dark=findViewById(R.id.imagebtn_webcam_dark)
        btn_voiceOut_dark=findViewById(R.id.imgButton_voice_Out_dark)
        btn_cacenlphone_dark=findViewById(R.id.imgButton_cancelcall_dark)
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.mode = AudioManager.MODE_IN_COMMUNICATION
        audioManager.isSpeakerphoneOn=false
        if(sharedPreferences.getString("Audio_Path","")!=""){
            Log.e("JAMES","prepare player")
            sharedPreferences.getString("Audio_Path","")?.let { Log.e("JAMES", it) }
            player.setDataSource(sharedPreferences.getString("Audio_Path",""))
            player.prepare()
            player.start()
        }
    }

    override fun onResume() {
        super.onResume()
        var micOn=true
        var isvoiceOut=false
        var timercount=0
        val thread=Thread(Runnable {
            while(!isCancel){
                timercount++
                val minute=timercount/60
                val second=timercount-minute*60
                val timer_string=String.format("%02d:%02d",minute,second)
                runOnUiThread{
                    tv_call_time_dark.text=timer_string
                }
                Thread.sleep(1000)
            }
        })
        thread.start()
        btn_cacenlphone_dark.setOnClickListener {
            isCancel=true
            finish()
        }
        btn_cacenlphone_dark.setOnClickListener {
            isCancel=true
            finish()
        }
        btn_mic_dark.setOnClickListener {
            if(micOn){
                btn_mic_dark.setImageResource(R.drawable.ic_baseline_mic_off_24)
                tv_micStatus_dark.text="開啟麥克風"
                micOn=false
            }
            else{
                btn_mic_dark.setImageResource(R.drawable.ic_baseline_mic_24)
                tv_micStatus_dark.text="關閉麥克風"
                micOn=true
            }
        }
        btn_webcam_dark.setOnClickListener {
            Toast.makeText(this,"攝像頭功能不可開啟", Toast.LENGTH_SHORT).show()
        }
        btn_voiceOut_dark.setOnClickListener {
            val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            if(!isvoiceOut){
                audioManager.mode = AudioManager.MODE_NORMAL
                audioManager.isSpeakerphoneOn=true
                tv_VoiceOut_dark.text="關閉擴音"
                isvoiceOut=true
            }
            else{
                audioManager.mode = AudioManager.MODE_IN_COMMUNICATION
                audioManager.isSpeakerphoneOn=false
                tv_VoiceOut_dark.text="開啟擴音"
                isvoiceOut=false
            }

        }
        player.setOnCompletionListener {
            it.stop()
            it.release()
        }
    }
    override fun finish() {
        super.finish()
        try{
            player.stop()
            player.release()
        }catch (e: IllegalStateException){
            e.printStackTrace()
        }

    }
}