package com.alialfayed.deersms.view.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.alialfayed.deersms.R
import com.alialfayed.deersms.utils.SMSReceiver
import com.alialfayed.deersms.viewmodel.ScheduleMessageViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_schedule_message.*
import kotlinx.android.synthetic.main.app_bar.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class ScheduleMessageActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_SEND_SMS: Int = 1
    private val PERMISSION_REQUEST_READ_CONTACTS = 2
    private val REQUEST_READ_PHONE_STATE: Int = 3

    private var calender: DatePickerDialog.OnDateSetListener? = null
    private var timePickerDialog: TimePickerDialog? = null

    private var date: Date? = null
    private var myDateCheck:Date? = null
    private var mHour = 0
    private var mMin = 0
    private var hours1 = 0
    private var min1 = 0
    private var year1 = 0
    private var month1 = 0
    private var dayOfMonth1 = 0

    private lateinit var calendarAlarm: Calendar



    private lateinit var scheduleMessageViewModel: ScheduleMessageViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_message)

        scheduleMessageViewModel = ViewModelProviders.of(this, ScheduleMessageViewModelFactory(this@ScheduleMessageActivity))
            .get(ScheduleMessageViewModel::class.java)


        toolbar.title = "Schedule Messages"
        setSupportActionBar(toolbar)

        /*** date ***/
        editTxtDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val dialog = DatePickerDialog(this@ScheduleMessageActivity,
                calender, year, month, day)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            dialog.show()
        }

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
                    myDateCheck!!.before(date) -> Toast.makeText(this@ScheduleMessageActivity,
                        "Please, Enter a valid Date!", Toast.LENGTH_LONG).show()
                    else -> {
                        editTxtDate.setText(startDate)
                        editTxtTime.setText("")
                    }
                }
            }

        /*** time ***/
        editTxtTime.setOnClickListener {
            val c = Calendar.getInstance()
            mHour = c.get(Calendar.HOUR_OF_DAY)
            mMin = c.get(Calendar.MINUTE)

            timePickerDialog = TimePickerDialog(this@ScheduleMessageActivity,
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minutes ->
                    val myCalInstance = Calendar.getInstance()
                    val myRealCalender = Calendar.getInstance()

                    if (myDateCheck == null) {
                        val timeStamp = SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().time)
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
                        Toast.makeText(this@ScheduleMessageActivity,
                            "Please, Enter a valid Time!", Toast.LENGTH_LONG).show()
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
                            editTxtTime.setText("0$hourOfDay:$minutes")
                        } else if (hourOfDay < 10 && minutes < 10) {
                            editTxtTime.setText("0$hourOfDay:0$minutes")
                        } else if (hourOfDay >= 10 && minutes < 10) {
                            editTxtTime.setText("$hourOfDay:0$minutes")
                        } else if (hourOfDay >= 10 && minutes >= 10) {
                            editTxtTime.setText("$hourOfDay:$minutes")
                        }
                    }
                }, mHour, mMin, false
            )
            timePickerDialog!!.show()
        }

        btnDone.setOnClickListener {
            val permissionCheck: Int = ContextCompat.checkSelfPermission(this@ScheduleMessageActivity, Manifest.permission.SEND_SMS)
            val permissionCheck1: Int = ContextCompat.checkSelfPermission(this@ScheduleMessageActivity, Manifest.permission.READ_PHONE_STATE)

            if (permissionCheck == PackageManager.PERMISSION_GRANTED && permissionCheck1 == PackageManager.PERMISSION_GRANTED){
                scheduleMessage()
            } else {
                ActivityCompat.requestPermissions(this@ScheduleMessageActivity, arrayOf(Manifest.permission.SEND_SMS), PERMISSION_REQUEST_SEND_SMS)
            }
        }

        imgViewContacts.setOnClickListener {
            val permissionCheck: Int = ContextCompat.checkSelfPermission(this@ScheduleMessageActivity, Manifest.permission.READ_CONTACTS)
            if (permissionCheck == PackageManager.PERMISSION_GRANTED){
                startActivity(Intent(this@ScheduleMessageActivity, ContactsActivity::class.java))
                finish()
            } else {
                ActivityCompat.requestPermissions(this@ScheduleMessageActivity, arrayOf(Manifest.permission.READ_CONTACTS), PERMISSION_REQUEST_READ_CONTACTS)
            }
        }
    }

    private fun scheduleMessage(){
        val phoneNum: String = editTxtReceiverNumber.text.toString().trim()
        val smsMessage: String = editTxtMessage.text.toString().trim()

        if (phoneNum.isEmpty() || phoneNum.isBlank()){
            editTxtReceiverNumber.error = "Receiver Phone Number Required!"
            editTxtReceiverNumber.requestFocus()
            return
        } else if (TextUtils.isDigitsOnly(phoneNum) || !Patterns.PHONE.matcher(phoneNum).matches()){
            editTxtReceiverNumber.error = "Enter a valid phone number!\nDigits only or choose from your contacts!"
            editTxtReceiverNumber.requestFocus()
            return
        } else if (smsMessage.isEmpty() || smsMessage.isBlank()){
            editTxtMessage.error = "Message mustn't be empty!"
            editTxtMessage.requestFocus()
            return
        } else if (editTxtDate.text.toString().trim().isEmpty()){
            Toast.makeText(this@ScheduleMessageActivity, "Enter a valid Date!", Toast.LENGTH_SHORT).show()
            return
        } else if (editTxtTime.text.toString().trim().isEmpty()){
            Toast.makeText(this@ScheduleMessageActivity, "Enter a valid Time!", Toast.LENGTH_SHORT).show()
            return
        } else {

            scheduleMessageViewModel.scheduleMessageViewModel(txtViewPersonName.text.toString(),
                editTxtReceiverNumber.text.toString(), editTxtMessage.text.toString(),
                editTxtDate.text.toString(), editTxtTime.text.toString(),"Pending","SMS",
                calendarAlarm.timeInMillis, "sent")
        }
    }



    fun setSMSAlarm(smsId: String, receiverName: String, receiverNumber: String, SMSMessage: String,
                    date: String, time: String, status: String, type: String, currentUser: String,
                    calendar: Long) {

        val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(this@ScheduleMessageActivity, SMSReceiver::class.java)
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

        val pendingIntent: PendingIntent =
            PendingIntent.getBroadcast(this@ScheduleMessageActivity, smsId.hashCode(), intent, 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendarAlarm.timeInMillis, pendingIntent)
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendarAlarm.timeInMillis, pendingIntent)
        }

        Toast.makeText(this@ScheduleMessageActivity, "Message scheduled successfully!", Toast.LENGTH_SHORT).show()
        finish()
    }






    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode){
            PERMISSION_REQUEST_SEND_SMS -> {
                if (grantResults.size >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    scheduleMessage()
                } else {
                    Toast.makeText(this@ScheduleMessageActivity, "Give us the permission to send your messages!", Toast.LENGTH_LONG).show()
                }
            }

            PERMISSION_REQUEST_READ_CONTACTS -> {
                if (grantResults.size >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startActivity(Intent(this@ScheduleMessageActivity, ContactsActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@ScheduleMessageActivity, "Give us the permission to read your contacts!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    inner class ScheduleMessageViewModelFactory(private val scheduleMessageActivity: ScheduleMessageActivity) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ScheduleMessageViewModel(scheduleMessageActivity) as T
        }
    }
}
