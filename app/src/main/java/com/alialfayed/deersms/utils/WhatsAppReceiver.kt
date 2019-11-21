package com.alialfayed.deersms.utils

import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.telephony.SmsManager
import android.widget.Toast
import com.alialfayed.deersms.model.MessageFirebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * Class do :
 * Created by ( Eng Ali)
 */
class WhatsAppReceiver : BroadcastReceiver() {
    private lateinit var sentPendingIntent: PendingIntent
    private lateinit var deliveredPendingIntent: PendingIntent
    private lateinit var smsSentReceiver: BroadcastReceiver
    private lateinit var smsDeliveredReceiver: BroadcastReceiver

    private lateinit var messageId: String
    private lateinit var messagePersonName: String
    private lateinit var messagePersonNumber: String
    private lateinit var messageText: String
    private lateinit var messageDate: String
    private lateinit var messageTime: String
    private lateinit var messageStatus: String
    private lateinit var messageSendVia: String
    private lateinit var userID: String
    private lateinit var messageDelivered:String
    private  var messageCalender: Long = 0

    lateinit var databaseReferenceMsg: DatabaseReference

    override fun onReceive(context: Context?, intent: Intent?) {

        context!!.startService(Intent(context.applicationContext, WhatsappAccessibilityService::class.java))

        databaseReferenceMsg = FirebaseDatabase.getInstance().getReference("Message")

        sentPendingIntent = PendingIntent.getBroadcast(context, 0, Intent("Message sent"), 0)
        deliveredPendingIntent =
            PendingIntent.getBroadcast(context, 0, Intent("message delivered"), 0)

        messageId = intent?.extras?.getString("WhatsAppId")!!
        messagePersonName = intent.extras?.getString("PersonName")!!
        messagePersonNumber = intent.extras?.getString("PersonNumber")!!
        messageText = intent.extras?.getString("WhatsAppAppMessage")!!
        messageDate = intent.extras?.getString("WhatsAppDate")!!
        messageTime = intent.extras?.getString("WhatsAppTime")!!
        messageStatus = intent.extras?.getString("WhatsAppStatus")!!
        messageSendVia = intent.extras?.getString("WhatsAppSendVia")!!
        userID = intent.extras?.getString("UserID")!!
        messageDelivered = intent.extras?.getString("WhatsAppDelivered")!!
        messageCalender = intent.extras!!.getLong("calendar")


        WhatsappAccessibilityService.sActive = true
        WhatsappAccessibilityService.sPhone = messagePersonNumber
        WhatsappAccessibilityService.sContact = messagePersonName
        WhatsappAccessibilityService.sMsg = messageText
        val intent =  Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + messagePersonNumber.replace("+","") + "&text=" + WhatsappAccessibilityService.sMsg))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)

        smsSentReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, arg1: Intent) {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        updateMsg("Completed" ,"Sent")
                        showNotification(context, "message sent")
                    }
                    SmsManager.RESULT_ERROR_GENERIC_FAILURE -> {
                        updateMsg("GENERIC FAILURE" , "GENERIC FAILURE" )
                        showNotification(context, " GENERIC FAILURE")
                    }
                    SmsManager.RESULT_ERROR_NO_SERVICE -> {
                        updateMsg("NO SERVICE" , "NO SERVICE")
                        showNotification(context, " NO SERVICE")
                    }
                    SmsManager.RESULT_ERROR_NULL_PDU -> {
                        updateMsg("NULL PDU" , "NO SERVICE")
                        showNotification(context, " NULL PDU")
                    }
                    SmsManager.RESULT_ERROR_RADIO_OFF -> {
                        updateMsg("RADIO OFF" , "RADIO OFF")
                        showNotification(context, " RADIO OFF")
                    }
                }
            }
        }

        smsDeliveredReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, arg1: Intent) {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        Toast.makeText(context, "message delivered", Toast.LENGTH_SHORT).show()
                        updateMsg("Completed" , "Delivered" )
                        showNotification(context, "message delivered")
                    }
                    Activity.RESULT_CANCELED -> {
                        Toast.makeText(context, "message not delivered", Toast.LENGTH_SHORT).show()
                        updateMsg("Not","Not Delivered")
                        showNotification(context, "message not delivered")
                    }
                }
            }
        }

        context?.applicationContext?.registerReceiver(smsSentReceiver, IntentFilter("message sent"))
        context?.applicationContext?.registerReceiver(
            smsDeliveredReceiver,
            IntentFilter("message delivered")
        )

    }

    private fun updateMsg(messageStatus: String , messageDelivered :String) {

        val message = MessageFirebase(
            messageId, messagePersonName, messagePersonNumber, messageText, messageDate, messageTime,
            messageStatus, messageSendVia, userID, messageCalender , messageDelivered
        )
        // update msg on fireBase
        databaseReferenceMsg.child(messageId).setValue(message)
    }

    private fun showNotification(context: Context?, msg: String) {
        val notificationHelper = NotificationHelper(
            context!!,messageId,messagePersonName,messagePersonNumber,messageText,messageDate,messageTime,messageStatus,messageSendVia,userID, msg )
        val nb = notificationHelper.getChannelNotification()
        notificationHelper.getManager().notify(messageId.hashCode(), nb.build())
    }



}