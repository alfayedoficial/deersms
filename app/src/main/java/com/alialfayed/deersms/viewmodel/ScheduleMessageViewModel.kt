package com.alialfayed.deersms.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.alialfayed.deersms.repo.FirebaseHandler
import com.alialfayed.deersms.view.activity.AddMessageActivity
import com.alialfayed.deersms.view.activity.ScheduleMessageActivity

class ScheduleMessageViewModel : ViewModel {

//    var scheduleMessageActivity: ScheduleMessageActivity
    var addMessageActivity:AddMessageActivity
    var fireBaseRepository: FirebaseHandler

    constructor(activity: Activity){
//        this.scheduleMessageActivity = activity as ScheduleMessageActivity
        this.addMessageActivity = activity as AddMessageActivity
        this.fireBaseRepository = FirebaseHandler(activity,this)
    }


    fun scheduleMessageViewModel(personName: String, receiverNumber: String, message: String,
                                 date: String, time: String, status: String, type: String,
                                 calendar: Long , smsDelivered :String) {

        fireBaseRepository.scheduleMessageRepository(personName, receiverNumber, message, date, time,
            status, type, calendar , smsDelivered)
    }

//    fun setSMSAlarm(smsId: String, personName: String, receiverNumber: String, SMSMessage: String,
//                   date: String, time: String, status: String, type: String, currentUser: String,
//                   calendar: Long) {
//
//        scheduleMessageActivity.setSMSAlarm(smsId, personName, receiverNumber, SMSMessage, date,
//            time, status, type, currentUser, calendar)
//    }

    fun setSMSAlarm(smsId: String, personName: String, receiverNumber: String, SMSMessage: String,
                    date: String, time: String, status: String, type: String, currentUser: String,
                    calendar: Long, smsDelivered :String) {

        addMessageActivity.setSMSAlarm(smsId, personName, receiverNumber, SMSMessage, date,
            time, status, type, currentUser, calendar , smsDelivered)
    }
}