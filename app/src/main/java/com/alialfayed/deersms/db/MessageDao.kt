package com.alialfayed.deersms.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alialfayed.deersms.model.Message

/**
 * Class do :
 * Created by (Eng Ali)
 */
@Dao
interface MessageDao {
    @Insert
    fun addMessage(message: Message):Long

    @Query("select * from Message")
    fun getAllMessages(): LiveData<List<Message>>

    @Query("select * from Message where msgId = :msgID")
    fun getMessageById(msgID: Long):Message

    @Delete
    fun deleteMessage(message: Message)

    @Update
    fun updateMessage(message: Message)
}