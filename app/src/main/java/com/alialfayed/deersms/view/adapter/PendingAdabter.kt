package com.alialfayed.deersms.view.adapter

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.alialfayed.deersms.R
import com.alialfayed.deersms.model.MessageFirebase
import com.alialfayed.deersms.utils.SMSReceiver
import com.alialfayed.deersms.view.activity.AddMessageActivity
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.ArrayList

/**
 * Class do :
 * Created by ( Eng Ali)
 */
class PendingAdabter : RecyclerView.Adapter<PendingAdabter.ViewHolder> {

    private var dataPendingAdabter = ArrayList<MessageFirebase>()
    internal var activity: Activity
    private var mdatabaseReference = FirebaseDatabase.getInstance().getReference("Message")

    constructor(activity: Activity) {
        this.activity = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.cardview_pending, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return if (dataPendingAdabter.size > 0) {
            dataPendingAdabter.size
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val message = dataPendingAdabter[position]

        holder.getTxtTime()!!.text = message.getSmsTime()
        holder.getTxtDate()!!.text = message.getSmsDate()
        holder.getTxtContacts()!!.text = message.getSmsReceiverNumber()
        holder.getTxtMessage()!!.text = message.getSmsMsg()
        if (message.getSmsType() == "SMS") {
            holder.getImageSender()!!.setImageResource(R.drawable.ic_sms)
        } else {
            holder.getImageSender()!!.setImageResource(R.drawable.ic_whatsapp)
        }
        holder.getBtnDelete()!!.setOnClickListener {
            mdatabaseReference.child(message.getSmsId()).removeValue()
            val alarRemoved = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intentRemoved = Intent(activity, SMSReceiver::class.java)
            intentRemoved.putExtra("SmsId", message.getSmsId())
            intentRemoved.putExtra("SmsReceiverName", message.getSmsReceiverName())
            intentRemoved.putExtra("SmsReceiverNumber", message.getSmsReceiverNumber())
            intentRemoved.putExtra("SmsMsg", message.getSmsMsg())
            intentRemoved.putExtra("SmsDate", message.getSmsDate())
            intentRemoved.putExtra("SmsTime", message.getSmsTime())
            intentRemoved.putExtra("SmsStatus", message.getSmsStatus())
            intentRemoved.putExtra("SmsType", message.getSmsType())
            intentRemoved.putExtra("UserID", message.getUserID())
            intentRemoved.putExtra("calendar", message.getSmsCalender())

            val pendingIntent = PendingIntent.getBroadcast(
                activity,
                message.getSmsId().hashCode(),
                intentRemoved,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            alarRemoved.cancel(pendingIntent)

            Toast.makeText(
                activity, "Message Deleted Successfully!",
                Toast.LENGTH_SHORT
            ).show()

        }
        // TODO SEND - EDITE Buttons
        holder.getBtnEdit()!!.setOnClickListener {
            //TODO Edite Activity
            val editIntent = Intent(activity, AddMessageActivity::class.java)

            editIntent.putExtra("SmsId", message.getSmsId())
            editIntent.putExtra("SmsReceiverName", message.getSmsReceiverName())
            editIntent.putExtra("SmsReceiverNumber", message.getSmsReceiverNumber())
            editIntent.putExtra("SmsMsg", message.getSmsMsg())
            editIntent.putExtra("SmsDate", message.getSmsDate())
            editIntent.putExtra("SmsTime", message.getSmsTime())
            editIntent.putExtra("SmsStatus", message.getSmsStatus())
            editIntent.putExtra("SmsType", message.getSmsType())
            editIntent.putExtra("UserID", message.getUserID())
            editIntent.putExtra("calendar", message.getSmsCalender())

            activity.startActivity(editIntent)

        }
        holder.getBtnSend()!!.setOnClickListener {
            val alarSend = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intentSend = Intent(activity, SMSReceiver::class.java)
            intentSend.putExtra("SmsId", message.getSmsId())
            intentSend.putExtra("SmsReceiverName", message.getSmsReceiverName())
            intentSend.putExtra("SmsReceiverNumber", message.getSmsReceiverNumber())
            intentSend.putExtra("SmsMsg", message.getSmsMsg())
            intentSend.putExtra("SmsDate", message.getSmsDate())
            intentSend.putExtra("SmsTime", message.getSmsTime())
            intentSend.putExtra("SmsStatus", message.getSmsStatus())
            intentSend.putExtra("SmsType", message.getSmsType())
            intentSend.putExtra("UserID", message.getUserID())
            intentSend.putExtra("calendar", message.getSmsCalender())

            val pendingIntent = PendingIntent.getBroadcast(
                activity,
                message.getSmsId().hashCode(),
                intentSend,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarSend.setExact(
                    AlarmManager.RTC_WAKEUP,
                    Calendar.getInstance().time.time,
                    pendingIntent
                )
            } else {
                alarSend.set(
                    AlarmManager.RTC_WAKEUP,
                    Calendar.getInstance().time.time,
                    pendingIntent
                )
            }


            Toast.makeText(
                activity, "Message Send Successfully!",
                Toast.LENGTH_SHORT
            ).show()
        }


    }

    fun setDataToAdapter(dataPendingAdabter: ArrayList<MessageFirebase>) {
        this.dataPendingAdabter = dataPendingAdabter
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var txtTime_CardView_pending: TextView? = null
        private var txtData_CardView_pending: TextView? = null
        private var txtContacts_CardView_pending: TextView? = null
        private var txtMessage_CardView_pending: TextView? = null
        private var btnedit_CardView_pending: Button? = null
        private var btnDelete_CardView_pending: Button? = null
        private var btnSend_CardView_pending: Button? = null
        private var imageSender: ImageView? = null

        fun getTxtTime(): TextView? {
            if (txtTime_CardView_pending == null) {
                txtTime_CardView_pending = itemView.findViewById(R.id.txtTime_CardView_pending)
            }
            return txtTime_CardView_pending
        }
        fun getTxtDate(): TextView? {
            if (txtData_CardView_pending == null) {
                txtData_CardView_pending = itemView.findViewById(R.id.txtData_CardView_pending)
            }
            return txtTime_CardView_pending
        }

        fun getTxtContacts(): TextView? {
            if (txtContacts_CardView_pending == null) {
                txtContacts_CardView_pending =
                    itemView.findViewById(R.id.txtContacts_CardView_pending)
            }
            return txtContacts_CardView_pending
        }

        fun getTxtMessage(): TextView? {
            if (txtMessage_CardView_pending == null) {
                txtMessage_CardView_pending =
                    itemView.findViewById(R.id.txtMessage_CardView_pending)
            }
            return txtMessage_CardView_pending
        }

        fun getImageSender(): ImageView? {
            if (imageSender == null) {
                imageSender = itemView.findViewById(R.id.imageSender)
            }
            return imageSender
        }

        fun getBtnEdit(): Button? {
            if (btnedit_CardView_pending == null) {
                btnedit_CardView_pending = itemView.findViewById(R.id.btnedit_CardView_pending)
            }
            return btnedit_CardView_pending
        }

        fun getBtnDelete(): Button? {
            if (btnDelete_CardView_pending == null) {
                btnDelete_CardView_pending = itemView.findViewById(R.id.btnDelete_CardView_pending)
            }
            return btnDelete_CardView_pending
        }

        fun getBtnSend(): Button? {
            if (btnSend_CardView_pending == null) {
                btnSend_CardView_pending = itemView.findViewById(R.id.btnSend_CardView_pending)
            }
            return btnSend_CardView_pending
        }


    }
}