package com.alialfayed.deersms.repo

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alialfayed.deersms.db.MessageDao
import com.alialfayed.deersms.db.MessageDatabase
import com.alialfayed.deersms.model.Message

/**
 * Class do :
 * Created by ( Eng Ali)
 */
class MessageRepository(context: Context) {
    private var daoInstance: MessageDao
    private var allMessages: LiveData<List<Message>> = MutableLiveData()
    private var activity: Activity? = null
    init {
        val messageDate = MessageDatabase.getInstance(context)
        daoInstance = messageDate.getDaoInstance()

    }

    fun getAllMessages():LiveData<List<Message>>{
        allMessages = daoInstance.getAllMessages()
        return allMessages
    }
    fun addMessage(message: Message):Long{
        class AddMessage:AsyncTask<Unit,Unit,Long>(){
            override fun doInBackground(vararg p0: Unit?): Long {
                return daoInstance.addMessage(message)
            }
        }
        return AddMessage().execute().get()
    }

    fun deleteMessage(message: Message){
        class DeleteMessage:AsyncTask<Unit,Unit,Unit>(){
            override fun doInBackground(vararg p0: Unit?) {
                daoInstance.deleteMessage(message)
            }
        }
        DeleteMessage().execute()
    }

    fun updateMessage(message: Message){
        class UpdateMessage:AsyncTask<Unit,Unit,Unit>(){
            override fun doInBackground(vararg p0: Unit?) {
                daoInstance.updateMessage(message)
            }
        }
        UpdateMessage().execute()
    }

    fun getMessageById(messageId:Long):Message{
        var message:Message
        class GetMessageById:AsyncTask<Unit,Unit,Message>(){
            override fun doInBackground(vararg params: Unit?): Message {
                return daoInstance.getMessageById(messageId)
            }
        }
        return GetMessageById().execute().get()
    }

}