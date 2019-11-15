package com.alialfayed.deersms.view.adapter

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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
import com.google.firebase.database.FirebaseDatabase

/**
 * Class do :
 * Created by ( Eng Ali)
 */
class CompletedAdabter:RecyclerView.Adapter<CompletedAdabter.ViewHolder> {

    internal var activity: Activity
    private var mdatabaseReference = FirebaseDatabase.getInstance().getReference("Message")
    private var dataCompletedAdabter = ArrayList<MessageFirebase>()

    constructor(activity: Activity) {
        this.activity = activity
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompletedAdabter.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.cardview_completed, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return if (dataCompletedAdabter.size > 0) {
            dataCompletedAdabter.size
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: CompletedAdabter.ViewHolder, position: Int) {
        val message = dataCompletedAdabter[position]

        holder.getTxtTime()!!.text = message.getSmsTime()
        holder.getTxtDate()!!.text = message.getSmsDate()
        holder.getTxtContacts()!!.text = message.getSmsReceiverNumber()
        holder.getTxtMessage()!!.text = message.getSmsMsg()
        if (message.getSmsType() == "SMS") {
            holder.getImageSender()!!.setImageResource(R.drawable.ic_sms)
        } else {
            holder.getImageSender()!!.setImageResource(R.drawable.ic_whatsapp)
        }
        if (message.getSmsDelivered() == "Delivered"){
            holder.getImageDelivered()!!.setImageResource(R.drawable.ic_received)
        }else{
            holder.getImageDelivered()!!.setImageResource(R.drawable.ic_send)
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
    }


    fun setDataToAdapter(completedList: ArrayList<MessageFirebase>) {
        this.dataCompletedAdabter= completedList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var txtTime_CardView_Completed: TextView? = null
        private var txtData_CardView_Completed: TextView? = null
        private var txtContacts_CardView_Completed: TextView? = null
        private var txtMessage_CardView_Completed: TextView? = null
        private var btnDelete_CardView_Completed: Button? = null
        private var imageSender_Completed: ImageView? = null
        private var imageViewDelivered : ImageView? = null

        fun getTxtTime(): TextView? {
            if (txtTime_CardView_Completed == null) {
                txtTime_CardView_Completed = itemView.findViewById(R.id.txtTime_CardView_Completed)
            }
            return txtTime_CardView_Completed
        }

        fun getTxtDate(): TextView? {
            if (txtData_CardView_Completed == null) {
                txtData_CardView_Completed = itemView.findViewById(R.id.txtData_CardView_Completed)
            }
            return txtData_CardView_Completed
        }

        fun getTxtContacts(): TextView? {
            if (txtContacts_CardView_Completed == null) {
                txtContacts_CardView_Completed =
                    itemView.findViewById(R.id.txtContacts_CardView_Completed)
            }
            return txtContacts_CardView_Completed
        }

        fun getTxtMessage(): TextView? {
            if (txtMessage_CardView_Completed == null) {
                txtMessage_CardView_Completed =
                    itemView.findViewById(R.id.txtMessage_CardView_Completed)
            }
            return txtMessage_CardView_Completed
        }

        fun getImageSender(): ImageView? {
            if (imageSender_Completed == null) {
                imageSender_Completed = itemView.findViewById(R.id.imageSender_Completed)
            }
            return imageSender_Completed
        }

        fun getImageDelivered(): ImageView? {
            if (imageViewDelivered == null) {
                imageViewDelivered = itemView.findViewById(R.id.imageViewDelivered)
            }
            return imageViewDelivered
        }


        fun getBtnDelete(): Button? {
            if (btnDelete_CardView_Completed == null) {
                btnDelete_CardView_Completed = itemView.findViewById(R.id.btnDelete_CardView_Completed)
            }
            return btnDelete_CardView_Completed
        }




    }
}