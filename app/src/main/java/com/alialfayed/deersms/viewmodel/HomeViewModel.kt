package com.alialfayed.deersms.viewmodel

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.alialfayed.deersms.repo.Repository
import com.alialfayed.deersms.view.activity.AddGroupActivity
import com.alialfayed.deersms.view.activity.AddMessageActivity
import com.alialfayed.deersms.view.activity.GroupContactsActivity
import com.alialfayed.deersms.view.activity.SettingsActivity


/**
 * Class do :
 * Created ( Eng Ali)
 */
class HomeViewModel(val homeActivity: Activity) : ViewModel() {
    var repository: Repository = Repository(homeActivity)


    /**
    * method do (move to add message)
    */
    fun addMessage(){
        val intent = Intent(homeActivity,AddMessageActivity::class.java)
        homeActivity.startActivity(intent)
    }
    /**
     * method do (move to add Group)
     */
    fun addGroups(){
        val intent = Intent(homeActivity,AddGroupActivity::class.java)
        homeActivity.startActivity(intent)
    }

    /**
     * method do (move to Groups and Contacts)
     */
    fun groupContacts(){
        val intent = Intent(homeActivity,GroupContactsActivity::class.java)
        homeActivity.startActivity(intent)
    }

    /**
     * method do (move to settings)
     */
    fun settings(){
        val intent = Intent(homeActivity,SettingsActivity::class.java)
        homeActivity.startActivity(intent)
    }



}