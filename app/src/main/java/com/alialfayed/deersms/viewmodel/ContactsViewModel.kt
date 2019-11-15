package com.alialfayed.deersms.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alialfayed.deersms.model.ContactList
import com.alialfayed.deersms.repo.ContactsRepository
import com.alialfayed.deersms.view.fragment.ContactsFragment

class ContactsViewModel : ViewModel {
    var viewRef: ContactsFragment? = null
    var repoRef: ContactsRepository? = null
    private lateinit var mutableLiveData: MutableLiveData<List<ContactList>>


    constructor(viewRef: ContactsFragment) {
        this.viewRef = viewRef
        repoRef = ContactsRepository(this)
        mutableLiveData = MutableLiveData()

    }

    fun passContext(): Context {
        return viewRef!!.passContext()
    }

    fun getAllOrders(): MutableLiveData<ArrayList<ContactList>> {
        // val contactsList = repoRef!!.getContactsList()
        mutableLiveData.postValue(repoRef!!.getContactsList())

        return mutableLiveData as MutableLiveData<ArrayList<ContactList>>
    }

    private val _text = MutableLiveData<String>().apply {
        value = "Please Enable App To Access Your Contacts"
    }
    val text: LiveData<String> = _text


}