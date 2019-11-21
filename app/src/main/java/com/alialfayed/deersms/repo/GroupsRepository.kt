package com.alialfayed.deersms.repo

import com.alialfayed.deersms.model.ContactList
import com.alialfayed.deersms.model.GroupFirebase
import com.alialfayed.deersms.viewmodel.AddGroupViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.ArrayList

class GroupsRepository {

    var addGroupviewModel: AddGroupViewModel
    var mdatabaseReference: DatabaseReference
    var auth: FirebaseAuth


    constructor(addGroupviewModel: AddGroupViewModel) {
        this.addGroupviewModel = addGroupviewModel
        auth = FirebaseAuth.getInstance()
        mdatabaseReference = FirebaseDatabase.getInstance().getReference("Groups")
    }

    fun insetGroup(list : ArrayList<ContactList> , groupName: String){
        /**
         * create  Group Firebase
         * 1- create id group
         * 2- create group
         * 3- inset data
         */
        val groupId: String = mdatabaseReference.push().key.toString()
        if (auth!!.currentUser != null) {
            val userId = auth!!.currentUser!!.uid
            var group: GroupFirebase = GroupFirebase(userId,groupId,groupName,list)
            mdatabaseReference.child(groupId).setValue(group)
        }


    }


}