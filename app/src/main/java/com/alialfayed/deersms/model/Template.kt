package com.alialfayed.deersms.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Class do :
 * Created by Group 4 ITI (Eng/Bassen - Eng Fatma - Eng Ali)
 */
@Entity
class Template {

    @PrimaryKey(autoGenerate = true)
    var TemplateId:Int = 0
    lateinit var templateText : String


}