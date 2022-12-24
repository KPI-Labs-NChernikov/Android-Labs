package com.example.lab3

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ListView


class NoScrollListView : ListView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val customHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
            Int.MAX_VALUE shr 2, MeasureSpec.AT_MOST
        )
        super.onMeasure(widthMeasureSpec, customHeightMeasureSpec)
        val params: ViewGroup.LayoutParams = layoutParams
        params.height = measuredHeight
    }
}