package com.example.fake_call2

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.GridView
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson

class Schedule_Call_Activity : AppCompatActivity() {
    private lateinit var gridView_timer_picker: GridView
    private lateinit var customTimerPickerView: custom_timer_picker_view
    private lateinit var tv_custom_timer_ms:TextView
    private lateinit var numberPicker_Minute:NumberPicker
    private lateinit var numberPicker_Second: NumberPicker
    fun numberPicker_setting(){
        numberPicker_Minute.maxValue=60
        numberPicker_Second.maxValue=59
        numberPicker_Minute.minValue=0
        numberPicker_Second.minValue=1
        numberPicker_Minute.value=0
        numberPicker_Second.value=1
    }
    fun save_timer_data_in_sharepreference(Second_ms:Int){
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("caller_information", MODE_PRIVATE)
        val editor: SharedPreferences.Editor=sharedPreferences.edit()
        editor.putInt("timer_setting_time_ms",Second_ms)
        editor.apply()
    }
    fun load_timer_data_in_sharepreference():Int{
        val sharedPreferences:SharedPreferences=
            getSharedPreferences("caller_information", MODE_PRIVATE)
        val timer_ms_data=sharedPreferences.getInt("timer_setting_time_ms",0)
        return timer_ms_data
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_call)
        gridView_timer_picker=findViewById(R.id.gridView_timerpicker)
        customTimerPickerView=findViewById(R.id.customtimer_view)
        val timerPicker_gridview_array=ArrayList<Item>()
        val icon_array=resources.obtainTypedArray(R.array.timer)
        val timePicker_title_array= arrayOf("10秒","30秒","1分鐘","5分鐘","15分鐘","30分鐘")
        for (i in 0 until timePicker_title_array.size){
            val icon=icon_array.getResourceId(0,0)
            val setting_title=timePicker_title_array[i]
            timerPicker_gridview_array.add(Item(icon,setting_title))
        }
        gridView_timer_picker.adapter=MyAdapter(this,timerPicker_gridview_array,R.layout.timer_picker_grid_view)
        tv_custom_timer_ms=customTimerPickerView.findViewById<TextView>(R.id.textView_customer_Picker)
        if(load_timer_data_in_sharepreference()>=0){
            var time=load_timer_data_in_sharepreference()
            var minute=(time/1000)/60
            var second=(time/1000)-minute*60
            tv_custom_timer_ms.text="自訂義時間(${minute}分${second}秒)"
        }
    }

    override fun onResume() {
        super.onResume()
        customTimerPickerView.setOnClickListener {
            val inflater:LayoutInflater=this.layoutInflater
            val timerPicker_view=inflater.inflate(R.layout.timerpicker_view,null)
            var customTimer_Minute:Int=0
            var customTimer_Second:Int=1
            numberPicker_Minute=timerPicker_view.findViewById(R.id.numberPicker_minute)
            numberPicker_Second=timerPicker_view.findViewById(R.id.numberPicker_second)
            numberPicker_setting()
            val builder=AlertDialog.Builder(this)
            builder.setTitle("自訂義時間")
            builder.setMessage("請選擇自訂義時間:")
            builder.setView(timerPicker_view)
            numberPicker_Minute.setOnValueChangedListener { numberPicker, oldval, newval ->
                customTimer_Minute=newval
                if(newval>=1){
                    numberPicker_Second.minValue=0
                }
                else{
                    numberPicker_Second.minValue=1
                }
            }
            numberPicker_Second.setOnValueChangedListener { numberPicker, oldval, newval ->
               customTimer_Second=newval
            }
            builder.setPositiveButton("確定"){
                dialog,which->
                var timer_time_ms:Int=(customTimer_Minute*60+customTimer_Second)*1000
                save_timer_data_in_sharepreference(timer_time_ms)
                if(load_timer_data_in_sharepreference()>=0){
                    var time=load_timer_data_in_sharepreference()
                    var minute=(time/1000)/60
                    var second=(time/1000)-minute*60
                    tv_custom_timer_ms.text="自訂義時間(${minute}分${second}秒)"
                }
                Toast.makeText(this,"已設定時間為$customTimer_Minute 分 $customTimer_Second 秒"
                    ,Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("取消"){
                dialog,which->
            }
            val custom_timer_dialog:AlertDialog=builder.create()
            custom_timer_dialog.show()
        }
        gridView_timer_picker.setOnItemClickListener {
                adapterView, view, position, l ->
            when(position){
                0->save_timer_data_in_sharepreference(10000)
                1->save_timer_data_in_sharepreference(30000)
                2->save_timer_data_in_sharepreference(60000)
                3->save_timer_data_in_sharepreference(300000)
                4->save_timer_data_in_sharepreference(900000)
                5->save_timer_data_in_sharepreference(1800000)
            }
            if(load_timer_data_in_sharepreference()>=0){
                var time=load_timer_data_in_sharepreference()
                var minute=(time/1000)/60
                var second=(time/1000)-minute*60
                tv_custom_timer_ms.text="自訂義時間(${minute}分${second}秒)"
            }
        }

    }
}