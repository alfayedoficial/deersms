package com.alialfayed.deersms.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alialfayed.deersms.model.ContactList
import com.alialfayed.deersms.view.activity.AddMessageActivity
import com.alialfayed.deersms.view.activity.WhatsAppActivity
import kotlinx.android.synthetic.main.cardview_contacts.view.*


/**
 * Class do :
 * Created by( Eng Ali)
 */

class ContactListAdabter : RecyclerView.Adapter<ContactListAdabter.HolderClass>(), Filterable {


    interface ChnageStatusListener {
        fun onItemChangeListener(position: Int, model: ContactList)
    }

    private var context: Context? = null
    private var checker: Boolean = true
    //    private lateinit var groupContactsActivity :GroupContactsActivity
    private var list: MutableList<ContactList>? = null
    private var fulllist: List<ContactList>? = null
    internal var chnageStatusListener: ChnageStatusListener? = null


    override fun getItemCount(): Int {
        return if (list != null) list!!.size else 0;
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: HolderClass, position: Int) {
        val contact: ContactList = list!!.get(position)
        if (contact != null) {
            holder.position = position
            holder.contactName.setText(contact.name)
            holder.contactNumber.setText(contact.number)
            if (contact.image != null)
                holder.contactImage.setImageBitmap(contact.image)
            else
                holder.contactImage.setImageDrawable(
                    ContextCompat.getDrawable(
                        context!!,
                        com.alialfayed.deersms.R.mipmap.ic_launcher_round
                    )
                )
            if (contact.isSelected()) {
                holder.view.setBackgroundResource(com.alialfayed.deersms.R.color.background)
            } else {
                holder.view.setBackgroundResource(com.alialfayed.deersms.R.color.cardView_color)
            }


        }
        holder.view.setOnLongClickListener {
            val model1: ContactList = list!!.get(position)
            if (model1.isSelected()) {
                model1.setSelected(false)
            } else {
                model1.setSelected(true)
            }

            list!!.set(holder.position!!, model1)
            if (chnageStatusListener != null) {
                chnageStatusListener!!.onItemChangeListener(holder.position!!, model1)
            }
            notifyItemChanged(holder.position!!)
            checker = false

            return@setOnLongClickListener true


        }

        holder.view.setOnClickListener {
            if (checker) {
                val intent = Intent(context, AddMessageActivity::class.java)
                intent.putExtra("nameContact", contact.name)
                intent.putExtra("phoneContact", contact.number)
                context?.startActivity(intent)
//            activity?.finish()
            } else {
                val model1: ContactList = list!!.get(position)
                if (model1.isSelected()) {
                    model1.setSelected(false)
                } else {
                    model1.setSelected(true)
                }

                list!!.set(holder.position!!, model1)
                if (chnageStatusListener != null) {
                    chnageStatusListener!!.onItemChangeListener(holder.position!!, model1)
                }
                notifyItemChanged(holder.position!!)
                checker = false
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderClass {
        context = parent.getContext()
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(
            com.alialfayed.deersms.R.layout.cardview_contacts,
            parent, false
        )
        return HolderClass(view)

    }

    fun setData(contactList: MutableList<ContactList>) {
        this.list = contactList
        fulllist = ArrayList(list!!)
        notifyDataSetChanged()
    }

    inner class HolderClass : RecyclerView.ViewHolder {
        val contactName: TextView
        val contactNumber: TextView
        val contactImage: ImageView
        val view: View
        var position: Int? = 0

        @SuppressLint("ResourceAsColor")
        constructor(itemView: View) : super(itemView) {
            contactName = itemView.txtViewTitle_CardView_Contacts
            contactNumber = itemView.txtNumber_CardView_Contacts
            contactImage = itemView.imageView_CardView_Contacts
            this.view = itemView
        }

    }

    override fun getFilter(): android.widget.Filter {
        return contactsFilter
    }

    private val contactsFilter = object : android.widget.Filter() {
        override fun performFiltering(constraint: CharSequence?): android.widget.Filter.FilterResults {
            val filteredList: ArrayList<ContactList> = ArrayList()
            if (constraint == null || constraint.length == 0) {
                filteredList.addAll(fulllist!!)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }
                for (item in fulllist!!) {
                    if (item.name.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            list!!.clear()
            list!!.addAll(results.values as Collection<ContactList>)
            notifyDataSetChanged()
        }
    }


}