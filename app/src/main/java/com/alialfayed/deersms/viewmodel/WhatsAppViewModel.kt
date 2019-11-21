package com.alialfayed.deersms.viewmodel

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.alialfayed.deersms.R
import com.alialfayed.deersms.repo.FirebaseHandler
import com.alialfayed.deersms.utils.WhatsAppReceiver
import com.alialfayed.deersms.view.activity.*
import kotlinx.android.synthetic.main.activity_add_message.*
import kotlinx.android.synthetic.main.activity_whats_app.*
import java.util.*

/**
 * Class do :
 * Created by ( Eng Ali)
 */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class WhatsAppViewModel (val whatsAppActivity : WhatsAppActivity): ViewModel() {

//    private lateinit var whatsAppActivity: WhatsAppActivity
    private  var firebaseHandler: FirebaseHandler = FirebaseHandler(whatsAppActivity, this)
    private lateinit var calendarAlarm: Calendar
    var checkerContactorGroups: Boolean = true
    private val resultTemplate = 0
    private lateinit var checkerButtonTime: RadioGroup
    var isSendNow: Boolean = true


    fun viewModeSendMessage(
        personName: String, personNumber: String, whatsAppMessage: String,
        whatsAppDate: String, whatsAppTime: String, whatsAppStatus: String, whatsAppSendVia: String,
        whatsAppDelivered: String, calendar: Long
    ) {
        firebaseHandler.scheduleWhatsAppMessageRepository(
            personName,
            personNumber,
            whatsAppMessage,
            whatsAppDate,
            whatsAppTime,
            whatsAppStatus,
            whatsAppSendVia,
            whatsAppDelivered,
            calendar
        )
    }

    fun addNowWhatsAppActivity(date: String, time: String) {
        val phoneText = whatsAppActivity.edtNumber_WhatsAppActivity.text.toString().trim()
        val messageText = whatsAppActivity.edtMessage_WhatsAppActivity.text.toString().trim()
        val nameText = whatsAppActivity.edtname_WhatsAppActivity.text.toString().trim()
        calendarAlarm = Calendar.getInstance()


        if (phoneText.isEmpty()) {
            whatsAppActivity.edtNumber_WhatsAppActivity.error = "Phone Number mustn't be empty"
            whatsAppActivity.edtNumber_WhatsAppActivity.requestFocus()
            return
        } else if (nameText.isEmpty()) {
            whatsAppActivity.edtname_WhatsAppActivity.error = "Name mustn't be empty!"
            whatsAppActivity.edtname_WhatsAppActivity.requestFocus()
            return
        } else if (messageText.isEmpty()) {
            whatsAppActivity.edtMessage_WhatsAppActivity.error = "Message mustn't be empty!"
            whatsAppActivity.edtMessage_WhatsAppActivity.requestFocus()
            return
        } else {
                viewModeSendMessage(
                nameText,
                phoneText,
                messageText,
                date,
                time,
                "Completed",
                "WhatsApp",
                "Sent",
                calendarAlarm.timeInMillis
                )
        }

    }

    fun addScheduleMessageActivity() {
        val phoneText = whatsAppActivity.edtNumber_WhatsAppActivity.text.toString().trim()
        val messageText = whatsAppActivity.edtMessage_WhatsAppActivity.text.toString().trim()
        val nameText = whatsAppActivity.edtname_WhatsAppActivity.text.toString().trim()
        val date = whatsAppActivity.txtDate_WhatsAppActivity.text.toString().trim()
        val time = whatsAppActivity.txtTime_WhatsAppActivity.text.toString().trim()
        calendarAlarm = Calendar.getInstance()


        if (phoneText.isEmpty()) {
            whatsAppActivity.edtNumber_WhatsAppActivity.error = "Phone Number mustn't be empty"
            whatsAppActivity.edtNumber_WhatsAppActivity.requestFocus()
            return
        } else if (nameText.isEmpty()) {
            whatsAppActivity.edtname_WhatsAppActivity.error = "Name mustn't be empty!"
            whatsAppActivity.edtname_WhatsAppActivity.requestFocus()
            return
        } else if (messageText.isEmpty()) {
            whatsAppActivity.edtMessage_WhatsAppActivity.error = "Message mustn't be empty!"
            whatsAppActivity.edtMessage_WhatsAppActivity.requestFocus()
            return
        } else if (date.isEmpty()) {
            Toast.makeText(whatsAppActivity, "Enter a valid Date!", Toast.LENGTH_SHORT).show()
            return
        } else if (time.isEmpty()) {
            Toast.makeText(whatsAppActivity, "Enter a valid Time!", Toast.LENGTH_SHORT).show()
            return
        } else {
            viewModeSendMessage(
                nameText,
                phoneText,
                messageText,
                date,
                time,
                "Pending",
                "WhatsApp",
                "Pending Sent",
                calendarAlarm.timeInMillis
            )
        }

    }

    fun setWhatsAppAlarm(
        whatsAppId: String,
        personName: String,
        personNumber: String,
        whatsAppMessage: String,
        whatsAppDate: String,
        whatsAppTime: String,
        whatsAppStatus: String,
        whatsAppSendVia: String,
        currentUser: String,
        calendar: Long,
        whatsAppDelivered: String
    ) {
        whatsAppActivity.setSMSAlarm(
            whatsAppId,
            personName,
            personNumber,
            whatsAppMessage,
            whatsAppDate,
            whatsAppTime,
            whatsAppStatus,
            whatsAppSendVia,
            currentUser,
            calendar,
            whatsAppDelivered
        )
//        val alarmManager: AlarmManager =
//            whatsAppActivity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(whatsAppActivity, WhatsAppReceiver::class.java)
//        intent.putExtra("WhatsAppId", whatsAppId)
//        intent.putExtra("PersonName", personName)
//        intent.putExtra("PersonNumber", personNumber)
//        intent.putExtra("WhatsAppAppMessage", whatsAppMessage)
//        intent.putExtra("WhatsAppDate", whatsAppDate)
//        intent.putExtra("WhatsAppTime", whatsAppTime)
//        intent.putExtra("WhatsAppStatus", whatsAppStatus)
//        intent.putExtra("WhatsAppSendVia", whatsAppSendVia)
//        intent.putExtra("UserID", currentUser)
//        intent.putExtra("WhatsAppDelivered", whatsAppDelivered)
//        intent.putExtra("calendar", calendar)
//
//
//        val pendingIntent: PendingIntent =
//            PendingIntent.getBroadcast(whatsAppActivity, whatsAppId.hashCode(), intent, 0)
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            alarmManager.setExact(
//                AlarmManager.RTC_WAKEUP,
//                calendarAlarm.timeInMillis,
//                pendingIntent
//            )
//        } else {
//            alarmManager.set(AlarmManager.RTC_WAKEUP, calendarAlarm.timeInMillis, pendingIntent)
//        }
    }

    fun resetActivity() {
        whatsAppActivity.edtname_WhatsAppActivity.setText("")
        whatsAppActivity.edtNumber_WhatsAppActivity.setText("")
        whatsAppActivity.edtMessage_WhatsAppActivity.setText("")
        whatsAppActivity.txtDate_WhatsAppActivity.setText("")
        whatsAppActivity.txtTime_WhatsAppActivity.setText("")
        whatsAppActivity.checkerButtonTime_WhatsAppActivity.setVisibility(View.VISIBLE)
        whatsAppActivity.linearLayout_WhatsAppActivity.setVisibility(View.GONE)
        whatsAppActivity.imageBtnContacts_WhatsAppActivity.setVisibility(View.GONE)
        whatsAppActivity.imageBtnGroups_WhatsAppActivity.setVisibility(View.GONE)
        whatsAppActivity.edtNumber_WhatsAppActivity.setVisibility(View.VISIBLE)
        whatsAppActivity.edtGroup_WhatsAppActivity.setVisibility(View.GONE)
        whatsAppActivity.edtname_WhatsAppActivity.setEnabled(true)

    }

    fun template() {
        val phone = whatsAppActivity.edtNumber_WhatsAppActivity.text.toString()
        val name = whatsAppActivity.edtname_WhatsAppActivity.text.toString()
        val intent = Intent(whatsAppActivity, TemplatesActivity::class.java)
        intent.putExtra("teName", name)
        intent.putExtra("tePhone", phone)
        whatsAppActivity.startActivityForResult(intent, resultTemplate)
    }

    fun checker() {
        if (checkerContactorGroups) {
            whatsAppActivity.imageBtnContacts_WhatsAppActivity.setVisibility(View.VISIBLE)
            whatsAppActivity.imageBtnGroups_WhatsAppActivity.setVisibility(View.VISIBLE)
            checkerContactorGroups = false
        } else {
            whatsAppActivity.imageBtnContacts_WhatsAppActivity.setVisibility(View.GONE)
            whatsAppActivity.imageBtnGroups_WhatsAppActivity.setVisibility(View.GONE)
            checkerContactorGroups = true
        }
    }

    fun getContacts() {
//        val intent = Intent(whatsAppActivity, GroupContactsActivity::class.java)
//        intent.putExtra("whatsAppMode","goBack")
//        whatsAppActivity.startActivity(intent)
        val intent = Intent(whatsAppActivity, ContactsActivity::class.java)
        intent.putExtra("whatsAppMode","goBack")
        whatsAppActivity.startActivity(intent)
    }

    fun setInitComponentWhatsAppActivity() {

        checkerButtonTime = whatsAppActivity.checkerButtonTime_WhatsAppActivity
        checkerButtonTime.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.radioBtnNow_WhatsAppActivity -> {
                    isSendNow = true
                }
                R.id.radioBtnCustom_WhatsAppActivity -> {
                    isSendNow = false
                }
            }
        }

        var phoneGroup = ArrayList<String>()
        if (whatsAppActivity.intent.getStringExtra("phoneContact") != null || whatsAppActivity.intent.getStringExtra(
                "nameContact"
            ) != null
        ) {
            val textName: String = whatsAppActivity.intent.getStringExtra("nameContact")
            whatsAppActivity.edtname_WhatsAppActivity.setText(textName)
            val textPhone: String = whatsAppActivity.intent.getStringExtra("phoneContact")
            whatsAppActivity.edtNumber_WhatsAppActivity.setText(textPhone)
            if (whatsAppActivity.intent.getStringExtra("Template") == null) {
                val textTemplate: String =
                    whatsAppActivity.getText(R.string.type_your_message).toString()
                whatsAppActivity.edtMessage_WhatsAppActivity.setHint(textTemplate)
            } else {
                val textTemplate: String = whatsAppActivity.intent.getStringExtra("Template")
                whatsAppActivity.edtMessage_WhatsAppActivity.setText(textTemplate)
            }
        }
        if (whatsAppActivity.intent.getStringExtra("Template") != null) {
            val textName: String = whatsAppActivity.intent.getStringExtra("Template")
            whatsAppActivity.edtMessage_WhatsAppActivity.setText(textName)
            if (whatsAppActivity.intent.getStringExtra("TPhone") != null && whatsAppActivity.intent.getStringExtra(
                    "TName"
                ) != null
            ) {
                val textName: String = whatsAppActivity.intent.getStringExtra("TName")
                whatsAppActivity.edtname_WhatsAppActivity.setText(textName)
                val textPhone: String = whatsAppActivity.intent.getStringExtra("TPhone")
                whatsAppActivity.edtNumber_WhatsAppActivity.setText(textPhone)
            } else if (whatsAppActivity.intent.getStringExtra("TPhone") != null) {
                val textPhone: String = whatsAppActivity.intent.getStringExtra("TPhone")
                whatsAppActivity.edtNumber_WhatsAppActivity.setText(textPhone)
            } else if (whatsAppActivity.intent.getStringExtra("TName") != null) {
                var textName: String = whatsAppActivity.intent.getStringExtra("TName")
                whatsAppActivity.edtname_WhatsAppActivity.setText(textName)
            } else {
                val textName: String = whatsAppActivity.getText(R.string.name).toString()
                whatsAppActivity.edtname_WhatsAppActivity.setHint(textName)
                val textPhone: String = whatsAppActivity.getText(R.string.choose_number).toString()
                whatsAppActivity.edtNumber_WhatsAppActivity.setHint(textPhone)
            }
        }
        if (whatsAppActivity.intent.getStringArrayListExtra("phoneGroup") != null || whatsAppActivity.intent.getStringExtra(
                "nameGroup"
            ) != null
        ) {
            whatsAppActivity.edtNumber_WhatsAppActivity.setVisibility(View.GONE)
            whatsAppActivity.edtGroup_WhatsAppActivity.setVisibility(View.VISIBLE)
            phoneGroup = whatsAppActivity.intent.getStringArrayListExtra("phoneGroup")
            val nameGroup = whatsAppActivity.intent.getStringExtra("nameGroup")
            whatsAppActivity.edtname_WhatsAppActivity.setText(nameGroup)
            whatsAppActivity.edtGroup_WhatsAppActivity.setText(phoneGroup.toString())
            whatsAppActivity.edtname_WhatsAppActivity.setEnabled(false)
            whatsAppActivity.groupChecker = true

        }

    }

    fun checkPermission(permission: String): Boolean {
        val check: Int = ContextCompat.checkSelfPermission(whatsAppActivity, permission)
        return (check == PackageManager.PERMISSION_GRANTED)
    }

    fun isAccessibilityOn(context: Context, clazz: Class<out AccessibilityService>): Boolean {
        var accessibilityEnabled = 0
        val service = context.packageName + "/" + clazz.canonicalName
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                context.applicationContext.contentResolver,
                Settings.Secure.ACCESSIBILITY_ENABLED
            )
        } catch (ignored: Settings.SettingNotFoundException) {
        }
        val colonSplitter = TextUtils.SimpleStringSplitter(':')
        if (accessibilityEnabled == 1) {
            val settingValue = Settings.Secure.getString(
                context.applicationContext.contentResolver,
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
            )
            if (settingValue != null) {
                colonSplitter.setString(settingValue)
                while (colonSplitter.hasNext()) {
                    val accessibilityService = colonSplitter.next()
                    if (accessibilityService.equals(service, ignoreCase = true)) {
                        return true
                    }
                }
            }
        }
        return false
    }

    fun finishNowSend() {
        val phone = whatsAppActivity.edtNumber_WhatsAppActivity.text.toString().trim()
        val message = whatsAppActivity.edtMessage_WhatsAppActivity.text.toString().trim()
        val name = whatsAppActivity.edtname_WhatsAppActivity.text.toString().trim()
        if (phone.isEmpty() || message.isEmpty() || name.isEmpty()) {
            Toast.makeText(whatsAppActivity, "Please Check Your Fields ", Toast.LENGTH_SHORT).show()
        } else {
            whatsAppActivity.startActivity(Intent(whatsAppActivity, HomeActivity::class.java))
        }
    }

    fun finishScheduleSend() {
        val phone = whatsAppActivity.edtNumber_WhatsAppActivity.text.toString().trim()
        val message = whatsAppActivity.edtMessage_WhatsAppActivity.text.toString().trim()
        val name = whatsAppActivity.edtname_WhatsAppActivity.text.toString().trim()
        val date = whatsAppActivity.txtDate_WhatsAppActivity.text.toString().trim()
        val time = whatsAppActivity.txtTime_WhatsAppActivity.text.toString().trim()
        if (phone.isEmpty() || message.isEmpty() || name.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(whatsAppActivity, "Please Check Your Fields ", Toast.LENGTH_SHORT).show()
        } else {
            whatsAppActivity.startActivity(Intent(whatsAppActivity, HomeActivity::class.java))
        }
    }
}