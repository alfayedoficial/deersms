package com.alialfayed.deersms.utils

import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.telephony.SmsManager
import android.widget.Toast
import com.alialfayed.deersms.model.MessageFirebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * Class do :
 * Created by ( Eng Ali)
 */
class SMSReceiver : BroadcastReceiver() {

    private lateinit var sentPendingIntent: PendingIntent
    private lateinit var deliveredPendingIntent: PendingIntent
    private lateinit var smsSentReceiver: BroadcastReceiver
    private lateinit var smsDeliveredReceiver: BroadcastReceiver

    private lateinit var smsId: String
    private lateinit var smsReceiverName: String
    private lateinit var smsReceiverNumber: String
    private lateinit var smsMsg: String
    private lateinit var smsDate: String
    private lateinit var smsTime: String
    private lateinit var smsStatus: String
    private lateinit var smsType: String
    private lateinit var userID: String
    private var calendar: Long = 0
    private lateinit var smsDelivered :String

    lateinit var databaseReferenceMsg: DatabaseReference

    override fun onReceive(context: Context?, intent: Intent?) {

        databaseReferenceMsg = FirebaseDatabase.getInstance().getReference("Message")
        sentPendingIntent = PendingIntent.getBroadcast(context, 0, Intent("Message sent"), 0)
        deliveredPendingIntent = PendingIntent.getBroadcast(context, 0, Intent("message delivered"), 0)

        smsId = intent?.extras?.getString("SmsId")!!
        smsReceiverName = intent.extras?.getString("SmsReceiverName")!!
        smsReceiverNumber = intent.extras?.getString("SmsReceiverNumber")!!
        smsMsg = intent.extras?.getString("SmsMsg")!!
        smsDate = intent.extras?.getString("SmsDate")!!
        smsTime = intent.extras?.getString("SmsTime")!!
        smsStatus = intent.extras?.getString("SmsStatus")!!
        smsType = intent.extras?.getString("SmsType")!!
        userID = intent.extras?.getString("UserID")!!
        smsDelivered = intent.extras?.getString("SmsDelivered")!!
        calendar = intent.extras!!.getLong("calendar")

        val smsManager: SmsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(smsReceiverNumber, null, smsMsg, sentPendingIntent, deliveredPendingIntent)

        smsSentReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, arg1: Intent) {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        // update msg on fireBase
                        updateMsg("Completed" ,"Sent")
                        // show notification
                        showNotification(context, "Message sent")
                    }
                    SmsManager.RESULT_ERROR_GENERIC_FAILURE -> {
                        // update msg on fireBase
                        updateMsg("GENERIC FAILURE" , "GENERIC FAILURE" )
                        // show notification
                        showNotification(context, " GENERIC FAILURE")
                    }
                    SmsManager.RESULT_ERROR_NO_SERVICE -> {
                        // update msg on fireBase
                        updateMsg("NO SERVICE" , "NO SERVICE")
                        // show notification
                        showNotification(context, " NO SERVICE")
                    }
                    SmsManager.RESULT_ERROR_NULL_PDU -> {
                        // update msg on fireBase
                        updateMsg("NULL PDU" , "NO SERVICE")
                        // show notification
                        showNotification(context, " NULL PDU")
                    }
                    SmsManager.RESULT_ERROR_RADIO_OFF -> {
                        // update msg on fireBase
                        updateMsg("RADIO OFF" , "RADIO OFF")
                        // show notification
                        showNotification(context, " RADIO OFF")
                    }
                }
            }
        }

        smsDeliveredReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, arg1: Intent) {
                when (resultCode){
                    Activity.RESULT_OK -> {
                        // update msg on fireBase
                        updateMsg("Completed" , "Delivered" )
                        // show notification
                        showNotification(context, "Message Delivered")
                    }
                    Activity.RESULT_CANCELED -> {
                        // update msg on fireBase
                        updateMsg("Not","Not Delivered")
                        // show notification
                        showNotification(context, "Message not delivered")
                    }
                }
            }
        }

        context?.applicationContext?.registerReceiver(smsSentReceiver, IntentFilter("Message sent"))
        context?.applicationContext?.registerReceiver(smsDeliveredReceiver, IntentFilter("message delivered"))

    }

    private fun updateMsg(smsStatus: String, smsDelivered :String){

        val message = MessageFirebase(smsId, smsReceiverName, smsReceiverNumber, smsMsg, smsDate, smsTime,
            smsStatus, smsType, userID, calendar , smsDelivered)
        // update msg on fireBase
        databaseReferenceMsg.child(smsId).setValue(message)
    }

    private fun showNotification(context: Context?, msg: String) {
        val notificationHelper = NotificationHelper(context!!, smsId, smsReceiverName, smsReceiverNumber, smsMsg, smsDate, smsTime, smsStatus, smsType, userID, msg)
        val nb = notificationHelper.getChannelNotification()
        notificationHelper.getManager().notify(smsId.hashCode(), nb.build())
    }



}
