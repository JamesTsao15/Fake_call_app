package com.example.fake_call2

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Choose_Caller_Activity : AppCompatActivity() {
    private lateinit var tv_choose_caller_Name: TextView
    private lateinit var imageView_choose_caller_photo:ImageView
    private lateinit var btn_choose_caller_last_photo:Button
    private lateinit var btn_choose_caller_next_photo:Button
    var callerinformation_array:ArrayList<Caller> = ArrayList<Caller>()

    fun load_caller_data():ArrayList<Caller>{
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("caller_information", MODE_PRIVATE)
        val gson: Gson = Gson()
        val json: String? =sharedPreferences.getString("caller list",null)
        val listType=object: TypeToken<ArrayList<Caller>>(){}.type
        val jsonArray=gson.fromJson<ArrayList<Caller>>(json,listType)
        if(jsonArray==null){
            return ArrayList<Caller>()
        }
        else{
            return jsonArray
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_caller)
        tv_choose_caller_Name=findViewById(R.id.textView_choose_caller_Name)
        imageView_choose_caller_photo=findViewById(R.id.imageView_choose_caller_photo)
        btn_choose_caller_last_photo=findViewById(R.id.button_lastPhoto)
        btn_choose_caller_next_photo=findViewById(R.id.button_nextPhoto)
        callerinformation_array =load_caller_data()
    }

    override fun onResume() {
        super.onResume()
        
    }
}