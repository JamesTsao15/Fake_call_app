package com.example.fake_call2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridView

class Schedule_Call_Activity : AppCompatActivity() {
    private lateinit var gridView_timer_picker: GridView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_call)
        gridView_timer_picker=findViewById(R.id.gridView_timerpicker)
        val timerPicker_gridview_array=ArrayList<Item>()
        val icon_array=resources.obtainTypedArray(R.array.timer)
        val timePicker_title_array= arrayOf("10秒","30秒","1分鐘","5分鐘","15分鐘","30分鐘")
        for (i in 0 until timePicker_title_array.size){
            val icon=icon_array.getResourceId(0,0)
            val setting_title=timePicker_title_array[i]
            timerPicker_gridview_array.add(Item(icon,setting_title))
        }
        gridView_timer_picker.adapter=MyAdapter(this,timerPicker_gridview_array,R.layout.timer_picker_grid_view)
    }
}