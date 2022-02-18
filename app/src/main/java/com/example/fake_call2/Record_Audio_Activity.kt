package com.example.fake_call2

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import java.io.File

class Record_Audio_Activity : AppCompatActivity() {
    private val recorder=MediaRecorder()
    private val player=MediaPlayer()
    private lateinit var folder: File
    private var leave_activity:Boolean=false
    private var thread=Thread()
    private var fileName:String=""
    private var fileNameisNull:Boolean=true
    private fun setFolder(){
        folder= File(filesDir.absolutePath+"/record")
        if(!folder.exists()){
            folder.mkdirs()
        }
    }
    private fun setFilename(){
        val inflater: LayoutInflater =this.layoutInflater
        val btn_record=findViewById<Button>(R.id.button_record)
        val btn_stop_record=findViewById<Button>(R.id.button_stop_record)
        val btn_play_audio=findViewById<Button>(R.id.button_play_audio)
        val btn_stop_audio=findViewById<Button>(R.id.button_stop_audio)
        val tv_recording=findViewById<TextView>(R.id.textView_recording)
        val set_file_name_view=inflater.inflate(R.layout.set_file_name_view,null)
        val editTextView:EditText=set_file_name_view.findViewById(R.id.editTextFileName)
        val builder=AlertDialog.Builder(this)
        builder.setTitle("檔案名稱")
        builder.setMessage("請輸入檔案名稱:")
        builder.setView(set_file_name_view)
        builder.setPositiveButton("確定"){
            dialog,which->
            if(editTextView.text.toString()!=""){
                fileName=editTextView.text.toString()
                fileNameisNull=false
            }
            else{
                fileNameisNull=true
                btn_record.isEnabled=false
                btn_stop_record.isEnabled=false
                btn_play_audio.isEnabled=false
                btn_stop_audio.isEnabled=false
                tv_recording.text="未設定檔名無法錄音"
                Toast.makeText(this,"檔名為空",Toast.LENGTH_SHORT).show()
            }
        }
        val alertdialog=builder.create()
        alertdialog.show()

    }
    private fun setListener(){
        val btn_record=findViewById<Button>(R.id.button_record)
        val btn_stop_record=findViewById<Button>(R.id.button_stop_record)
        val btn_play_audio=findViewById<Button>(R.id.button_play_audio)
        val btn_stop_audio=findViewById<Button>(R.id.button_stop_audio)
        val btn_save_audio_path=findViewById<Button>(R.id.button_save_audio_path)
        val tv_recording=findViewById<TextView>(R.id.textView_recording)
        setFilename()
        thread=Thread(Runnable {
            while(leave_activity==false){
                if(fileNameisNull==false){
                    runOnUiThread{
                        btn_record.setOnClickListener {
                            recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
                            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                            recorder.setOutputFile(File(folder,fileName).absolutePath)
                            Log.e("JAMES_RECORD",File(folder,fileName).absolutePath)
                            recorder.prepare()
                            recorder.start()
                            tv_recording.text="錄音中....."
                            btn_play_audio.isEnabled=false
                            btn_stop_audio.isEnabled=false
                            btn_record.isEnabled=false
                            btn_stop_record.isEnabled=true
                        }
                        btn_stop_record.setOnClickListener {
                            try {
                                val file=File(folder,fileName)
                                recorder.stop()
                                tv_recording.text="已儲存在${file.absolutePath}"
                                btn_record.isEnabled=true
                                btn_stop_record.isEnabled=false
                                btn_play_audio.isEnabled=true
                                btn_stop_audio.isEnabled=false

                            }catch (e:Exception){
                                e.printStackTrace()
                                recorder.reset()
                                tv_recording.text="錄音失敗"
                                btn_record.isEnabled=true
                                btn_stop_record.isEnabled=false
                                btn_play_audio.isEnabled=false
                                btn_stop_audio.isEnabled=false
                            }
                        }
                        btn_play_audio.setOnClickListener {
                            val file=File(folder,fileName)
                            player.setDataSource(applicationContext, Uri.fromFile(file))
                            player.setVolume(1f,1f)
                            player.prepare()
                            player.start()
                            tv_recording.text="播放錄音中...."
                            btn_play_audio.isEnabled=false
                            btn_stop_audio.isEnabled=true
                            btn_record.isEnabled=false
                            btn_stop_record.isEnabled=false
                        }
                        btn_stop_audio.setOnClickListener {
                            player.stop()
                            player.release()
                            tv_recording.text="播放結束"
                            btn_play_audio.isEnabled=true
                            btn_stop_audio.isEnabled=false
                            btn_record.isEnabled=true
                            btn_stop_record.isEnabled=false
                        }
                        btn_save_audio_path.setOnClickListener {
                            val file=File(folder,fileName)
                            val sharedPreferences:SharedPreferences=getSharedPreferences("caller_information", MODE_PRIVATE)
                            val editor:SharedPreferences.Editor=sharedPreferences.edit()
                            editor.putString("audio_path",Uri.fromFile(file).toString())
                            editor.apply()
                            player.stop()
                            player.reset()
                            finish()
                        }
                        player.setOnCompletionListener {
                            it.reset()
                            tv_recording.text="播放結束"
                            btn_play_audio.isEnabled=true
                            btn_stop_audio.isEnabled=false
                            btn_record.isEnabled=true
                            btn_stop_record.isEnabled=false
                        }
                    }
                }
                Thread.sleep(200)
                Log.e("JAMES","inthread")
            }

        })
        thread.start()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_audio)
        val permission=android.Manifest.permission.RECORD_AUDIO
        if(ActivityCompat.checkSelfPermission(this,permission)
        !=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(permission),0)
        }
        else{
            setFolder()
            setListener()
        }
    }

    override fun finish() {
        super.finish()
        player.stop()
        player.release()
        val file=File(folder,fileName)
        val sharedPreferences:SharedPreferences=getSharedPreferences("caller_information", MODE_PRIVATE)
        val editor:SharedPreferences.Editor=sharedPreferences.edit()
        editor.putString("audio_path",Uri.fromFile(file).toString())
        editor.apply()
        leave_activity=true
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults.isNotEmpty() && requestCode==0)
        {
            val result=grantResults[0]
            if(result==PackageManager.PERMISSION_DENIED)
                Toast.makeText(this,"未允許權限",Toast.LENGTH_SHORT).show()
            else{
                setFolder()
                setListener()
            }
        }
    }
}