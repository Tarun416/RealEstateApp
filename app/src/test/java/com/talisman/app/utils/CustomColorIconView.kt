package com.talisman.app.utils

import android.graphics.PorterDuff
import com.talisman.app.utils.CustomColorIconView
import android.content.res.TypedArray
import android.os.Build
import android.annotation.TargetApi
import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import com.example.tarun.talismanpi.R


/**
 * Created by Tarun on 11/13/17.
 */
class CustomColorIconView : ImageView {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomColorIconView)

        val color = typedArray.getColor(R.styleable.CustomColorIconView_dciv_color, 0)
        setColorFilter(color, PorterDuff.Mode.SRC_ATOP)

        typedArray.recycle()
    }

    fun setImageFilterColor(color: Int) {
        if (color == -1) {
            setColorFilter(null)
        } else {
            setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }
}