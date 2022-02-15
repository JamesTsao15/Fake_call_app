package com.example.fake_call2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridView
import android.widget.TextView

class Theme_Activity : AppCompatActivity() {
    private lateinit var gridView_theme_pick: GridView
    private lateinit var tv_styleselsct:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme)
        gridView_theme_pick=findViewById(R.id.gridView_theme_style)
        tv_styleselsct=findViewById(R.id.textView_style_selected)
        val setting_title_array= arrayOf<String>("明亮風格","暗系風格")
        val theme_item=ArrayList<Item>()
        val theme_array=resources.obtainTypedArray(R.array.theme_list)
        for (i in 0 until theme_array.length()){
            val icon=theme_array.getResourceId(i,0)
            val setting_title=setting_title_array[i]
            theme_item.add(Item(icon,setting_title))
        }
        gridView_theme_pick.adapter= MyAdapter(this,theme_item,R.layout.custom_theme_gridview)
        gridView_theme_pick.setOnItemClickListener { adapterView, view, position, l ->
            when(position){
               0->tv_styleselsct.text="目前風格為:明亮風格"
               1->tv_styleselsct.text="目前風格為:暗系風格"
            }
        }

    }
}