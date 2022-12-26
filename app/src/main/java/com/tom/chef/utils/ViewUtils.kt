package com.tom.chef.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import com.google.android.material.textfield.TextInputEditText
import com.hbb20.CountryCodePicker
import com.tom.chef.BuildConfig
import com.tom.chef.R
import com.tom.chef.models.auth.User
import com.tom.chef.models.profile.Address
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun <A : Activity> Activity.startNextActivity(activity: Class<A>) {
    Intent(this, activity).also {
        startActivity(it)
    }
}

fun Context.myToast(message:String?){
   if(!message.isNullOrEmpty() && !message.contains("Unable to resolve host")){
       Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
   }
}

fun Context.dialogMsgOk(message:String,title:String="Alert",buttonText:String="OK"){
    MaterialAlertDialogBuilder(this)
        .setTitle("$title")
        .setMessage(message)
        .setPositiveButton(
            "$buttonText"
        ) { dialogInterface, i ->
            dialogInterface.dismiss()
        }
        .show()
}

fun TextView.htmlToString(htmlString: String){
    val formatedString = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(htmlString, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(htmlString)
    }
    this.text = formatedString
}



fun View.showBottomSnackBar(message: String?) {
    message?.let {
        Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
    }
}

fun Context.showDT(message: Any?){
    if(BuildConfig.DEBUG){
        Log.i("Log",message.toString())
        Toast.makeText(this,message.toString(),Toast.LENGTH_SHORT).show()
    }
}

fun Context.VectorToBitMapDes(id:Int): BitmapDescriptor?{
    this.VectorToBitMap(id)?.let {
       return BitmapDescriptorFactory.fromBitmap(it)
    }
    return null
}

