package com.alialfayed.deersms.repo

import android.net.Uri
import android.provider.ContactsContract
import android.provider.MediaStore
import com.alialfayed.deersms.model.ContactList
import com.alialfayed.deersms.viewmodel.ContactsViewModel
import java.util.*
import kotlin.collections.ArrayList

class ContactsRepository_D {

    lateinit var contactViewModel: ContactsViewModel
//    lateinit var addGroupViewModel: AddGroupViewModel


    constructor(contactViewModel: ContactsViewModel) {
        this.contactViewModel = contactViewModel
    }

//    constructor(addGroupViewModel: AddGroupViewModel){
//        this.addGroupViewModel= addGroupViewModel
//    }


    fun getContactsList(): List<ContactList> {

        val contactList: MutableList<ContactList> = ArrayList()
        val contacts = contactViewModel.passContext().contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null, null
        )
        while (contacts!!.moveToNext()) {
            val name =
                contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val number =
                contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val obj = ContactList()
            obj.name = name
            obj.number = number

            val photo_uri =
                contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))
            if (photo_uri != null) {
                obj.image = MediaStore.Images.Media.getBitmap(
                    contactViewModel.passContext().contentResolver,
                    Uri.parse(photo_uri)
                )
            }
            contactList.add(obj)
        }
        //contact_list.adapter = ContactAdapter(contactList,this)
        contacts.close()

        Collections.sort(contactList, object : Comparator<ContactList> {
            override fun compare(o1: ContactList, o2: ContactList): Int {
                return o1.name.compareTo(o2.name)
            }
        })


        return contactList
    }

}




