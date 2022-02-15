package com.example.fake_call2

import android.annotation.SuppressLint
import android.content.SharedPreferences
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
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("caller_information", MODE_PRIVATE)
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
        val isDarkTheme:Boolean=sharedPreferences.getBoolean("isDarkTheme",false)
        if(isDarkTheme==true){
            tv_styleselsct.text="目前設定風格為:暗系風格"
        }
        else{
            tv_styleselsct.text="目前設定風格為:明亮風格"
        }

    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("caller_information", MODE_PRIVATE)
        val editor: SharedPreferences.Editor=sharedPreferences.edit()
        gridView_theme_pick.setOnItemClickListener { adapterView, view, position, l ->
            when(position){
                0->{
                    editor.putBoolean("isDarkTheme",false)
                    tv_styleselsct.text="目前設定風格為:明亮風格"
                    editor.apply()
                }
                1->{
                    editor.putBoolean("isDarkTheme",true)
                    tv_styleselsct.text="目前設定風格為:暗系風格"
                    editor.apply()
                }

            }
        }
    }
}