package com.alialfayed.deersms.viewmodel

import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.alialfayed.deersms.R
import com.alialfayed.deersms.repo.FirebaseHandler
import com.alialfayed.deersms.view.activity.*
import kotlinx.android.synthetic.main.activity_current_sim.*
import kotlinx.android.synthetic.main.activity_whats_app.*
import java.util.*

/**
 * Class do :
 * Created by ( Eng Ali)
 */
class CurrentSIMViewModel(val currentSIMActivity: CurrentSIMActivity):ViewModel() {
    private  var firebaseHandler: FirebaseHandler = FirebaseHandler(currentSIMActivity,this)
    private lateinit var calendarAlarm: Calendar
    var checkerContactorGroups: Boolean = true
    private val resultTemplate = 0
    private lateinit var checkerButtonTime: RadioGroup
    var isSendNow: Boolean = true

    fun viewModeSendMessage(
        personName: String, personNumber: String, SMSMessage: String,
        SMSDate: String, SMSTime: String, SMSStatus: String, SMSSendVia: String,
        SMSDelivered: String, calendar: Long
    ) {
        firebaseHandler.scheduleMessageRepository(
            personName,
            personNumber,
            SMSMessage,
            SMSDate,
            SMSTime,
            SMSStatus,
            SMSSendVia,
            SMSDelivered,
            calendar
        )
    }

    fun addNowCurrentSIM(date: String, time: String) {
        val phoneText = currentSIMActivity.edtNumber_CurrentSIM.text.toString().trim()
        val messageText = currentSIMActivity.edtMessage_CurrentSIM.text.toString().trim()
        val nameText = currentSIMActivity.edtname_CurrentSIM.text.toString().trim()
        calendarAlarm = Calendar.getInstance()


        if (phoneText.isEmpty()) {
            currentSIMActivity.edtNumber_CurrentSIM.error = "Phone Number mustn't be empty"
            currentSIMActivity.edtNumber_CurrentSIM.requestFocus()
            return
        } else if (nameText.isEmpty()) {
            currentSIMActivity.edtname_CurrentSIM.error = "Name mustn't be empty!"
            currentSIMActivity.edtname_CurrentSIM.requestFocus()
            return
        } else if (messageText.isEmpty()) {
            currentSIMActivity.edtMessage_CurrentSIM.error = "Message mustn't be empty!"
            currentSIMActivity.edtMessage_CurrentSIM.requestFocus()
            return
        } else {
            viewModeSendMessage(
                nameText,
                phoneText,
                messageText,
                date,
                time,
                "Completed",
                "SMS",
                "Sent",
                calendarAlarm.timeInMillis
            )
        }

    }

    fun addScheduleCurrentSIM() {
        val phoneText = currentSIMActivity.edtNumber_CurrentSIM.text.toString().trim()
        val messageText = currentSIMActivity.edtMessage_CurrentSIM.text.toString().trim()
        val nameText = currentSIMActivity.edtname_CurrentSIM.text.toString().trim()
        val date = currentSIMActivity.txtDate_CurrentSIM.text.toString().trim()
        val time = currentSIMActivity.txtTime_CurrentSIM.text.toString().trim()
        calendarAlarm = Calendar.getInstance()


        if (phoneText.isEmpty()) {
            currentSIMActivity.edtNumber_CurrentSIM.error = "Phone Number mustn't be empty"
            currentSIMActivity.edtNumber_CurrentSIM.requestFocus()
            return
        } else if (nameText.isEmpty()) {
            currentSIMActivity.edtname_CurrentSIM.error = "Name mustn't be empty!"
            currentSIMActivity.edtname_CurrentSIM.requestFocus()
            return
        } else if (messageText.isEmpty()) {
            currentSIMActivity.edtMessage_CurrentSIM.error = "Message mustn't be empty!"
            currentSIMActivity.edtMessage_CurrentSIM.requestFocus()
            return
        } else if (date.isEmpty()) {
            Toast.makeText(currentSIMActivity, "Enter a valid Date!", Toast.LENGTH_SHORT).show()
            return
        } else if (time.isEmpty()) {
            Toast.makeText(currentSIMActivity, "Enter a valid Time!", Toast.LENGTH_SHORT).show()
            return
        } else {
            viewModeSendMessage(
                nameText,
                phoneText,
                messageText,
                date,
                time,
                "Pending",
                "SMS",
                "Pending Sent",
                calendarAlarm.timeInMillis
            )
        }

    }

