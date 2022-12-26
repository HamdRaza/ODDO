package com.tom.chef.ui.comman.imageLoaders

import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.view.View
import android.widget.ImageView
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
        fun loadImage(view: ImageView, url: String?) {
            url?.let { path->
                view.context?.let {
                    Glide.with(it).load(path).placeholder(AppCompatResources.getDrawable(it,R.drawable.app_logo)).into(view)
                }
                return
            }
            view.context?.let {
                Glide.with(it).load(AppCompatResources.getDrawable(it,R.drawable.app_logo)).into(view)
            }
        }
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, url: Any?) {
            if (url is String){
                view.context?.let {
                    Glide.with(it).load(url).placeholder(AppCompatResources.getDrawable(it,R.drawable.app_logo)).into(view)
                }
                return
            }
            if (url is Uri){
                view.context?.let {
                    Glide.with(it).load(url).placeholder(AppCompatResources.getDrawable(it,R.drawable.app_logo)).into(view)
                }
                return
            }
            view.context?.let {
                Glide.with(it).load(AppCompatResources.getDrawable(it,R.drawable.app_logo)).into(view)
            }
        }
        @JvmStatic
        @BindingAdapter("coverImage")
        fun coverImage(view: ImageView, url: String?) {
            url?.let { path->
                view.context?.let {
                    Glide.with(it).load(path).placeholder(AppCompatResources.getDrawable(it,R.drawable.app_logo)).into(view)
                }
                return
            }
            view.context?.let {
                Glide.with(it).load(AppCompatResources.getDrawable(it,R.drawable.app_logo)).into(view)
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