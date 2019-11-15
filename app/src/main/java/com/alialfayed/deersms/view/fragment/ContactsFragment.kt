package com.alialfayed.deersms.view.fragment


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alialfayed.deersms.R
import com.alialfayed.deersms.model.ContactList
import com.alialfayed.deersms.view.adapter.ContactListAdabter
import com.alialfayed.deersms.viewmodel.ContactsViewModel

/**
 * A simple [Fragment] subclass.
 */
class ContactsFragment : Fragment(), ContactListAdabter.ChnageStatusListener {

    private lateinit var contactsViewModel: ContactsViewModel
    private var adapterClass: ContactListAdabter? = null
    private var recyclerView: RecyclerView? = null
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        contactsViewModel = ViewModelProviders.of(this, ContactsViewModelFactory(this)).get(
            ContactsViewModel::class.java
        )
        val view = inflater.inflate(R.layout.fragment_contacts, container, false)

        recyclerView = view.findViewById(R.id.recyclerView_Contacts)
        searchView = view.findViewById(R.id.searchView_Contacts)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapterClass!!.getFilter().filter(newText)
                return false
            }
        })

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setAdapter()
        contactsViewModel.getAllOrders()!!.observe(
            this,
            object : Observer<MutableList<ContactList>> {
                override fun onChanged(contacts: MutableList<ContactList>?) {
                    adapterClass!!.setData((contacts as MutableList<ContactList>?)!!)
                }
            })
    }

    private fun setAdapter() {
        val manager = LinearLayoutManager(activity)
        recyclerView!!.setLayoutManager(manager)
        adapterClass = ContactListAdabter()
        recyclerView!!.setAdapter(adapterClass)
    }

    fun passContext(): Context {
        return this!!.activity!!
    }

    override fun onItemChangeListener(position: Int, model: ContactList) {
        try {

            val allOrders = contactsViewModel.getAllOrders()
            contactsViewModel.getAllOrders()!!.observe(this, object :
                Observer<MutableList<ContactList>> {
                override fun onChanged(contacts: MutableList<ContactList>?) {
                    contacts!!.set(position, model)
                    //  adapterClass!!.setData((contacts as MutableList<Contact>?)!!)
                }
            })

        } catch (e: Exception) {

        }

    }

    internal inner class ContactsViewModelFactory : ViewModelProvider.Factory {
        private var contactFragment: ContactsFragment

        constructor(contactFragment: ContactsFragment) {
            this.contactFragment = contactFragment

        }

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ContactsViewModel(
                contactFragment
            ) as T
        }
    }


}