    fun setSMSAlarm(
        SMSId: String,
        personName: String,
        personNumber: String,
        SMSMessage: String,
        SMSDate: String,
        SMSTime: String,
        SMSStatus: String,
        SMSSendVia: String,
        currentUser: String,
        calendar: Long,
        SMSDelivered: String
    ) {
        currentSIMActivity.setSMSAlarm(
            SMSId,
            personName,
            personNumber,
            SMSMessage,
            SMSDate,
            SMSTime,
            SMSStatus,
            SMSSendVia,
            currentUser,
            calendar,
            SMSDelivered
        )
    }

    fun setInitComponentCurrentSIMActivity() {

        checkerButtonTime = currentSIMActivity.checkerButtonTime_CurrentSIM
        checkerButtonTime.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.radioBtnNow_CurrentSIM -> {
                    isSendNow = true
                }
                R.id.radioBtnCustom_CurrentSIM -> {
                    isSendNow = false
                }
            }
        }

        var phoneGroup = ArrayList<String>()
        if (currentSIMActivity.intent.getStringExtra("phoneContact") != null || currentSIMActivity.intent.getStringExtra(
                "nameContact"
            ) != null
        ) {
            val textName: String = currentSIMActivity.intent.getStringExtra("nameContact")
            currentSIMActivity.edtname_CurrentSIM.setText(textName)
            val textPhone: String = currentSIMActivity.intent.getStringExtra("phoneContact")
            currentSIMActivity.edtNumber_CurrentSIM.setText(textPhone)
            if (currentSIMActivity.intent.getStringExtra("Template") == null) {
                val textTemplate: String =
                    currentSIMActivity.getText(R.string.type_your_message).toString()
                currentSIMActivity.edtMessage_CurrentSIM.setHint(textTemplate)
            } else {
                val textTemplate: String = currentSIMActivity.intent.getStringExtra("Template")
                currentSIMActivity.edtMessage_CurrentSIM.setText(textTemplate)
            }
        }
        if (currentSIMActivity.intent.getStringExtra("Template") != null) {
            val textName: String = currentSIMActivity.intent.getStringExtra("Template")
            currentSIMActivity.edtMessage_CurrentSIM.setText(textName)
            if (currentSIMActivity.intent.getStringExtra("TPhone") != null && currentSIMActivity.intent.getStringExtra(
                    "TName"
                ) != null
            ) {
                val textName: String = currentSIMActivity.intent.getStringExtra("TName")
                currentSIMActivity.edtname_CurrentSIM.setText(textName)
                val textPhone: String = currentSIMActivity.intent.getStringExtra("TPhone")
                currentSIMActivity.edtNumber_CurrentSIM.setText(textPhone)
            } else if (currentSIMActivity.intent.getStringExtra("TPhone") != null) {
                val textPhone: String = currentSIMActivity.intent.getStringExtra("TPhone")
                currentSIMActivity            } else if (currentSIMActivity.intent.getStringExtra("TName") != null) {
                var textName: String = currentSIMActivity.intent.getStringExtra("TName")
                currentSIMActivity.edtname_CurrentSIM.setText(textName)
            } else {
                val textName: String = currentSIMActivity.getText(R.string.name).toString()
                currentSIMActivity.edtname_CurrentSIM.setHint(textName)
                val textPhone: String = currentSIMActivity.getText(R.string.choose_number).toString()
                currentSIMActivity.edtNumber_CurrentSIM.setHint(textPhone)
            }
        }
        if (currentSIMActivity.intent.getStringArrayListExtra("phoneGroup") != null || currentSIMActivity.intent.getStringExtra(
                "nameGroup"
            ) != null
        ) {
            currentSIMActivity.edtNumber_CurrentSIM.setVisibility(View.GONE)
            currentSIMActivity.edtGroup_CurrentSIM.setVisibility(View.VISIBLE)
            phoneGroup = currentSIMActivity.intent.getStringArrayListExtra("phoneGroup")
            val nameGroup = currentSIMActivity.intent.getStringExtra("nameGroup")
            currentSIMActivity.edtname_CurrentSIM.setText(nameGroup)
            currentSIMActivity.edtGroup_CurrentSIM.setText(phoneGroup.toString())
            currentSIMActivity.edtname_CurrentSIM.setEnabled(false)
            currentSIMActivity.groupChecker = true

        }

    }

    fun resetActivity() {
        currentSIMActivity.edtname_CurrentSIM.setText("")
        currentSIMActivity.edtNumber_CurrentSIM.setText("")
        currentSIMActivity.edtMessage_CurrentSIM.setText("")
        currentSIMActivity.txtDate_CurrentSIM.setText("")
        currentSIMActivity.txtTime_CurrentSIM.setText("")
        currentSIMActivity.checkerButtonTime_CurrentSIM.setVisibility(View.VISIBLE)
        currentSIMActivity.linearLayout_CurrentSIM.setVisibility(View.GONE)
        currentSIMActivity.imageBtnContacts_CurrentSIM.setVisibility(View.GONE)
        currentSIMActivity.imageBtnGroups_CurrentSIM.setVisibility(View.GONE)
        currentSIMActivity.edtNumber_CurrentSIM.setVisibility(View.VISIBLE)
        currentSIMActivity.edtGroup_CurrentSIM.setVisibility(View.GONE)
        currentSIMActivity.edtname_CurrentSIM.setEnabled(true)

    }

    fun template() {
        val phone = currentSIMActivity.edtNumber_CurrentSIM.text.toString()
        val name = currentSIMActivity.edtname_CurrentSIM.text.toString()
        val intent = Intent(currentSIMActivity, TemplatesActivity::class.java)
        intent.putExtra("teName", name)
        intent.putExtra("tePhone", phone)
        currentSIMActivity.startActivityForResult(intent, resultTemplate)
    }

    fun checker() {
        if (checkerContactorGroups) {
            currentSIMActivity.imageBtnContacts_CurrentSIM.setVisibility(View.VISIBLE)
            currentSIMActivity.imageBtnGroups_CurrentSIM.setVisibility(View.VISIBLE)
            checkerContactorGroups = false
        } else {
            currentSIMActivity.imageBtnContacts_CurrentSIM.setVisibility(View.GONE)
            currentSIMActivity.imageBtnGroups_CurrentSIM.setVisibility(View.GONE)
            checkerContactorGroups = true
        }
    }

    fun getContacts() {
        val intent = Intent(currentSIMActivity, GroupContactsActivity::class.java)
        intent.putExtra("SMSMode","goBack")
        currentSIMActivity.startActivity(intent)
    }

    fun checkPermission(permission: String): Boolean {
        val check: Int = ContextCompat.checkSelfPermission(currentSIMActivity, permission)
        return (check == PackageManager.PERMISSION_GRANTED)
    }

    fun finishNowSend() {
        val phone = currentSIMActivity.edtNumber_CurrentSIM.text.toString().trim()
        val message = currentSIMActivity.edtMessage_CurrentSIM.text.toString().trim()
        val name = currentSIMActivity.edtname_CurrentSIM.text.toString().trim()
        if (phone.isEmpty() || message.isEmpty() || name.isEmpty()) {
            Toast.makeText(currentSIMActivity, "Please Check Your Fields ", Toast.LENGTH_SHORT).show()
        } else {
            currentSIMActivity.startActivity(Intent(currentSIMActivity, HomeActivity::class.java))
        }
    }

    fun finishScheduleSend() {
        val phone = currentSIMActivity.edtNumber_CurrentSIM.text.toString().trim()
        val message = currentSIMActivity.edtMessage_CurrentSIM.text.toString().trim()
        val name = currentSIMActivity.edtname_CurrentSIM.text.toString().trim()
        val date = currentSIMActivity.txtDate_CurrentSIM.text.toString().trim()
        val time = currentSIMActivity.txtTime_CurrentSIM.text.toString().trim()
        if (phone.isEmpty() || message.isEmpty() || name.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(currentSIMActivity, "Please Check Your Fields ", Toast.LENGTH_SHORT).show()
        } else {
            currentSIMActivity.startActivity(Intent(currentSIMActivity, HomeActivity::class.java))
        }
    }

}