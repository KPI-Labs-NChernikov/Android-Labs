package com.example.lab2

import android.graphics.Color
import android.view.View

fun highlight(view: View) {
    val selectedColorString = "#FFBB86FC"
    val selectedColor = Color.parseColor(selectedColorString)
    view.setBackgroundColor(selectedColor)
}

fun makeTransparent(view: View) {
    view.setBackgroundColor(Color.TRANSPARENT)
}
