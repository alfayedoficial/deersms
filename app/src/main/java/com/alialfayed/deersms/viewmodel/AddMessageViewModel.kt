package com.alialfayed.deersms.viewmodel

import android.content.Intent
import android.os.AsyncTask
import android.telephony.SmsManager

import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.alialfayed.deersms.view.activity.AddMessageActivity
import com.alialfayed.deersms.view.activity.ContactsActivity
import com.alialfayed.deersms.view.activity.TemplatesActivity

/**
 * Class do :
 * Created by ( Eng Ali)
 */
class AddMessageViewModel :ViewModel() {

    val resultTemplate = 0
    lateinit var addMessageActivity: AddMessageActivity
    fun setAddMessage(addMessageActivity: AddMessageActivity){
        this.addMessageActivity = addMessageActivity as AddMessageActivity

    }

    fun sendSMS(number:String,message:String){
        AsyncTask.execute {   SmsManager.getDefault().sendTextMessage(number,null,message,null,null)

        }

        Toast.makeText(addMessageActivity,"Sms sent",Toast.LENGTH_SHORT).show()
    }

    fun contacts(){
        val intent = Intent(addMessageActivity, ContactsActivity::class.java)
        addMessageActivity.startActivity(intent)
    }

    fun template(){
        val intent = Intent(addMessageActivity, TemplatesActivity::class.java)
//        addMessageActivity.startActivity(intent)
        addMessageActivity.startActivityForResult(intent,resultTemplate)
    }
}