package com.tom.chef.ui.dashboard.fragments.account.subFragments.support

import android.content.Intent
import android.net.Uri
import com.tom.chef.newBase.BaseActivity
import com.tom.chef.ui.comman.ViewModel
import com.tom.chef.utils.startEmailIntent


class SupportViewModel(val baseActivity: BaseActivity): ViewModel {


    var phoneNumber="+97152320651616"
    var email="Info@tom.com"
    var whatsApp="+97152320651616"
    var liveChat=""

    fun onPhoneClicked(){
        baseActivity.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${phoneNumber}")))
    }
    fun onEmailClicked(){
        baseActivity.startEmailIntent(email=email,"Support")
    }
    fun onWhatsAppClicked(){
        baseActivity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(String.format("https://api.whatsapp.com/send?phone=%s&text=%s", whatsApp, "HI"))))
    }
    fun onLiveChatClicked(){

    }

}