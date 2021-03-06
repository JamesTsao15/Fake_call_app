package com.example.fake_call2

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.lang.reflect.Type

data class Caller(val name:String,val phoneNumber:String,val photoPath:String)
class Caller_Activity : AppCompatActivity() {
    private lateinit var imgview_callerphoto:ImageView
    private lateinit var btn_change_caller_photo:Button
    private lateinit var btn_Save_Caller_Information:Button
    private lateinit var editText_caller_name:EditText
    private lateinit var editText_caller_phone_number:EditText
    private lateinit var btn_delete_caller:Button
    var Caller_information=ArrayList<Caller>()
    var photoPath_Store=""
    fun pick_photo(){
        val uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val intent = Intent(Intent.ACTION_PICK, uri)
        startActivityForResult(intent,1)
    }
    fun save_caller_data(){
        val sharedPreferences:SharedPreferences=
            getSharedPreferences("caller_information", MODE_PRIVATE)
        val editor:SharedPreferences.Editor=sharedPreferences.edit()
        val gson:Gson=Gson()
        var json:String=gson.toJson(Caller_information)
        editor.putString("caller list",json)
        editor.apply()
    }
    fun load_caller_data():ArrayList<Caller>{
        val sharedPreferences:SharedPreferences=
            getSharedPreferences("caller_information", MODE_PRIVATE)
        val gson:Gson=Gson()
        val json: String? =sharedPreferences.getString("caller list",null)
        val listType=object:TypeToken<ArrayList<Caller>>(){}.type
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
        setContentView(R.layout.activity_caller)
        imgview_callerphoto=findViewById(R.id.imageView_callerphoto_setting)
        btn_change_caller_photo=findViewById(R.id.button_change_caller_photo)
        btn_Save_Caller_Information=findViewById(R.id.button_Save)
        btn_delete_caller=findViewById(R.id.button_delete)
        editText_caller_name=findViewById(R.id.editTextPersonName)
        editText_caller_phone_number=findViewById(R.id.editTextPhone)
        if(load_caller_data().isEmpty()!=true){
            Caller_information=load_caller_data()
        }
    }
    override fun onResume() {
        super.onResume()
        btn_change_caller_photo.setOnClickListener {
            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
                !=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),0)
                pick_photo()
            }
            else{
                pick_photo()
            }
        }
        btn_Save_Caller_Information.setOnClickListener {
            val Caller_Name=editText_caller_name.text.toString()
            val Caller_Phone=editText_caller_phone_number.text.toString()
            if(Caller_Name.equals("")==true || Caller_Phone.equals("")==true){
                Toast.makeText(this,"????????????????????????",Toast.LENGTH_SHORT).show()
            }
            else{
                val newCaller=Caller(Caller_Name,Caller_Phone,photoPath_Store)
                Caller_information.add(newCaller)
                editText_caller_phone_number.setText("")
                editText_caller_name.setText("")
                Log.e("JAMES",Caller_information.toString())
                save_caller_data()
                Toast.makeText(this,"????????????",Toast.LENGTH_SHORT).show()
            }
        }
        btn_delete_caller.setOnClickListener {
           if(Caller_information.isEmpty()==false){
               val delete_caller=editText_caller_name.text.toString()
               for(i in 0 until Caller_information.size){
                   val Caller_Name=Caller_information[i].name
                   if(Caller_Name==delete_caller){
                       Caller_information.removeAt(i)
                       save_caller_data()
                       Toast.makeText(this,"???????????????:$Caller_Name ??????",Toast.LENGTH_SHORT).show()
                       break
                   }
                   else{
                       Toast.makeText(this,"?????????????????????????????????",Toast.LENGTH_SHORT).show()
                   }
               }
           }
        }
    }
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1 && resultCode==Activity.RESULT_OK){
            val uri=data!!.data
            val uriPathHelper=URIPathHelper()
            val photoPath= uri?.let { uriPathHelper.getPath(this, it) }
            if (photoPath != null) {
                photoPath_Store=photoPath
            }
            val bitmap:Bitmap=BitmapFactory.decodeFile(photoPath)
            val resize_img=Bitmap.createScaledBitmap(bitmap,225,225,false)
            imgview_callerphoto.setImageBitmap(resize_img)
        }
        else{
            Toast.makeText(this,"??????????????????",Toast.LENGTH_SHORT).show()
        }
    }
}