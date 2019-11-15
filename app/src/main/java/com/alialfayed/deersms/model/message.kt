package com.alialfayed.deersms.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

/**
 * Class do :
 * Created by ( Eng Ali)
 */
val MSG_PENDING = "pending"
val MSG_SENT = "sending"
val MSG_RECEIVED = "received"
@Entity
class Message() {

    @PrimaryKey(autoGenerate = true)
    var msgId: Long = 0
    var msgTitle: String = ""
    var msgBody: String = ""
    var msgStatus: String = MSG_PENDING
    var contactName: String = ""
    var contactNumber: String = ""
    var msgYear: Int = 0
    var msgMonth: Int = 0
    var msgDay: Int = 0
    var msgHour: Int = 0
    var msgMinute: Int = 0


    @Ignore
    constructor(
        msgTitle: String,
        msgBody: String,
        contact: Contacts,
        msgCalendar: Calendar
    ) : this() {

        this.msgTitle = msgTitle
        this.msgBody = msgBody
        msgStatus = MSG_PENDING
        contactName = contact.getName()
        contactNumber = contact.getPhone()
        this.msgYear = msgCalendar.get(Calendar.YEAR)
        msgMonth = msgCalendar.get(Calendar.MONTH)
        msgDay = msgCalendar.get(Calendar.DAY_OF_MONTH)
        msgHour = msgCalendar.get(Calendar.HOUR)
        msgMinute = msgCalendar.get(Calendar.MINUTE)
    }

    fun setMsgCalendar(msgCalendar: Calendar) {
        msgYear = msgCalendar.get(Calendar.YEAR)
        msgMonth = msgCalendar.get(Calendar.MONTH)
        msgDay = msgCalendar.get(Calendar.DAY_OF_MONTH)
        msgHour = msgCalendar.get(Calendar.HOUR)
        msgMinute = msgCalendar.get(Calendar.MINUTE)
    }

    fun getMsgCalendar(): Calendar {
        val cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, msgYear)
        cal.set(Calendar.MONTH, msgMonth)
        cal.set(Calendar.DAY_OF_MONTH, msgDay)
        cal.set(Calendar.HOUR_OF_DAY, msgHour)
        cal.set(Calendar.MINUTE, msgMinute)
        return cal
    }

    fun setContact(contact: Contacts) {
        contactName = contact.getName()
        contactNumber = contact.getPhone()
    }
}
