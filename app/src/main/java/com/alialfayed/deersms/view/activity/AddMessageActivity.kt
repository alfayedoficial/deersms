package com.alialfayed.deersms.view.activity

import android.app.*
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.alialfayed.deersms.R
import com.alialfayed.deersms.utils.SMSReceiver
import com.alialfayed.deersms.viewmodel.AddMessageViewModel
import com.alialfayed.deersms.viewmodel.ScheduleMessageViewModel
import kotlinx.android.synthetic.main.activity_add_message.*
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AddMessageActivity : AppCompatActivity() {

    private lateinit var addMessageViewModel: AddMessageViewModel
    private lateinit var scheduleMessageViewModel: ScheduleMessageViewModel

    private val requestSendSMS = 2
    private val requestReadState = 2

    private var calender: DatePickerDialog.OnDateSetListener? = null
    private var timePickerDialog: TimePickerDialog? = null

    private var date: Date? = null
    private var myDateCheck: Date? = null
    private var mHour = 0
    private var mMin = 0
    private var hours1 = 0
    private var min1 = 0
    private var year1 = 0
    private var month1 = 0
    private var dayOfMonth1 = 0

    private lateinit var calendarAlarm: Calendar
    private lateinit var currentDate: String
    private lateinit var currentTime: String


    private var isSendNow: Boolean = true
    private var sendVia: String = "SMS"
    private var checkerButtonSend: Boolean = true
    private lateinit var radioGroup_SendVia: RadioGroup
    private lateinit var checkerButtonTime: RadioGroup
    private val REQ_CODE = 100


//    private lateinit var saveResultMessage:String
//    private lateinit var saveResultPhone:String
//    var contactList = ArrayList<Contacts>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_message)

        addMessageViewModel = ViewModelProviders.of(this).get(AddMessageViewModel::class.java)
        addMessageViewModel.setAddMessage(this)

        scheduleMessageViewModel =
            ViewModelProviders.of(this, ScheduleMessageViewModelFactory(this@AddMessageActivity))
                .get(ScheduleMessageViewModel::class.java)


        radioGroup_SendVia = checkerButtonVia_AddMessage
        radioGroup_SendVia.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.radioBtnWhatsApp_AddMessage -> {
                    checkerButtonSend = false
                    checkerButtonTime_AddMessage.setVisibility(View.GONE)
                    textView8.setVisibility(View.GONE)
                }
                R.id.radioBtnSMS_AddMessage -> {
                    checkerButtonSend = true
                    checkerButtonTime_AddMessage.setVisibility(View.VISIBLE)
                    textView8.setVisibility(View.VISIBLE)
                }
            }
        }
        checkerButtonTime = checkerButtonTime_AddMessage
        checkerButtonTime.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.radioBtnNow_AddMessage -> {
                    isSendNow = true
                }
                R.id.radioBtnCustom_AddMessage -> {
                    isSendNow = false
                }
            }
        }


        /**
         * Speaker
         */
        imageBtnSpeaker_AddMessage.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please Speak Now")
            try {
                startActivityForResult(intent, REQ_CODE)
            } catch (a: ActivityNotFoundException) {
                Toast.makeText(
                    applicationContext,
                    "Sorry your device not supported",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }




        timesender()
        calender = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            var month = month
            month += 1

            year1 = year
            month1 = month
            dayOfMonth1 = dayOfMonth

            val startDate = "$dayOfMonth/$month/$year"
            val time = SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().time)
            val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            try {
                date = format.parse(time)
                myDateCheck = format.parse(startDate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            when {
                myDateCheck == null -> try {
                    myDateCheck = format.parse(time)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                myDateCheck!!.before(date) -> Toast.makeText(
                    this,
                    "Please, Enter a valid Date!", Toast.LENGTH_LONG
                ).show()
                else -> {
                    txtDate_AddMessage.setText(startDate)

                }
            }
        }

        if (intent.getStringExtra("phoneContact") != null || intent.getStringExtra("nameContact") != null) {
            var textName: String = intent.getStringExtra("nameContact")
            edtname_AddMessage.setText(textName)
            var textPhone: String = intent.getStringExtra("phoneContact")
            edtNumber_AddMessage.setText(textPhone)
            if (intent.getStringExtra("Template") == null) {
                var textTemplate: String = getText(R.string.type_your_message).toString()
                edtMessage_AddMessage.setHint(textTemplate)
            } else {
                var textTemplate: String = intent.getStringExtra("Template")
                edtMessage_AddMessage.setText(textTemplate)
            }
        }
        if (intent.getStringExtra("Template") != null) {
            var textName: String = intent.getStringExtra("Template")
            edtMessage_AddMessage.setText(textName)
            if (intent.getStringExtra("TPhone") != null && intent.getStringExtra("TName") != null) {
                var textName: String = intent.getStringExtra("TName")
                edtname_AddMessage.setText(textName)
                var textPhone: String = intent.getStringExtra("TPhone")
                edtNumber_AddMessage.setText(textPhone)
            } else if (intent.getStringExtra("TPhone") != null){
                var textPhone: String = intent.getStringExtra("TPhone")
                edtNumber_AddMessage.setText(textPhone)
            }else if ( intent.getStringExtra("TName") != null){
                var textName: String = intent.getStringExtra("TName")
                edtname_AddMessage.setText(textName)
            }else {
                var textName: String = getText(R.string.name).toString()
                edtname_AddMessage.setHint(textName)
                var textPhone: String = getText(R.string.choose_number).toString()
                edtNumber_AddMessage.setHint(textPhone)
            }
        }


        /**
         * button do send Message
         */
        imageBtnSend_AddMessage.setOnClickListener {

            val numberText = edtNumber_AddMessage.text.toString().trim()
            val messageText = edtMessage_AddMessage.text.toString().trim()

            if (checkPermission(android.Manifest.permission.SEND_SMS)
                && checkPermission(android.Manifest.permission.READ_CONTACTS)
                && checkPermission(android.Manifest.permission.READ_PHONE_STATE)
            ) {
                if (!checkerButtonSend) {
//                  if checkerButtonSend = false
                    sendVia = "WhatsApp"
                    calendarAlarm = Calendar.getInstance()
                    currentDate = DateFormat.getDateInstance().format(calendarAlarm.time)
                    currentTime = DateFormat.getTimeInstance().format(calendarAlarm.time)
//                        Toast.makeText(this,"Whatsapp Activity",Toast.LENGTH_SHORT).show()
                    addMessageViewModel.sendWhatsapp(
                        edtname_AddMessage.text.toString(),
                        edtNumber_AddMessage.text.toString(),
                        edtMessage_AddMessage.text.toString(),
                        currentDate,
                        currentTime,
                        "Completed",
                        sendVia,
                        "Sent",
                        calendarAlarm.timeInMillis
                    )
                } else {
//                if checkerButtonSend = true
                    if (isSendNow) {
//                      if isSendNow = true
                        sendVia = "SMS"
                        calendarAlarm = Calendar.getInstance()
                        currentDate = DateFormat.getDateInstance().format(calendarAlarm.time)
                        currentTime = DateFormat.getTimeInstance().format(calendarAlarm.time)
                        addNowMessageActivity(currentDate, currentTime, sendVia)
                        Log.i("Date now", currentDate + currentTime)
                        isSendNow = false
                        finishSend()
                    } else {
//                     if isSendNow = false
                        sendVia = "SMS"
                        addScheduleMessageActivity(sendVia)
                        isSendNow = true
                        finishSend()
                    }
                    checkerButtonSend = false
                }

            }

        }
        /**
         * button get contacts
         */
        imageBtnContacts_AddMessage.setOnClickListener {
            addMessageViewModel.contacts()
        }
        /**
         * button get template Message
         */
        imageBtnAttach_AddMessage.setOnClickListener {
            addMessageViewModel.template()
        }
        /**
         * editText Reset Activity
         */
        imageBtnReset.setOnClickListener {
            addMessageViewModel.resetActivity()
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        val numberText = edtNumber_AddMessage.text.toString().trim()
        val messageText = edtMessage_AddMessage.text.toString().trim()
        val date = txtDate_AddMessage.text.toString().trim()
        val time = txtTime_AddMessage.text.toString().trim()
        if (requestCode == requestSendSMS && requestCode == requestReadState) {
//            addMessageViewModel.sendSMS(numberText, messageText)
            addScheduleMessageActivity(sendVia)
            addNowMessageActivity(date, time, sendVia)

        }
    }

    private fun checkPermission(permission: String): Boolean {
        val check: Int = ContextCompat.checkSelfPermission(this, permission)
        return (check == PackageManager.PERMISSION_GRANTED)
    }

    override fun onBackPressed() {
        finish()
    }


    /**
     * Check Radio Button time
     */
    private fun timesender() {
        radioBtnNow_AddMessage.setOnClickListener {
            Toast.makeText(this, "Message Send Now", Toast.LENGTH_SHORT).show()
        }
        radioBtnCustom_AddMessage.setOnClickListener {
            selectTime()
            selectDate()
            checkerButtonTime_AddMessage.setVisibility(View.GONE)
            linearLayout_AddMessage.setVisibility(View.VISIBLE)
            isSendNow = false
            Toast.makeText(this, "Message Send Custom Time", Toast.LENGTH_SHORT).show()
        }
    }

    private fun selectDate() {

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val dialog = DatePickerDialog(
            this,
            calender, year, month, day
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.show()


    }

    private fun selectTime() {
        val c = Calendar.getInstance()
        mHour = c.get(Calendar.HOUR_OF_DAY)
        mMin = c.get(Calendar.MINUTE)

        timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minutes ->
                val myCalInstance = Calendar.getInstance()
                val myRealCalender = Calendar.getInstance()

                if (myDateCheck == null) {
                    val timeStamp =
                        SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().time)
                    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    try {
                        myDateCheck = format.parse(timeStamp)
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }
                }

                myRealCalender.time = myDateCheck
                myRealCalender.set(Calendar.HOUR_OF_DAY, hourOfDay)
                myRealCalender.set(Calendar.MINUTE, minutes)

                if (myRealCalender.time.before(myCalInstance.time)) {
                    Toast.makeText(
                        this,
                        "Please, Enter a valid Time!", Toast.LENGTH_LONG
                    ).show()
                } else {
                    hours1 = hourOfDay
                    min1 = minutes
                    calendarAlarm = Calendar.getInstance()
                    calendarAlarm.set(Calendar.YEAR, year1)
                    calendarAlarm.set(Calendar.MONTH, month1 - 1)
                    calendarAlarm.set(Calendar.DAY_OF_MONTH, dayOfMonth1)
                    calendarAlarm.set(Calendar.HOUR_OF_DAY, hours1)
                    calendarAlarm.set(Calendar.MINUTE, min1)
                    calendarAlarm.set(Calendar.SECOND, 0)
                    if (hourOfDay < 10 && minutes >= 10) {
                        txtTime_AddMessage.setText("0$hourOfDay:$minutes")
                    } else if (hourOfDay < 10 && minutes < 10) {
                        txtTime_AddMessage.setText("0$hourOfDay:0$minutes")
                    } else if (hourOfDay >= 10 && minutes < 10) {
                        txtTime_AddMessage.setText("$hourOfDay:0$minutes")
                    } else if (hourOfDay >= 10 && minutes >= 10) {
                        txtTime_AddMessage.setText("$hourOfDay:$minutes")
                    }
                }
            }, mHour, mMin, false
        )
        timePickerDialog!!.show()
    }


    fun addScheduleMessageActivity(sendVia: String) {
        val phoneText = edtNumber_AddMessage.text.toString().trim()
        val messageText = edtMessage_AddMessage.text.toString().trim()
        val nameText = edtname_AddMessage.text.toString().trim()
        val date = txtDate_AddMessage.text.toString().trim()
        val time = txtTime_AddMessage.text.toString().trim()


        if (!phoneText.isEmpty()) {
            edtNumber_AddMessage.error = "Phone Number mustn't be empty"
            edtNumber_AddMessage.requestFocus()
            return
        } else if (!messageText.isEmpty()) {
            edtMessage_AddMessage.error = "Message mustn't be empty!"
            edtMessage_AddMessage.requestFocus()
            return
        } else if (!txtDate_AddMessage.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter a valid Date!", Toast.LENGTH_SHORT).show()
            return
        } else if (!txtTime_AddMessage.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter a valid Time!", Toast.LENGTH_SHORT).show()
            return
        } else {

            addMessageViewModel.scheduleMessageViewModel(
                edtname_AddMessage.text.toString(),
                edtNumber_AddMessage.text.toString(),
                edtMessage_AddMessage.text.toString(),
                txtDate_AddMessage.text.toString(),
                txtTime_AddMessage.text.toString(),
                "Pending",
                sendVia,
                calendarAlarm.timeInMillis, "Sent"
            )
            Toast.makeText(this, "SMS Send at " + date + time, Toast.LENGTH_LONG).show()

        }

    }

    fun addNowMessageActivity(date: String, time: String, sendVia: String) {
        val phoneText = edtNumber_AddMessage.text.toString().trim()
        val messageText = edtMessage_AddMessage.text.toString().trim()

        if (phoneText.isEmpty() || messageText.isBlank()) {
            edtNumber_AddMessage.error = "Phone Number mustn't be empty"
            edtNumber_AddMessage.requestFocus()
            return
        } else if (messageText.isEmpty() || messageText.isBlank()) {
            edtMessage_AddMessage.error = "Message mustn't be empty!"
            edtMessage_AddMessage.requestFocus()
            return
        } else {
            Toast.makeText(
                this,
                "SMS Send at " + currentDate + currentTime + "By SMS ",
                Toast.LENGTH_LONG
            )
                .show()

            addMessageViewModel.scheduleMessageViewModel(
                edtname_AddMessage.text.toString(),
                edtNumber_AddMessage.text.toString(), edtMessage_AddMessage.text.toString(),
                date, time, "Completed", sendVia,
                calendarAlarm.timeInMillis, "Sent"
            )
        }

    }


    fun setSMSAlarm(
        smsId: String, receiverName: String, receiverNumber: String, SMSMessage: String,
        date: String, time: String, status: String, type: String, currentUser: String,
        calendar: Long, smsDelivered: String
    ) {

        val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(this@AddMessageActivity, SMSReceiver::class.java)
        intent.putExtra("SmsId", smsId)
        intent.putExtra("SmsReceiverName", receiverName)
        intent.putExtra("SmsReceiverNumber", receiverNumber)
        intent.putExtra("SmsMsg", SMSMessage)
        intent.putExtra("SmsDate", date)
        intent.putExtra("SmsTime", time)
        intent.putExtra("SmsStatus", status)
        intent.putExtra("SmsType", type)
        intent.putExtra("UserID", currentUser)
        intent.putExtra("calendar", calendar)
        intent.putExtra("SmsDelivered", smsDelivered)

        val pendingIntent: PendingIntent =
            PendingIntent.getBroadcast(this@AddMessageActivity, smsId.hashCode(), intent, 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendarAlarm.timeInMillis,
                pendingIntent
            )
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendarAlarm.timeInMillis, pendingIntent)
        }

        Toast.makeText(
            this@AddMessageActivity,
            "Message scheduled successfully!",
            Toast.LENGTH_SHORT
        ).show()
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_CODE -> {
                if (resultCode == Activity.RESULT_OK && null != data) {
                    //if (resultCode == RESULT_OK && null ! = data) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    //edttxt.setText(result.get(0));
                    if (result != null && !result.isEmpty()) {
                        edtMessage_AddMessage.setText(result[0])
                    }
                }
            }
        }
    }

    fun finishSend(){
        val phone = edtNumber_AddMessage.text.toString().trim()
        val message = edtMessage_AddMessage.text.toString().trim()
        val name = edtname_AddMessage.text.toString().trim()
        val date = txtDate_AddMessage.text.toString().trim()
        val time = txtTime_AddMessage.text.toString().trim()
        if (phone.isEmpty() || message.isEmpty() || name.isEmpty()) {
            Toast.makeText(this, "Please Check Your Fields ", Toast.LENGTH_SHORT).show()
        }else{
            startActivity(Intent(this,HomeActivity::class.java))
        }
    }

    inner class ScheduleMessageViewModelFactory(private val scheduleMessageActivity: AddMessageActivity) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ScheduleMessageViewModel(scheduleMessageActivity) as T
        }
    }


}
