package com.alialfayed.deersms.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alialfayed.deersms.model.Message

/**
 * Class do :
 * Created ( Eng Ali)
 */
@Database(entities = [Message::class], version = 1, exportSchema = false)
abstract class MessageDatabase: RoomDatabase() {
    abstract fun getDaoInstance():MessageDao

    companion object{
        private var INSTANCE: MessageDatabase? = null
        fun getInstance(context: Context): MessageDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,MessageDatabase::class.java,
                    "deerSMS.db")
                    .build()
            }

            return INSTANCE as MessageDatabase
        }
    }
}