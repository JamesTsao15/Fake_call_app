package com.example.fake_call2

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout


class caller_information_view constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    :ConstraintLayout(context,attrs,defStyleAttr)
{
    val view= View.inflate(context,R.layout.caller_information,this)
    init {
        if (attrs !=null){
            val attributes=context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.caller_information_view,0,0
            )
            val tv_callername=view.findViewById<TextView>(R.id.tv_caller_name)
            tv_callername.text=attributes.getString(R.styleable.caller_information_view_caller_name)?:"未知來電"
            val tv_caller_phone_number=view.findViewById<TextView>(R.id.tv_caller_phone_number)
            tv_caller_phone_number.text=attributes.getString(R.styleable.caller_information_view_caller_phone_nunber)
                ?:"0912345678"
            val imgView_caller_photo=view.findViewById<ImageView>(R.id.imageView_callerphoto)
            imgView_caller_photo.setImageResource(attributes.getResourceId(R.styleable.caller_information_view_caller_photo,R.drawable.callerphoto))

        }
    }
}