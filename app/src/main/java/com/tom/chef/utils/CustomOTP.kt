package com.tom.chef.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


class GenericKeyEvent internal constructor(private val currentView: EditText, private val previousView: EditText?,val list:List<View>) : View.OnKeyListener{
    override fun onKey(p0: View, keyCode: Int, event: KeyEvent): Boolean {
        if(event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.id != list.get(0).id && currentView.text.isEmpty()) {
            //If current is empty then previous EditText's number will also be deleted
            previousView?.text = null
            previousView?.requestFocus()
            return true
        }
        return false
    }


}

class GenericTextWatcher internal constructor(private val currentView: View, private val nextView: View?,val list: List<View>) : TextWatcher {
    override fun afterTextChanged(editable: Editable) {
        list.indexOf(currentView).let {
            nextView?.requestFocus()
            if (it==list.size-1 && editable.toString().isNotEmpty()){
                currentView.let {
                    val  imm=it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(it.windowToken,0)
                }
                return
            }
        }
    }

    override fun beforeTextChanged(
        arg0: CharSequence,
        arg1: Int,
        arg2: Int,
        arg3: Int
    ) {
    }

    override fun onTextChanged(
        arg0: CharSequence,
        arg1: Int,
        arg2: Int,
        arg3: Int
    ) {
    }

}


fun List<EditText>.makeCustomOTP(){
    for (i in 0 until this.size){
        this.get(i).addTextChangedListener(GenericTextWatcher(this.get(i),this.getOrNull(i+1),this))
    }
    for (i in this.size-1 downTo 0){
        this.get(i).setOnKeyListener(GenericKeyEvent(this.get(i), this.getOrNull(i-1),this))
    }
    this.firstOrNull()?.requestFocus()
}