package com.alialfayed.deersms.repo

import com.alialfayed.deersms.model.GroupFirebase
import com.alialfayed.deersms.viewmodel.AddGroupViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class GroupsRepository {

    var addGroupviewModel: AddGroupViewModel
    var mdatabaseReference: DatabaseReference


    constructor(addGroupviewModel: AddGroupViewModel) {
        this.addGroupviewModel = addGroupviewModel
        mdatabaseReference = FirebaseDatabase.getInstance().getReference("Groups")
    }

    fun creatGroup(group: GroupFirebase) {
        val id = mdatabaseReference.push().key.toString()
        mdatabaseReference.child(id).setValue(group)
    }
}