package com.alialfayed.deersms.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.alialfayed.deersms.R
import com.alialfayed.deersms.model.Contacts
import com.alialfayed.deersms.view.activity.*

/**
 * Class do :
 * Created by ( Eng Ali)
 */
class ContactsSMSActivityAdabter(val contactsList:ArrayList<Contacts>, var contactsActivity: ContactsSMSActivity):RecyclerView.Adapter<ContactsSMSActivityAdabter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_contacts,parent,false)
    return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

      val card =  contactsList[position]
        holder.getCardView()!!.setOnClickListener {
            val intent = Intent(contactsActivity,CurrentSIMActivity::class.java)
            intent.putExtra("nameContact" , card.getName())
            intent.putExtra("phoneContact" , card.getPhone())
            contactsActivity.startActivity(intent)
            contactsActivity.finish()
        }

        holder.getTxtViewPersonName()!!.text = card.getName()
        holder.getTxtViewPhoneNumber()!!.text = card.getPhone()


    }
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {


        private var cardView: CardView? = null
        private var txtViewPersonName: TextView? = null
        private var txtViewPhoneNumber: TextView? = null

        fun getCardView(): CardView? {
            if (cardView == null){
                cardView = itemView.findViewById(R.id.card_Contacts)
            }
            return cardView
        }

        fun getTxtViewPersonName(): TextView? {
            if (txtViewPersonName == null) {
                txtViewPersonName = itemView.findViewById(R.id.txtViewTitle_CardView_Contacts)
            }
            return txtViewPersonName
        }

        fun getTxtViewPhoneNumber(): TextView? {
            if (txtViewPhoneNumber == null) {
                txtViewPhoneNumber = itemView.findViewById(R.id.txtNumber_CardView_Contacts)
            }
            return txtViewPhoneNumber
        }

    }



















}







