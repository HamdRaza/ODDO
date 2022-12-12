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
import com.tom.chef.ui.dashboard.MainActivity
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

    fun makeFlyAnimation(activity: MainActivity, targetView: View,isService:Boolean=false) {
        var  cartButton=targetView
        CircleAnimationUtil().attachActivity(activity)
            .setBorderColor(activity.resources.getColor(R.color.redD1))
            .setTargetView(targetView).setMoveDuration(500).setDestView(cartButton)
            .setAnimationListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    YoYo.with(Techniques.ZoomIn)
                        .duration(400)
                        .repeat(0)
                        .onStart { }.playOn(cartButton)
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            }).startAnimation()
    }

    fun convertDateTime(dtStart: String?): String? {
        var date: Date? = null
        var dateTime = ""
        val format = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        format.timeZone = TimeZone.getTimeZone("UTC")
        try {
            date = format.parse(dtStart)
            println(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val dateFormat = SimpleDateFormat("dd MMM yyyy hh:mm aa")
        dateTime = dateFormat.format(date)
        println("Current Date Time : $dateTime")
        return dateTime
    }
}