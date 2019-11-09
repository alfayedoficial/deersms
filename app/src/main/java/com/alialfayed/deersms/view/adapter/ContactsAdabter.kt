package com.alialfayed.deersms.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alialfayed.deersms.R
import com.alialfayed.deersms.model.Contacts
import com.alialfayed.deersms.view.activity.AddMessageActivity
import com.alialfayed.deersms.view.activity.ContactsActivity
import kotlinx.android.synthetic.main.cardview_contacts.view.*
import kotlinx.android.synthetic.main.template_item.view.*

/**
 * Class do :
 * Created by ( Eng Ali)
 */
class ContactsAdabter(val contactsList:ArrayList<Contacts>, var contactsActivity : ContactsActivity):RecyclerView.Adapter<ContactsAdabter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsAdabter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_contacts,parent,false)
    return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    override fun onBindViewHolder(holder: ContactsAdabter.ViewHolder, position: Int) {
        holder.bindItem(contactsList[position])
        val textnumber = contactsList.get(position).phoneNumber
        val re = Regex("[^A-Za-z0-9 ]")
        var editnumber = textnumber.replace(re,"").replace(" ","")
        holder.contactcard.setOnClickListener {
            contactsActivity.startActivity(Intent(contactsActivity, AddMessageActivity::class.java).putExtra("result2",editnumber))
        }

    }
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bindItem(contacts: Contacts) {
            val textName = itemView.findViewById(R.id.text_view_title) as TextView
            val textPhoneNumber = itemView.findViewById(R.id.txtNumber_CardView_Contacts) as TextView

            textName.text = contacts.name
            textPhoneNumber.text = contacts.phoneNumber

        }
        val contactcard = itemView.card_Contacts

    }



















}







