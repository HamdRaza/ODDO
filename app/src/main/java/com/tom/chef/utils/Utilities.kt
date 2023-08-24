package com.tom.chef.utils

import android.animation.Animator
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.util.Log
import android.view.View
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.tom.chef.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object Utilities {
     fun loadBitmapFromView(view: View, width: Int, height: Int): Bitmap {
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) bgDrawable.draw(canvas) else canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        Log.e("width", "=$width")
        Log.e("height", "=$height")
        return returnedBitmap
    }

}