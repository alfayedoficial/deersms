package com.alialfayed.deersms.model

import android.graphics.Bitmap

/**
 * Class do :
 * Created ( Eng Ali)
 */
class ContactList {
    var name = ""
    var number = ""
    var image: Bitmap? = null
    private var isSelected: Boolean = false

    fun setSelected(selected: Boolean) {
        isSelected = selected
    }


    fun isSelected(): Boolean {
        return isSelected;
    }

}