package com.example.fake_call2

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

data class Item(val icon:Int,val setting_title:String)
class MyAdapter(context: Context,data:ArrayList<Item>,
                private val layout: Int)
    : ArrayAdapter<Item>(context,layout,data) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view=View.inflate(parent.context,layout,null)
        val item=getItem(position)?:return view
        val img_icon=view.findViewById<ImageView>(R.id.imageView)
        img_icon.setImageResource(item.icon)
        val tv_setting=view.findViewById<TextView>(R.id.textView)
        tv_setting.text=item.setting_title
        return view
    }
}