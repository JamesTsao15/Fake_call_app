package com.example.fake_call2

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class custom_timer_picker_view@JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0)
    : ConstraintLayout(context,attrs,defStyleAttr)
{

    val view= View.inflate(context,R.layout.custom_timer_picker_view,this)
    init {
        if (attrs !=null){
            val attributes=context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.custom_timer_picker_view,0,0
            )
            val tv_custompicker=view.findViewById<TextView>(R.id.textView_customer_Picker)
            tv_custompicker.text=attributes.getString(R.styleable.custom_timer_picker_view_timer_title)?:"自訂義時間"
            val imgView_caller_photo=view.findViewById<ImageView>(R.id.imageView_timer_icon)
            imgView_caller_photo.setImageResource(attributes.getResourceId(R.styleable.custom_timer_picker_view_custom_timer_icon,R.drawable.timer))

        }
    }
}