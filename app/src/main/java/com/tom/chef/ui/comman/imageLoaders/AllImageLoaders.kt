package com.tom.chef.ui.comman.imageLoaders

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.tom.chef.R
import com.tom.chef.ui.comman.ViewModel

class AllImageLoaders : ViewModel {
    override fun onItemClicked(view: View) {

    }

    override fun close() {

    }


    companion object {

        @JvmStatic
        @BindingAdapter("imageUrl")
        fun imageUrl(view: ImageView, url: Any?) {
            Log.i("loadImage","${url}")
            when(url){
                is String->{
                    view.context?.let {
                        Glide.with(it).load(url.replacePlaceHolder()).placeholder(AppCompatResources.getDrawable(it,R.mipmap.odoo_luncher)).into(view)
                    }
                    return
                }
                is Uri,is Int,is Drawable ->{
                    view.context?.let {
                        Glide.with(it).load(url).placeholder(AppCompatResources.getDrawable(it,R.mipmap.odoo_luncher)).into(view)
                    }
                    return
                }
                else->{
                    view.context?.let {
                        Glide.with(it).load(AppCompatResources.getDrawable(it, R.mipmap.odoo_luncher)).into(view)
                    }
                }
            }
        }

        @JvmStatic
        @BindingAdapter("coverImage")
        fun coverImage(view: ImageView, url: Any?) {
            Log.i("loadImage","${url}")
            when(url){
                is String->{
                    view.context?.let {
                        Glide.with(it).load(url.replacePlaceHolder()).placeholder(AppCompatResources.getDrawable(it,R.mipmap.odoo_luncher)).into(view)
                    }
                    return
                }
                is Uri,is Int,is Drawable ->{
                    view.context?.let {
                        Glide.with(it).load(url).placeholder(AppCompatResources.getDrawable(it,R.mipmap.odoo_luncher)).into(view)
                    }
                    return
                }
                else->{
                    view.context?.let {
                        Glide.with(it).load(AppCompatResources.getDrawable(it, R.mipmap.odoo_luncher)).into(view)
                    }
                }
            }
        }

        @JvmStatic
        @BindingAdapter("customTint")
        fun customTint(view: ImageView, tintColor: String?) {
            tintColor?.let { path->
                view.imageTintList= ColorStateList.valueOf(Color.parseColor(tintColor))
                return
            }

        }
        @JvmStatic
        @BindingAdapter("adHtmlText")
        fun adHtmlText(view: TextView, value: String?){
            if (value.isNullOrEmpty()){
                return
            }
            view.apply {
                text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(value, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    Html.fromHtml(value)
                }
            }
        }
        @JvmStatic
        @BindingAdapter("updateExpanded")
        fun updateExpanded(view: ImageView, boolean: Boolean) {
            if (boolean){
                view.rotation=180f
            }else{
                view.rotation=0f
            }
        }
    }




}
fun String?.replacePlaceHolder():String{
    if (this.isNullOrEmpty()){
        return "asd"
    }
    if (this.equals("https://jarsite.com/tom/public/placeholder.png")){
        return "asd"
    }
    return this
}