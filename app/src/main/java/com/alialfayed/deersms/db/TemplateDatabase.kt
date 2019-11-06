package com.alialfayed.deersms.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alialfayed.deersms.model.Template

/**
 * Class do :
 * Created by( Eng Ali)
 */
@Database(entities = [Template::class],version = 1,exportSchema = false)
abstract class TemplateDatabase :RoomDatabase() {
    abstract fun getDaoInstance():TemplateDao

    companion object{
        private var INSTANCE : TemplateDatabase? = null
        fun getInstance(context: Context):TemplateDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,TemplateDatabase::class.java,"template.dp").build()
            }
            return INSTANCE as TemplateDatabase
        }
    }
}