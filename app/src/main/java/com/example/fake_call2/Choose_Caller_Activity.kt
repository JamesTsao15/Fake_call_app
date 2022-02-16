package com.example.fake_call2

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.IndexOutOfBoundsException

class Choose_Caller_Activity : AppCompatActivity() {
    private lateinit var tv_choose_caller_Name: TextView
    private lateinit var imageView_choose_caller_photo:ImageView
    private lateinit var btn_choose_caller_last_photo:Button
    private lateinit var btn_choose_caller_next_photo:Button
    var pick_caller_thread:Thread=Thread()
    var leave_Activity:Boolean=false
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
        var index=0
        tv_choose_caller_Name.text=callerinformation_array[index].name
        pick_caller_thread=Thread(Runnable {
            while(leave_Activity==false){
                runOnUiThread{
                    if(index==0){
                        btn_choose_caller_last_photo.visibility=View.INVISIBLE
                        btn_choose_caller_next_photo.visibility=View.VISIBLE
                    }
                    else if (index==callerinformation_array.size-1){
                        btn_choose_caller_next_photo.visibility=View.INVISIBLE
                        btn_choose_caller_last_photo.visibility=View.VISIBLE
                    }
                    else{
                        btn_choose_caller_last_photo.visibility=View.VISIBLE
                        btn_choose_caller_next_photo.visibility=View.VISIBLE
                    }
                    Log.e("JAMES",index.toString())
                }
                Thread.sleep(200)
            }
        })

        pick_caller_thread.start()
        if(leave_Activity==true)pick_caller_thread.interrupt()
        btn_choose_caller_next_photo.setOnClickListener {
            if (index < callerinformation_array.size - 1) {
                index += 1
                tv_choose_caller_Name.text = callerinformation_array[index].name
            }
        }
        btn_choose_caller_last_photo.setOnClickListener {
            if (index>0){
                index-=1
                tv_choose_caller_Name.text=callerinformation_array[index].name
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        leave_Activity=true
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.e("JAMES","on Destroy")
    }
}