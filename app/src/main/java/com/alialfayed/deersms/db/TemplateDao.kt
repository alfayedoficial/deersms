package com.alialfayed.deersms.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.alialfayed.deersms.model.Template

/**
 * Class do :
 * Created by ( Eng Ali)
 */
@Dao
interface TemplateDao {
    @Insert
    fun addTemplate(template:Template)

    @Query("select * from Template")
    fun getTemplate():LiveData<List<Template>>
}