package com.example.fake_call2

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
    private lateinit var tv_show_no_file:TextView
    private lateinit var imageView_choose_caller_photo:ImageView
    private lateinit var btn_choose_caller_last_photo:Button
    private lateinit var btn_choose_caller_next_photo:Button
    private lateinit var btn_saveChooseCaller:Button
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
    fun load_caller_photo(photoPath:String){
        if(photoPath!=""){
            val bitmap: Bitmap = BitmapFactory.decodeFile(photoPath)
            val resize_img= Bitmap.createScaledBitmap(bitmap,50,50,false)
            imageView_choose_caller_photo.setImageBitmap(resize_img)
        }
        else{
            imageView_choose_caller_photo.setImageResource(R.drawable.callerphoto)
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_caller)
        tv_choose_caller_Name=findViewById(R.id.textView_choose_caller_Name)
        tv_show_no_file=findViewById(R.id.textView_show_no_file)
        imageView_choose_caller_photo=findViewById(R.id.imageView_choose_caller_photo)
        btn_choose_caller_last_photo=findViewById(R.id.button_lastPhoto)
        btn_choose_caller_next_photo=findViewById(R.id.button_nextPhoto)
        btn_saveChooseCaller=findViewById(R.id.button_saveChooseCaller)
        callerinformation_array =load_caller_data()
    }

    override fun onResume() {
        super.onResume()
        if(callerinformation_array.size>0){
            tv_show_no_file.visibility=View.INVISIBLE
            tv_choose_caller_Name.visibility=View.VISIBLE
            btn_choose_caller_last_photo.visibility=View.VISIBLE
            btn_choose_caller_next_photo.visibility=View.VISIBLE
            imageView_choose_caller_photo.visibility=View.VISIBLE
            btn_saveChooseCaller.visibility=View.VISIBLE
            var index=0
            tv_choose_caller_Name.text=callerinformation_array[index].name
            load_caller_photo(callerinformation_array[index].photoPath)
            pick_caller_thread=Thread(Runnable {
                while(leave_Activity==false){
                    runOnUiThread{
                        if(index==0){
                            btn_choose_caller_last_photo.visibility=View.INVISIBLE
                            if(callerinformation_array.size==1){
                                btn_choose_caller_next_photo.visibility=View.INVISIBLE
                            }
                            else{
                                btn_choose_caller_next_photo.visibility=View.VISIBLE
                            }
                        }
                        else if (index==callerinformation_array.size-1){
                            btn_choose_caller_next_photo.visibility=View.INVISIBLE
                            btn_choose_caller_last_photo.visibility=View.VISIBLE
                        }
                        else{
                            btn_choose_caller_last_photo.visibility=View.VISIBLE
                            btn_choose_caller_next_photo.visibility=View.VISIBLE
                        }
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
                    load_caller_photo(callerinformation_array[index].photoPath)
                }
            }
            btn_choose_caller_last_photo.setOnClickListener {
                if (index>0){
                    index-=1
                    tv_choose_caller_Name.text=callerinformation_array[index].name
                    load_caller_photo(callerinformation_array[index].photoPath)
                }
            }
            btn_saveChooseCaller.setOnClickListener{
                val sharedPreferences:SharedPreferences=getSharedPreferences("caller_information", MODE_PRIVATE)
                val editor:SharedPreferences.Editor=sharedPreferences.edit()
                editor.putString("choose_caller_name",callerinformation_array[index].name)
                editor.putString("choose_caller_phone_number",callerinformation_array[index].phoneNumber)
                editor.putString("choose_caller_photo_path",callerinformation_array[index].photoPath)
                editor.apply()
                Toast.makeText(this,"設定來電者為${callerinformation_array[index].name}成功",Toast.LENGTH_SHORT).show()

            }
        }
       else{
           btn_choose_caller_last_photo.visibility=View.INVISIBLE
           btn_choose_caller_next_photo.visibility=View.INVISIBLE
           imageView_choose_caller_photo.visibility=View.INVISIBLE
           btn_saveChooseCaller.visibility=View.INVISIBLE
           tv_choose_caller_Name.visibility=View.INVISIBLE
           tv_show_no_file.visibility=View.VISIBLE
           tv_show_no_file.text="無任何資料，請先增加來電者資料"
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        leave_Activity=true
    }
}