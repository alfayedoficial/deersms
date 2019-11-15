package com.alialfayed.deersms.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Class do :
 * Created by ( Eng Ali)
 */
 class Contacts{
    private var name:String
    private var phone:String

    constructor(name:String,phone:String){
        this.name=name
        this.phone=phone
    }

    fun getName():String{
        return name
    }

    fun getPhone():String{
        return phone
    }

}
