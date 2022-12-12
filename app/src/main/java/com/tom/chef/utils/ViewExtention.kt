package com.tom.chef.utils

import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.Navigation
import com.google.android.material.card.MaterialCardView
import com.tom.chef.R

fun View.makeVisible(){
    this.visibility=View.VISIBLE
}
fun View.makeInvisible(){
    this.visibility=View.INVISIBLE
}
fun View.makeGone(){
    this.visibility=View.GONE
}
fun View.setShowCondition(condition:Boolean){
    if (condition){
        this.makeVisible()
    }else{
        this.makeGone()
    }
}

fun LoadingDialog?.showLocal(){
    try {
        this?.show()
    }catch (e:Exception){
        e.printStackTrace()
    }
}
fun LoadingDialog?.hideLocal(){
    try {
        this?.dismiss()
    }catch (e:Exception){
        e.printStackTrace()
    }
}

fun MaterialCardView.setCustomBackground(color:Int){
    this.setCardBackgroundColor(color)
}
fun Window?.setWhiteColor(activity:Activity){
       this?.setColors(activity.getColor(R.color.white),activity.getColor(R.color.white))
}
fun Window.setColors(navigation: Int,status: Int){
    this.navigationBarColor=navigation
    this.statusBarColor=status
}

fun Window?.makeTransparent(){
    this?.setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
    );
}
fun Window?.makeTransparentStatusBar(){
    this?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
    this?.statusBarColor = Color.TRANSPARENT

}

fun Window?.makeTransparentStatusBarBlack(){
    this?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    this?.statusBarColor = Color.TRANSPARENT

}



fun AppCompatActivity.hideSystemBars() {
    val actionBar = supportActionBar
    if (actionBar != null) actionBar.hide()

    val windowInsetsController =
        WindowCompat.getInsetsController(window, window.decorView) ?: return
    windowInsetsController.systemBarsBehavior =
        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
}

fun TextView.updateSelectedColor(boolean: Boolean){
    val contact=this.context
    val selectedColor=contact.getColor(R.color.redD1)
    val notSelectedColor=contact.getColor(R.color.black40)
    if (boolean){
        this.setTextColor(ColorStateList.valueOf(selectedColor))
    }else{
        this.setTextColor(ColorStateList.valueOf(notSelectedColor))
    }
}

fun Activity.mainNavigation(int: Int){
    Navigation.findNavController(this, R.id.fragment_view)
        .navigate(int)
}

fun String?.handleHull():String{
    this?.let {
        return this
    }
    return ""
}