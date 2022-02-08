package com.example.fake_call2

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.File
data class Caller(val name:String,val phoneNumber:String,val photoPath:String)
class Caller_Activity : AppCompatActivity() {
    private lateinit var imgview_callerphoto:ImageView
    private lateinit var btn_change_caller_photo:Button
    private lateinit var btn_Save_Caller_Information:Button
    private lateinit var editText_caller_name:EditText
    private lateinit var editText_caller_phone_number:EditText
    val Caller_information=ArrayList<Caller>()
    var photoPath_Store=""
    fun pick_photo(){
        val uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val intent = Intent(Intent.ACTION_PICK, uri)
        startActivityForResult(intent,1)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caller)
        imgview_callerphoto=findViewById(R.id.imageView_callerphoto_setting)
        btn_change_caller_photo=findViewById(R.id.button_change_caller_photo)
        btn_Save_Caller_Information=findViewById(R.id.button_Save)
        editText_caller_name=findViewById(R.id.editTextPersonName)
        editText_caller_phone_number=findViewById(R.id.editTextPhone)
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
            val newCaller=Caller(Caller_Name,Caller_Phone,photoPath_Store)
            Caller_information.add(newCaller)
            editText_caller_phone_number.setText("")
            editText_caller_name.setText("")
            Log.e("JAMES",Caller_information.toString())
        }
    }
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1 && resultCode==Activity.RESULT_OK){
            Log.e("JAMES","照片選擇成功")
            val uri=data!!.data
            val uriPathHelper=URIPathHelper()
            val photoPath= uri?.let { uriPathHelper.getPath(this, it) }
            Log.e("JAMES",photoPath.toString())
            if (photoPath != null) {
                photoPath_Store=photoPath
            }
            val bitmap:Bitmap=BitmapFactory.decodeFile(photoPath)
            val resize_img=Bitmap.createScaledBitmap(bitmap,225,225,false)
            imgview_callerphoto.setImageBitmap(resize_img)
        }
        else{
            Log.e("JAMES","照片選擇失敗")
            Toast.makeText(this,"照片選擇失敗",Toast.LENGTH_SHORT).show()
        }
    }
}