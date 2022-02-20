package com.example.fake_call2


import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog


class MainActivity : AppCompatActivity() {
    private lateinit var gridView_home: GridView
    private lateinit var btn_call:ImageButton
    private lateinit var tv_caller_information_name: TextView
    private lateinit var tv_caller_information_phone_number:TextView
    private lateinit var imgView_caller_information_photo: ImageView
    private lateinit var callerInformationView: caller_information_view
    var isClickAudio:Boolean=false
    fun load_caller_photo(photoPath:String){
        if(photoPath!=""){
            val bitmap: Bitmap = BitmapFactory.decodeFile(photoPath)
            val resize_img= Bitmap.createScaledBitmap(bitmap,225,225,false)
            imgView_caller_information_photo.setImageBitmap(resize_img)
        }
        else{
            imgView_caller_information_photo.setImageResource(R.drawable.callerphoto)
        }

    }
    fun pick_Audio(){
        val uri=android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val audio_picker_intent=Intent(Intent.ACTION_PICK,uri)
        startActivityForResult(audio_picker_intent,1)
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val setting_title_array= arrayOf<String>("增加來電者","來電時間","來電主題","選擇來電者",
            "來電聲音","來電鈴聲")
        gridView_home=findViewById<GridView>(R.id.gridview_set_call_time)
        val item=ArrayList<Item>()
        val icon_array=resources.obtainTypedArray(R.array.icon_list)
        for (i in 0 until icon_array.length()){
            val icon=icon_array.getResourceId(i,0)
            val setting_title=setting_title_array[i]
            item.add(Item(icon,setting_title))
        }
        gridView_home.adapter= MyAdapter(this,item,R.layout.component_grid_view)
        callerInformationView=findViewById(R.id.view_callerinformation)
        btn_call=callerInformationView.findViewById(R.id.imageButton_call)
        tv_caller_information_name=callerInformationView.findViewById(R.id.tv_caller_name)
        tv_caller_information_phone_number=callerInformationView.findViewById(R.id.tv_caller_phone_number)
        imgView_caller_information_photo=callerInformationView.findViewById(R.id.imageView_callerphoto)
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences:SharedPreferences=getSharedPreferences("caller_information", MODE_PRIVATE)
        val editor:SharedPreferences.Editor=sharedPreferences.edit()
        editor.putString("Audio_Path","")
        editor.putString("PhoneTone_Path","")
        val caller_name=sharedPreferences.getString("choose_caller_name","未知來電")
        val caller_phone_number=sharedPreferences.getString("choose_caller_phone_number","09123456789")
        val caller_photo_path=sharedPreferences.getString("choose_caller_photo_path","")
        tv_caller_information_name.text=caller_name
        tv_caller_information_phone_number.text=caller_phone_number
        if (caller_photo_path != null) {
            load_caller_photo(caller_photo_path)
        }
        btn_call.setOnClickListener {

            Thread.sleep(sharedPreferences.getInt("timer_setting_time_ms",0).toLong())
            val AnswerCallIntent=Intent(this,Answer_Call_Activity::class.java)
            startActivity(AnswerCallIntent)
        }
        gridView_home.setOnItemClickListener { adapterView, view, position, l ->
            when(position){
                0->{
                    val caller_intent=Intent(this,Caller_Activity::class.java)
                    startActivity(caller_intent)
                }
                1->{
                    val caller_timer_intent=Intent(this,Schedule_Call_Activity::class.java)
                    startActivity(caller_timer_intent)
                }
                2->{
                    val caller_theme_intent=Intent(this,Theme_Activity::class.java)
                    startActivity(caller_theme_intent)
                }
                3->{
                    val choose_caller_intent=Intent(this,Choose_Caller_Activity::class.java)
                    startActivity(choose_caller_intent)
                }
                4->{
                    val select_ardio_array= arrayOf("無","音訊選擇器","自行錄音")
                    var alertdialog_position=0
                    AlertDialog.Builder(this)
                        .setTitle("選擇音訊")
                        .setSingleChoiceItems(select_ardio_array,0){
                            dialogInterface,i->
                            alertdialog_position=i
                        }
                        .setPositiveButton("確定"){
                            dialog,which->
                            if(alertdialog_position==1){
                                isClickAudio=true
                                pick_Audio()
                            }
                            if(alertdialog_position==2){
                                val record_intent=Intent(this,Record_Audio_Activity::class.java)
                                startActivity(record_intent)
                            }
                        }.show()
                }
                5->{
                    val select_ardio_array= arrayOf("預設鈴聲","音訊選擇器")
                    var alertdialog_position=0
                    AlertDialog.Builder(this)
                        .setTitle("選擇鈴聲")
                        .setSingleChoiceItems(select_ardio_array,0){
                                dialogInterface,i->
                            alertdialog_position=i
                        }
                        .setPositiveButton("確定"){
                                dialog,which->

                                if(alertdialog_position==1){
                                    isClickAudio=false
                                    pick_Audio()
                                }
                                else{
                                    editor.putString("PhoneTone_Path","")
                                    editor.apply()
                                }

                        }.show()
                }
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1 && resultCode==Activity.RESULT_OK){
            val sharedPreferences:SharedPreferences=getSharedPreferences("caller_information", MODE_PRIVATE)
            val editor:SharedPreferences.Editor=sharedPreferences.edit()
            val uri=data!!.data
            val uriPathHelper=URIPathHelper()
            val audioPath= uri?.let { uriPathHelper.getPath(this, it) }
            if(isClickAudio==true){
                editor.putString("Audio_Path",audioPath)
            }
            else{
                editor.putString("PhoneTone_Path",audioPath)
            }
            editor.apply()
        }

    }
}