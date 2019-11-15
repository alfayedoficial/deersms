package com.alialfayed.deersms.viewmodel

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.telephony.SmsManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.alialfayed.deersms.repo.FirebaseHandler
import com.alialfayed.deersms.view.activity.AddMessageActivity
import com.alialfayed.deersms.view.activity.ContactsActivity
import com.alialfayed.deersms.view.activity.TemplatesActivity
import kotlinx.android.synthetic.main.activity_add_message.*
import java.util.*


/**
 * Class do :
 * Created by ( Eng Ali)
 */
class AddMessageViewModel : ViewModel() {

    lateinit var addMessageActivity: AddMessageActivity
    lateinit var firebaseHandler: FirebaseHandler

    fun setAddMessage(activity: Activity) {
        this.addMessageActivity = activity as AddMessageActivity
        this.firebaseHandler = FirebaseHandler(activity, this)

    }


    val resultTemplate = 0
    var msgCalendar: Calendar = Calendar.getInstance()
    val MSG_ID = "SMS"

    fun sendSMS(number: String, message: String) {
        val sms = SmsManager.getDefault()
        sms.sendTextMessage(number, null, message, null, null)
        //      SmsManager.getDefault().sendTextMessage(number,null,Message,null,null)
        Toast.makeText(addMessageActivity, "Sms sent", Toast.LENGTH_SHORT).show()
    }

    fun contacts() {
        val intent = Intent(addMessageActivity, ContactsActivity::class.java)
        addMessageActivity.startActivity(intent)
    }

    fun template() {
        val phone = addMessageActivity.edtNumber_AddMessage.text.toString()
        val name = addMessageActivity.edtname_AddMessage.text.toString()
        val intent = Intent(addMessageActivity, TemplatesActivity::class.java)
        intent.putExtra("teName",name)
        intent.putExtra("tePhone",phone)
        Log.i("TagViewModel",phone + name)
//        Toast.makeText(addMessageActivity,phone +name , Toast.LENGTH_LONG ).show()

//        addMessageActivity.startActivity(intent)
        addMessageActivity.startActivityForResult(intent, resultTemplate)
    }

    fun sendWhatsapp(
        personName: String,
        numberText: String,
        messageText: String,
        date: String,
        time: String,
        status: String,
        type: String,
        smsDelivered: String,
        calendar: Long

    ) {
        val number = "+201014775215"
//            val message = ""
        firebaseHandler.scheduleWhatsAppMessageRepository(
            personName, numberText, messageText, date, time,
            status, type,smsDelivered,calendar
        )
        var intent = Intent(Intent.ACTION_VIEW);
        intent.data = Uri.parse("http://api.whatsapp.com/send?phone=$numberText&text=$messageText")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        addMessageActivity.startActivity(intent)
        Toast.makeText(addMessageActivity, "WhatsApp sent", Toast.LENGTH_SHORT).show()
    }




fun resetActivity() {
    addMessageActivity.edtNumber_AddMessage.setText("")
    addMessageActivity.edtMessage_AddMessage.setText("")
    addMessageActivity.txtDate_AddMessage.setText("")
    addMessageActivity.txtTime_AddMessage.setText("")
    addMessageActivity.checkerButtonTime_AddMessage.setVisibility(View.VISIBLE)
    addMessageActivity.linearLayout_AddMessage.setVisibility(View.GONE)

}

fun setSMSAlarm(
    smsId: String, personName: String, receiverNumber: String, SMSMessage: String,
    date: String, time: String, status: String, type: String, currentUser: String,
    calendar: Long, smsDelivered: String
) {

    addMessageActivity.setSMSAlarm(
        smsId, personName, receiverNumber, SMSMessage, date,
        time, status, type, currentUser, calendar, smsDelivered
    )
}


fun scheduleMessageViewModel(
    personName: String, receiverNumber: String, message: String,
    date: String, time: String, status: String, type: String,
    calendar: Long, smsDelivered: String
) {

    firebaseHandler.scheduleMessageRepository(
        personName, receiverNumber, message, date, time,
        status, type, calendar, smsDelivered
    )
}
}