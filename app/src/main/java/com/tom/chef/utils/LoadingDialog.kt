package com.tom.chef.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.annotation.StyleRes
import com.tom.chef.R



class LoadingDialog constructor(context: Context, @StyleRes themeResId: Int) :
    Dialog(context, themeResId) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading)

    }


}