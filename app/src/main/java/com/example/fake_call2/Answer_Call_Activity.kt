package com.example.fake_call2

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.AssetFileDescriptor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class Answer_Call_Activity : AppCompatActivity() {
    private lateinit var imagView_answer_Callerphoto:ImageView
    private lateinit var tv_answer_caller_name:TextView
    private val player=MediaPlayer()
    private var isLitenCall:Boolean=false
    private lateinit var btn_listen_call:ImageButton
    private lateinit var btn_cancel_call:ImageButton
    fun load_caller_photo(photoPath:String){
        if(photoPath!=""){
            val bitmap: Bitmap = BitmapFactory.decodeFile(photoPath)
            val resize_img= Bitmap.createScaledBitmap(bitmap,225,225,false)
            imagView_answer_Callerphoto.setImageBitmap(resize_img)
        }
        else{
            imagView_answer_Callerphoto.setImageResource(R.drawable.callerphoto)
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer_call)
        tv_answer_caller_name=findViewById(R.id.textView_answer_callerNmae)
        btn_listen_call=findViewById(R.id.imageButton_listen)
        btn_cancel_call=findViewById(R.id.imageButton_cancel_caller)
        imagView_answer_Callerphoto=findViewById(R.id.imageView_answer_callerphoto)
        val sharedPreferences:SharedPreferences=getSharedPreferences("caller_information", MODE_PRIVATE)
        val callerName=sharedPreferences.getString("choose_caller_name","未知來電")
        val caller_photo_path=sharedPreferences.getString("choose_caller_photo_path","")
        val phoneTone_Path=sharedPreferences.getString("PhoneTone_Path","")
        supportActionBar?.hide()
        tv_answer_caller_name.text=callerName
        val afd:AssetFileDescriptor=assets.openFd("ringtone_music.mp3")
        if (caller_photo_path != null) {
            load_caller_photo(caller_photo_path)
        }
        if(phoneTone_Path==""){
            player.setDataSource(afd.fileDescriptor,afd.startOffset,afd.length)
            player.prepare()
            player.start()
        }
        else{
            player.setDataSource(phoneTone_Path)
            player.prepare()
            player.start()
        }
    }
    override fun onResume() {
        val sharedPreferences:SharedPreferences=getSharedPreferences("caller_information", MODE_PRIVATE)
        super.onResume()
        btn_listen_call.setOnClickListener{
            player.stop()
            player.release()
            isLitenCall=true
            var isDarkTheme:Boolean=false
            isDarkTheme=sharedPreferences.getBoolean("isDarkTheme",false)
            if(isDarkTheme){
                val dark_theme_Intent= Intent(this,dark_theme_activity::class.java)
                startActivity(dark_theme_Intent)
                finish()
            }
            else{
                val bright_theme_Intent=Intent(this,Full_color_caller_theme_Activity::class.java)
                startActivity(bright_theme_Intent)
                finish()
            }
        }
        btn_cancel_call.setOnClickListener{
            finish()
            isLitenCall=false
        }
    }

    override fun finish() {
        super.finish()
        if(isLitenCall==false){
            player.stop()
            player.release()
        }
    }
}