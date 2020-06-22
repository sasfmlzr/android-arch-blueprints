package com.distillery.android.blueprints.mvvm

import android.graphics.Paint
import android.widget.TextView

/**
 * pass true to strike through the text view else it clears all the textview flags
 */
infix fun TextView.strikeThrough(isStrike: Boolean) {
    paintFlags = if (isStrike) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG else 0
}
