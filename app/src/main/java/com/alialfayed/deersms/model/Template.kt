package com.alialfayed.deersms.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Class do :
 * Created by ( Eng Ali)
 */
@Entity
class Template {

    @PrimaryKey(autoGenerate = true)
    var TemplateId: Int = 0
    var templateText = ""
        get() {
            return field
        }
        set(value) {
            field = value
        }


}