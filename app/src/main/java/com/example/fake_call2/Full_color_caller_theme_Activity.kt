package com.example.fake_call2

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.lang.IllegalStateException

class Full_color_caller_theme_Activity : AppCompatActivity() {
    private lateinit var tv_call_time:TextView
    private lateinit var tv_callerview_name:TextView
    private lateinit var tv_micStatus:TextView
    private lateinit var tv_VoiceOut:TextView
    private lateinit var img_callerview_photo:ImageView
    private lateinit var btn_mic:ImageButton
    private lateinit var btn_webcam:ImageButton
    private lateinit var btn_voiceOut:ImageButton
    private lateinit var btn_cacenlphone:ImageButton
    private var isCancel:Boolean=false
    private val player=MediaPlayer()
    fun load_caller_photo(photoPath:String){
        if(photoPath!=""){
            val bitmap: Bitmap = BitmapFactory.decodeFile(photoPath)
            val resize_img= Bitmap.createScaledBitmap(bitmap,225,225,false)
            img_callerview_photo.setImageBitmap(resize_img)
        }
        else{
            img_callerview_photo.setImageResource(R.drawable.callerphoto)
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_color_caller_theme)
        val sharedPreferences:SharedPreferences=getSharedPreferences("caller_information", MODE_PRIVATE)
        supportActionBar?.hide()
        tv_micStatus=findViewById(R.id.textView_mic)
        tv_call_time=findViewById(R.id.textView_callTime)
        tv_callerview_name=findViewById(R.id.textView_callerview_Name)
        tv_VoiceOut=findViewById(R.id.textView_VoiceOut)
        btn_mic=findViewById(R.id.button_Mic)
        btn_webcam=findViewById(R.id.button_webcam)
        btn_voiceOut=findViewById(R.id.button_voiceOut)
        btn_cacenlphone=findViewById(R.id.imageButton_bright)
        img_callerview_photo=findViewById(R.id.imageView_callerviewphoto)
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
        val sharedPreferences:SharedPreferences=getSharedPreferences("caller_information", MODE_PRIVATE)
        tv_callerview_name.text=sharedPreferences.getString("choose_caller_name","未知來電")
        val path=sharedPreferences.getString("choose_caller_photo_path","")
        if (path != null) {
            load_caller_photo(path)
        }
        var timercount=0
        var micOn:Boolean=true
        var isvoiceOut:Boolean=false
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
        btn_mic.setOnClickListener {
            if(micOn){
                btn_mic.setImageResource(R.drawable.ic_baseline_mic_off_24)
                tv_micStatus.text="開啟麥克風"
                micOn=false
            }
            else{
                btn_mic.setImageResource(R.drawable.ic_baseline_mic_24)
                tv_micStatus.text="關閉麥克風"
                micOn=true
            }
        }
        btn_webcam.setOnClickListener {
            Toast.makeText(this,"攝像頭功能不可開啟",Toast.LENGTH_SHORT).show()
        }
        btn_voiceOut.setOnClickListener {
            val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            if(!isvoiceOut){
                audioManager.mode = AudioManager.MODE_NORMAL
                audioManager.isSpeakerphoneOn=true
                tv_VoiceOut.text="關閉擴音"
                isvoiceOut=true
            }
            else{
                audioManager.mode = AudioManager.MODE_IN_COMMUNICATION
                audioManager.isSpeakerphoneOn=false
                tv_VoiceOut.text="開啟擴音"
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
        }catch (e:IllegalStateException){
            e.printStackTrace()
        }

    }
}