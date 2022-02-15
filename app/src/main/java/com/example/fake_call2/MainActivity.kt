package com.example.fake_call2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.GridView
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    private lateinit var gridView_home: GridView
    private lateinit var btn_call:ImageButton
    private lateinit var callerInformationView: caller_information_view
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
    }

    override fun onResume() {
        super.onResume()
        btn_call.setOnClickListener {
            Log.e("JAMES","on click calling")
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
                4->Log.e("JAMES","來電聲音")
                5->Log.e("JAMES","來電鈴聲")
            }

        }
        callerInformationView.setOnClickListener {
            Log.e("JAMES","onclick_view")
        }
    }
}