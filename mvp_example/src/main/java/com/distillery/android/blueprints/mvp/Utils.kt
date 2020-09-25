package com.distillery.android.blueprints.mvp

import android.content.Context
import android.util.DisplayMetrics

class Utils {

    fun convertDpToPixel(dp: Float, context: Context): Float {
        return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun convertPixelsToDp(px: Int, context: Context): Float {
        return (px / (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)).toFloat()
    }

    fun isLargeTextEnabled(context: Context): Boolean {
        val c = context.resources.configuration
        val scale = c.fontScale
        return scale>1
    }

    /*

    itemBinder.root.announceForAccessibility(
                    TransactionUtils.mapTxTypesFilter.find
                    { warningString -> it.first == warningString.first }?.second?.let { it1 -> getString(it1) }
            )





     */
}