package com.alialfayed.deersms.viewmodel

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.telephony.SmsManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.alialfayed.deersms.model.Contacts
import com.alialfayed.deersms.model.Message
import com.alialfayed.deersms.repo.FirebaseHandler
import com.alialfayed.deersms.repo.MessageRepository
import com.alialfayed.deersms.utils.SMSReceiver
import com.alialfayed.deersms.view.activity.*
import com.alialfayed.deersms.view.fragment.ContactsFragment
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_add_message.*
import java.net.URLEncoder
import java.util.*


/**
 * Class do :
 * Created by ( Eng Ali)
 */
class AddMessageViewModel : ViewModel() {

    lateinit var addMessageActivity: AddMessageActivity
    lateinit var firebaseHandler: FirebaseHandler

    fun setAddMessage(activity: Activity){
        this.addMessageActivity = activity as AddMessageActivity
        this.firebaseHandler = FirebaseHandler(activity,this)

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
        val intent = Intent(addMessageActivity, TemplatesActivity::class.java)
//        addMessageActivity.startActivity(intent)
        addMessageActivity.startActivityForResult(intent, resultTemplate)
    }

    fun sendWhatsapp(numberText: String) {
        val mes = "test whatsapp"

        val packageManager = addMessageActivity.getPackageManager()
        val i = Intent(Intent.ACTION_VIEW)
        try {
            val url =
                "https://api.whatsapp.com/send?text=" + numberText + URLEncoder.encode(mes, "UTF-8")
            i.setPackage("com.whatsapp")
            i.data = Uri.parse(url)
            if (i.resolveActivity(packageManager) != null) {
                addMessageActivity.startActivity(i)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
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

    fun setSMSAlarm(smsId: String, personName: String, receiverNumber: String, SMSMessage: String,
                    date: String, time: String, status: String, type: String, currentUser: String,
                    calendar: Long , smsDelivered :String) {

        addMessageActivity.setSMSAlarm(smsId, personName, receiverNumber, SMSMessage, date,
            time, status, type, currentUser, calendar , smsDelivered)
    }

    fun scheduleMessageViewModel(personName: String, receiverNumber: String, message: String,
                                 date: String, time: String, status: String, type: String,
                                 calendar: Long , smsDelivered :String) {

        firebaseHandler.scheduleMessageRepository(personName, receiverNumber, message, date, time,
            status, type, calendar , smsDelivered)
    }
}