fun Context.VectorToBitMap(id:Int): Bitmap?{
    ContextCompat.getDrawable(this,id)?.let {
        it.setBounds(0,0,it.intrinsicWidth,it.intrinsicHeight)
        val bitmap= Bitmap.createBitmap(it.minimumWidth,it.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas= Canvas(bitmap)
        it.draw(canvas)
        return bitmap
    }
    return null
}

fun LocalDate.getCurrentMonth():String{
    return SimpleDateFormat("MMM", Locale.ENGLISH).format(this)
}


fun LifecycleOwner.IncludeAutoUpdate(viewPager: ViewPager2){
    val job = lifecycleScope.launch(Dispatchers.Main, start = CoroutineStart.LAZY) {
        var lastMoved=0
        repeat(5000) {
            delay(5000)
            try {
                viewPager.adapter?.let {
                    if(it.itemCount>lastMoved+1){
                        viewPager.setCurrentItem(lastMoved+1,true)
                        lastMoved++
                    }else{
                        viewPager.setCurrentItem(0,true)
                        lastMoved=0
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    job.start()
}

fun View.getLocalText():String{
    if (this is TextInputEditText){
        return this.text.toString()
    }
    if (this is EditText){
        return this.text.toString()
    }
    if (this is TextView){
        return this.text.toString()
    }
    if (this is CountryCodePicker){
        return this.selectedCountryCode
    }
    return ""
}
fun String.handleStartingZero():String{
    if (this.startsWith("0")){
        this.subSequence(IntRange(1,this.length-1)).toString().handleStartingZero()
    }
    return this
}
fun String.removePlus():String{
    return this.replace("+","")
}
fun String.addPlus():String{
    return "+${this.removePlus()}"
}

fun View.setLocalError(string: String?){
    if (this is TextInputEditText){
        this.error=string
        return
    }
    if (this is EditText){
        this.error=string
        return
    }
    if (this is TextView){
        this.error=string
        return
    }
}



fun Int.intToBool():Boolean{
    return this==1
}

fun Boolean.boolToInt():Int{
    if (this){
        return 1
    }
    return 0
}
fun Any.getErrorMessage():String{
    if (this is JSONObject){
       return buildString {
            this@getErrorMessage.keys().forEach {
                   if (this@getErrorMessage.has(it)){
                       append(this@getErrorMessage.getString(it))
                   }
            }
        }.toString()

    }
    return ""
}


fun Long.getMinSec():String {
    if (this<1000){
        return "0:00 sec"
    }

    val minutes = this / 1000 / 60
    val seconds = this / 1000 % 60

    return "${minutes}:${seconds} sec"
}


fun List<EditText>.getOTP():String{
    return buildString {
        this@getOTP.forEach {
            append(it.getLocalText())
        }
    }.toString()
}


fun List<EditText>.validOTP():Boolean{
    this.getOTP().let {
        if (it.length==4){
            return true
        }
    }
    firstOrNull()?.context?.myToast("OTP not valid")
    return false
}
fun Any?.checkForSuccess():Boolean{
    if (this is Int){
        return this.intToBool()
    }
    if (this is Double){
        return this.toInt().intToBool()
    }
    if (this is Long){
        return this.toInt().intToBool()
    }
    if (this is String){
        when(this){
            "1"->{
                return true
            }
            else->{
                return false
            }
        }
    }
    return false
}

fun String?.makeNull():String?{
    if (this.isNullOrEmpty()){
        return null
    }
    if (this.trim().isEmpty()){
        return null
    }
    return this
}
fun Int?.makeNullString():String?{
    if (this==null){
        return null
    }
    return this.toString()
}

fun String?.addPriceTag():String{
    if (this==null){
        return ""
    }
    return "AED ${this}"
}
fun String.addCreditTag(isCredit:Boolean):String{
    if(isCredit){
        return "+ ${this}"
    }
    return "- ${this}"
}
fun String?.addStockUnit(type:Int):String{
    if (this.isNullOrEmpty()){
        return ""
    }
    when(type){
        1->{
            return "${this} ml"
        }
        2->{
           return "${this} Tablets"
        }
        3->{
            return "${this} grm"
        }
        else->{
           return this
        }
    }
}


fun Double.addPriceTag():String{
    return "AED ${this}"
}

fun User.maskPhone():String{
  return  buildString {
        if (!this@maskPhone.dialCode.contains("+")){
            append("+")
        }
        append(this@maskPhone.dialCode)
        append(this@maskPhone.phoneNumber.addStarsPhone())
    }
}
fun String?.addStarsPhone():String{
    if (this.isNullOrEmpty()){
        return ""
    }
    return this.mapIndexed { index, c ->
        if ((this.length-2 == index || this.length-3 == index) || (index>0 && index<3)) "*" else c
    }.joinToString("")
}

fun CalendarDay.getDateTime():Date{
    val calender= Calendar.getInstance()
    calender.set(Calendar.MONTH,this.month-1)
    calender.set(Calendar.DAY_OF_MONTH,this.day)
    calender.set(Calendar.YEAR,this.year)
   return calender.time
}

fun Date.getOnlyMonth():String{
    return SimpleDateFormat("MMMM yyyy", Locale.ENGLISH).format(this)
}

fun Date.nextMonth():CalendarDay{
    val calender=Calendar.getInstance()
    calender.timeInMillis=this.time
    calender.set(Calendar.MONTH,calender.get(Calendar.MONTH)+1)

    return CalendarDay.from(calender.get(Calendar.YEAR),calender.get(Calendar.MONTH)+1,calender.get(Calendar.DAY_OF_MONTH))
}

fun Date.previousMonth():CalendarDay{
    val calender=Calendar.getInstance()
    calender.timeInMillis=this.time
    calender.set(Calendar.MONTH,calender.get(Calendar.MONTH)-1)

    return CalendarDay.from(calender.get(Calendar.YEAR),calender.get(Calendar.MONTH)+1,calender.get(Calendar.DAY_OF_MONTH))
}
fun Date.tomorrow():CalendarDay{
    val calender=Calendar.getInstance()
    calender.timeInMillis=this.time
    calender.set(Calendar.DAY_OF_YEAR,calender.get(Calendar.DAY_OF_YEAR)+1)
    return CalendarDay.from(calender.get(Calendar.YEAR),calender.get(Calendar.MONTH)+1,calender.get(Calendar.DAY_OF_MONTH))

}
fun Date.after6Month():CalendarDay{
    val calender=Calendar.getInstance()
    calender.timeInMillis=this.time
    calender.set(Calendar.MONTH,calender.get(Calendar.MONTH)+6)
    return CalendarDay.from(calender.get(Calendar.YEAR),calender.get(Calendar.MONTH)+1,calender.get(Calendar.DAY_OF_MONTH))

}

fun Date.getFormatedDate():String{
    return SimpleDateFormat("yyyy-MM-dd").format(this)
}

fun String?.toIntLocal():Int{
    if (this.isNullOrEmpty()){
        return 0
    }
    return this.toInt()
}

fun String.getServiceTime():String{
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    sdf.parse(this)?.let {
        return SimpleDateFormat("d MMM yyyy, hh:mm:ss a", Locale.ENGLISH).format(it)
    }
    return ""
}
fun String.getOrderTime():String{
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    sdf.parse(this)?.let {
        return SimpleDateFormat("d MMM yyyy, hh:mm:ss a", Locale.ENGLISH).format(it)
    }
    return ""
}


fun TabLayout.handleClick(viewPager: ViewPager2){
    this.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
        override fun onTabSelected(tab: Tab?) {
            Log.i("TabIndex","002")
          viewPager.currentItem=this@handleClick.selectedTabPosition
        }

        override fun onTabUnselected(tab: Tab?) {

        }

        override fun onTabReselected(tab: Tab?) {

        }
    })
    viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {
             this@handleClick.getTabAt(position)?.select()
        }
    })
    viewPager.isUserInputEnabled=true
}
fun Int.handleIntentClicker(tabLayout: TabLayout){
    if (this>=0){
        tabLayout.getTabAt(this)?.select()
    }

}


fun String.getPaymentMethodName():String{
    return this
}

fun String.getPaymentMethodIcon():Int{
    when(this){
        "1"->{
            return R.drawable.icon_wallet_new
        }
        "2"->{
            return R.drawable.vector_card
        }
        "3"->{
            return R.drawable.ic_apple
        }
        "4"->{
            return R.drawable.ic_google
        }
        else->{
            return R.drawable.vector_card
        }
    }
}

fun ViewPager2.showLeftPreview(){
    this.apply {
        clipToPadding = false   // allow full width shown with padding
        clipChildren = false    // allow left/right item is not clipped
        offscreenPageLimit = 2  // make sure left/right item is rendered
    }

    //increase this offset to show more of left/right
    val offsetPx =
        resources.getDimension(R.dimen._10sdp).toInt().dpToPx(resources.displayMetrics)
    this.setPadding(0, 0, offsetPx, 0)

    //increase this offset to increase distance between 2 items
    val pageMarginPx = resources.getDimension(R.dimen._2sdp).toInt().dpToPx(resources.displayMetrics)
    val marginTransformer = MarginPageTransformer(pageMarginPx)
    this.setPageTransformer(marginTransformer)
}
fun ViewPager2.showLeftRightPreview(){
    this.apply {
        clipToPadding = false   // allow full width shown with padding
        clipChildren = false    // allow left/right item is not clipped
        offscreenPageLimit = 2  // make sure left/right item is rendered
    }

    //increase this offset to show more of left/right
    val offsetPx = resources.getDimension(R.dimen._10sdp).toInt().dpToPx(resources.displayMetrics)
    this.setPadding(offsetPx, 0, offsetPx, 0)

    //increase this offset to increase distance between 2 items
    val pageMarginPx = resources.getDimension(R.dimen._2sdp).toInt().dpToPx(resources.displayMetrics)
    val marginTransformer = MarginPageTransformer(pageMarginPx)
    this.setPageTransformer(marginTransformer)
}
fun Int.dpToPx(displayMetrics: DisplayMetrics): Int = (this * displayMetrics.density).toInt()



fun String.getNotificationTime():String{
    val calendar=Calendar.getInstance()
    this.toLongOrNull()?.let {
        calendar.timeInMillis=it
    }
    val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH)

    return sdf.format(calendar.time)
}

fun Any?.handLocalString():String{
    when(this){
        is Int->{
            return this.toString()
        }
        is Long->{
            return this.toInt().toString()
        }
        is Float->{
            return this.toInt().toString()
        }
        is Double->{
            return this.toInt().toString()
        }
        is String->{
            return this.replace(",","").toString()
        }
        else->{
            return ""
        }
    }
}

fun String.statusHandleRateShowProduct():Boolean{
    when(this){
        "4"->{
            return true
        }
        else->{
            return false
        }
    }
}
fun String.paymentRefundAble():Boolean{
    return when(this){
        "1","2","3","4"->{
            true
        }
        else->{
            false
        }
    }
}
fun String.statusHandleRateShowService():Boolean{
    when(this){
        "4"->{
            return true
        }
        else->{
            return false
        }
    }
}

fun String?.isOutOfStock():Boolean{
    if(this==null){
        return true
    }
    this.toIntOrNull()?.let {
        return it<1
    }
    return false
}

fun Bundle?.handleEmpty():Bundle{
    if (this==null){
        return Bundle()
    }
    return this
}

fun Any.checkRatingNotZero():Boolean{
    val rating=this.getRatingDouble()
    return rating>0
}

fun Any?.getRatingDouble():Double{
    when(this){
        is Int->{
            return this.toDouble()
        }
        is Long->{
            return this.toDouble()
        }
        is Float->{
            return this.toDouble()
        }
        is Double->{
            return this
        }
        is String->{
            this.replace(",","").toDoubleOrNull()?.let {
                return it
            }
            return 0.0
        }
        else->{
            return 0.0
        }
    }

}

fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
    val spannableString = SpannableString(this.text)
    for (link in links) {
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                Selection.setSelection((view as TextView).text as Spannable, 0)
                view.invalidate()
                link.second.onClick(view)
            }
        }
        val startIndexOfLink = this.text.toString().indexOf(link.first)
        spannableString.setSpan(
            clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            ForegroundColorSpan(Color.parseColor("#CB2E14")),
            startIndexOfLink,
            startIndexOfLink + link.first.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    this.movementMethod =
        LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
    this.setText(spannableString, TextView.BufferType.SPANNABLE)
}


fun Address.userAddressComplete(showBuilding:Boolean=false):String{
    return buildString {
        if (showBuilding){
            append(buildingName)
            append("  ")
        }
        append(landMark)
        append("  ")
        append(address)
        append("  ")
        append(location)
    }
}

fun Activity.startEmailIntent(email:String, subject: String?) {

    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$email")
        putExtra(Intent.EXTRA_SUBJECT, subject)
    }
    startActivity(Intent.createChooser(emailIntent, "Send feedback"))
}