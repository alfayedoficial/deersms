package com.alialfayed.deersms.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.alialfayed.deersms.R
import com.alialfayed.deersms.model.ContactList
import com.alialfayed.deersms.model.GroupFirebase
import com.alialfayed.deersms.view.adapter.ContactListAdabter
import com.alialfayed.deersms.viewmodel.AddGroupViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_add_group.*
import java.util.*

class AddGroupActivity : AppCompatActivity(), ContactListAdabter.ChnageStatusListener {

    private lateinit var addGroupViewModel: AddGroupViewModel
    private var contactListAdabter: ContactListAdabter? = null

    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_group)


        auth = FirebaseAuth.getInstance()
        addGroupViewModel = ViewModelProviders.of(this, CreatGroupViewModelFactory(this)).get(
            AddGroupViewModel::class.java
        )



        searchView_AddGroup.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                contactListAdabter!!.getFilter().filter(newText)
                return false
            }
        })


        //save data on firebase
        imageBtnAdd_AddGroup.setOnClickListener {
            val groupName = edtNameGroup_AddGroup.text!!.trim().toString()
            if (!groupName.isEmpty()) {
                val list = ArrayList<ContactList>()
                addGroupViewModel.getContacts()!!.observe(
                    this, object : Observer<MutableList<ContactList>> {
                        override fun onChanged(contacts: MutableList<ContactList>?) {
                            for (i in contacts!!.indices) {
                                if (contacts.get(i).isSelected()) {
                                    list.add(contacts.get(i))
                                }
                            }
                        }
                    })
                if (auth!!.currentUser != null) {
                    val uid = auth!!.currentUser!!.uid
                    var group: GroupFirebase = GroupFirebase(uid, groupName, list)
                    addGroupViewModel.uploadGroup(group)
                }
            }
            val intent = Intent(this,GroupContactsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        var contactList = ArrayList<ContactList>()
        setAdapter()
        addGroupViewModel.getContacts()!!.observe(
            this,
            object : Observer<MutableList<ContactList>> {
                override fun onChanged(contacts: MutableList<ContactList>?) {
                    contactListAdabter!!.setData((contacts)!!)
                    for (i in contacts!!.indices) {
                        if (contacts.get(i).isSelected()) {
                            contactList.add(contacts.get(i))
                        }
                    }
                }
            }
        )
    }

    private fun setAdapter() {
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView_AddGroup!!.setLayoutManager(linearLayoutManager)
        contactListAdabter = ContactListAdabter()
        recyclerView_AddGroup!!.setAdapter(contactListAdabter)
    }

    override fun onItemChangeListener(position: Int, model: ContactList) {
        try {
            val AllContacts = addGroupViewModel.getContacts()
            addGroupViewModel.getContacts()!!.observe(this, object :
                Observer<MutableList<ContactList>> {
                override fun onChanged(contacts: MutableList<ContactList>?) {
                    contacts!!.set(position, model)
                }
            })
        } catch (e: Exception) {
        }
    }

    internal inner class CreatGroupViewModelFactory : ViewModelProvider.Factory {
        private var addGroupActivity: AddGroupActivity

        constructor(addGroupActivity: AddGroupActivity) {
            this.addGroupActivity = addGroupActivity
        }

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddGroupViewModel(
                addGroupActivity
            ) as T
        }
    }


}
