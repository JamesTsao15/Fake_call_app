package com.example.fake_call2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val setting_title_array= arrayOf<String>("來電者","來電時間","來電主題","來電顯示照片",
            "來電聲音","來電鈴聲")
        val gridView=findViewById<GridView>(R.id.gridview_set_call_time)
        val item=ArrayList<Item>()
        val icon_array=resources.obtainTypedArray(R.array.icon_list)
        for (i in 0 until icon_array.length()){
            val icon=icon_array.getResourceId(i,0)
            val setting_title=setting_title_array[i]
            item.add(Item(icon,setting_title))
        }
        gridView.adapter= MyAdapter(this,item,R.layout.component_grid_view)


    }
}