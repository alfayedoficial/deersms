package com.alialfayed.deersms.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alialfayed.deersms.ContactsRepository.ContactsRepository
import com.alialfayed.deersms.model.ContactList
import com.alialfayed.deersms.model.GroupFirebase
import com.alialfayed.deersms.repo.GroupsRepository
import com.alialfayed.deersms.view.activity.AddGroupActivity

/**
 * Class do :
 * Created by ( Eng Ali)
 */
class AddGroupViewModel : ViewModel {


    var addGroupActivityRef: AddGroupActivity? = null
    var contactsRepository: ContactsRepository? = null
    var groupsRepository: GroupsRepository? = null
    private lateinit var mutableLiveData: MutableLiveData<List<ContactList>>


    constructor(addGroupActivityRef: AddGroupActivity) {
        this.addGroupActivityRef = addGroupActivityRef
        groupsRepository = GroupsRepository(this)
        contactsRepository = ContactsRepository(this)
        mutableLiveData = MutableLiveData()

    }

    fun uploadGroup(group: GroupFirebase) {
        groupsRepository!!.creatGroup(group)
    }


    fun getContacts(): MutableLiveData<ArrayList<ContactList>> {
        // val contactsList = contactsRepository!!.getContactsList()
        mutableLiveData.postValue(contactsRepository!!.getContactsList(addGroupActivityRef!!))
        return mutableLiveData as MutableLiveData<ArrayList<ContactList>>
    }


}