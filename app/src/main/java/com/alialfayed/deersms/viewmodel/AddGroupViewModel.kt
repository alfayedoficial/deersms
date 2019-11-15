package com.alialfayed.deersms.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.alialfayed.deersms.model.GroupNambers
import com.alialfayed.deersms.repo.FirebaseHandler
import com.alialfayed.deersms.view.activity.AddGroupActivity

/**
 * Class do :
 * Created by ( Eng Ali)
 */
class AddGroupViewModel :ViewModel() {
    private lateinit var addGroupActivity:AddGroupActivity
    private lateinit var firebaseHandler: FirebaseHandler

    fun setAddGroup(addGroupActivity:Activity){
        this.addGroupActivity = addGroupActivity as AddGroupActivity
        this.firebaseHandler = FirebaseHandler(addGroupActivity , this)
    }

    fun addGroup(groupName:String , groupNumbers:Array<List<GroupNambers>>){
        firebaseHandler.addGroup(groupName,groupNumbers)
    }

}