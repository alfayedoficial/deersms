package com.alialfayed.deersms.view.activity

import android.app.*
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.speech.RecognizerIntent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.alialfayed.deersms.utils.SMSReceiver
import com.alialfayed.deersms.utils.WhatsAppReceiver
import com.alialfayed.deersms.viewmodel.CurrentSIMViewModel
import kotlinx.android.synthetic.main.activity_current_sim.*
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class CurrentSIMActivity : AppCompatActivity() {
    private lateinit var currentSIMViewModel: CurrentSIMViewModel

    private lateinit var calendarAlarm: Calendar
    private lateinit var currentDate: String
    private lateinit var currentTime: String

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

    var groupChecker: Boolean = false
    var isSendNow: Boolean = true

    private val REQ_CODE = 100

    private val requestSendSMS = 2
    private val requestReadState = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.alialfayed.deersms.R.layout.activity_current_sim)
        currentSIMViewModel = ViewModelProviders.of(this,MyViewModelFactory(this)).get(CurrentSIMViewModel::class.java)
        currentSIMViewModel.setInitComponentCurrentSIMActivity()

        calender()
        timesender()
        calendarAlarm = Calendar.getInstance()
        currentDate = DateFormat.getDateInstance().format(calendarAlarm.time)
        currentTime = DateFormat.getTimeInstance().format(calendarAlarm.time)

        imageBtnSend_CurrentSIM.setOnClickListener {
            if (currentSIMViewModel.checkPermission(android.Manifest.permission.READ_CONTACTS)
                && currentSIMViewModel.checkPermission(android.Manifest.permission.READ_PHONE_STATE)
                && currentSIMViewModel.checkPermission(android.Manifest.permission.READ_PHONE_STATE)
            ) {
                if (isSendNow){
                    currentSIMViewModel.addNowCurrentSIM(currentDate,currentTime)
                    currentSIMViewModel.finishNowSend()
                    Toast.makeText(this, "Done WhatsApp Now ", Toast.LENGTH_SHORT).show()

                }else{
                    currentSIMViewModel.addScheduleCurrentSIM()
                    currentSIMViewModel.finishScheduleSend()
                    Toast.makeText(this, "Done WhatsApp Schedule ", Toast.LENGTH_SHORT).show()

                }
            } else {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }
        }
        /**
         * editText Reset Activity
         */
        imageBtnReset_CurrentSIM.setOnClickListener {
            currentSIMViewModel.resetActivity()
        }
        /**
         * button check group or contacts
         */
        imageBtnChecker_CurrentSIM.setOnClickListener {
            currentSIMViewModel.checker()
        }
        /**
         * Speaker
         */
        imageBtnSpeaker_CurrentSIM.setOnClickListener {
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
        /**
         * button get template Message
         */
        imageBtnAttach_CurrentSIM.setOnClickListener {
            currentSIMViewModel.template()
        }
        /**
         * button get contacts
         */
        imageBtnContacts_CurrentSIM.setOnClickListener {
            currentSIMViewModel.getContacts()
            currentSIMViewModel.checker()
            edtNumber_CurrentSIM.setVisibility(View.VISIBLE)
            edtNumber_CurrentSIM.setText("")
            edtname_CurrentSIM.setText("")
            edtname_CurrentSIM.setEnabled(true)
            edtGroup_CurrentSIM.setVisibility(View.GONE)
            groupChecker = false

        }
        /**
         * button get Groups
         */
        imageBtnGroups_CurrentSIM.setOnClickListener {
            currentSIMViewModel.getContacts()
            currentSIMViewModel.checker()
            edtNumber_CurrentSIM.setVisibility(View.GONE)
            edtGroup_CurrentSIM.setVisibility(View.VISIBLE)
            edtGroup_CurrentSIM.setText("")
            edtname_CurrentSIM.setText("")


        }
    }

    fun setSMSAlarm(
        smsId: String, receiverName: String, receiverNumber: String, SMSMessage: String,
        date: String, time: String, status: String, type: String, currentUser: String,
        calendar: Long, smsDelivered: String
    ) {

        val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(this@CurrentSIMActivity, SMSReceiver::class.java)
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
            PendingIntent.getBroadcast(this@CurrentSIMActivity, smsId.hashCode(), intent, 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendarAlarm.timeInMillis,
                pendingIntent
            )
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendarAlarm.timeInMillis, pendingIntent)
        }

    }


    /**
     * Check Radio Button time
     */
    private fun timesender() {
        radioBtnNow_CurrentSIM.setOnClickListener {
            Toast.makeText(this, "Message Send Now", Toast.LENGTH_SHORT).show()
            isSendNow = true
        }
        radioBtnCustom_CurrentSIM.setOnClickListener {
            selectTime()
            selectDate()
            checkerButtonTime_CurrentSIM.setVisibility(View.GONE)
            linearLayout_CurrentSIM.setVisibility(View.VISIBLE)
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
                        txtTime_CurrentSIM.setText("0$hourOfDay:$minutes")
                    } else if (hourOfDay < 10 && minutes < 10) {
                        txtTime_CurrentSIM.setText("0$hourOfDay:0$minutes")
                    } else if (hourOfDay >= 10 && minutes < 10) {
                        txtTime_CurrentSIM.setText("$hourOfDay:0$minutes")
                    } else if (hourOfDay >= 10 && minutes >= 10) {
                        txtTime_CurrentSIM.setText("$hourOfDay:$minutes")
                    }
                }
            }, mHour, mMin, false
        )
        timePickerDialog!!.show()
    }

    fun calender() {
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
                    txtDate_CurrentSIM.setText(startDate)

                }
            }
        }
    }

    override fun onBackPressed() {
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
                        edtMessage_CurrentSIM.setText(result[0])
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        val numberText = edtNumber_CurrentSIM.text.toString().trim()
        val messageText = edtMessage_CurrentSIM.text.toString().trim()
        val date = txtDate_CurrentSIM.text.toString().trim()
        val time = txtTime_CurrentSIM.text.toString().trim()
        if (requestCode == requestSendSMS && requestCode == requestReadState) {
//            addScheduleMessageActivity(sendVia)
//            addNowMessageActivity(date, time, sendVia)
        } else {
            Toast.makeText(
                this,
                "Sorry , Please Accept Permissions To Send Message",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    internal class MyViewModelFactory(val currentSIMActivity: CurrentSIMActivity):ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CurrentSIMViewModel(currentSIMActivity) as T
        }

    }
}
