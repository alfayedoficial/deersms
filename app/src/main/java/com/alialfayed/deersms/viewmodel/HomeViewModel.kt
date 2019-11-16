package com.alialfayed.deersms.viewmodel

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.alialfayed.deersms.repo.FirebaseHandler
import com.alialfayed.deersms.repo.MessageRepository
import com.alialfayed.deersms.view.activity.*


/**
 * Class do :
 * Created ( Eng Ali)
 */
class HomeViewModel(val homeActivity: Activity) : ViewModel() {
    var messageRepository: MessageRepository = MessageRepository(homeActivity)
    private var firebaseHandler: FirebaseHandler = FirebaseHandler(homeActivity,this)


    /**
    * method do (move to add Message)
    */
    fun addMessage(){
        val intent = Intent(homeActivity,AddMessageActivity::class.java)
        homeActivity.startActivity(intent)
//        val intent = Intent(homeActivity,ScheduleMessageActivity::class.java)
//        homeActivity.startActivity(intent)
    }
    /**
     * method do (move to add Group)
     */
    fun addGroups(){
        val intent = Intent(homeActivity, AddGroupActivity::class.java)
        homeActivity.startActivity(intent)
    }

    /**
     * method do (move to Groups and Contacts)
     */
    fun groupContacts(){
        val intent = Intent(homeActivity,GroupContactsActivity::class.java)
        homeActivity.startActivity(intent)
    }
    fun contacts(){
        val intent = Intent(homeActivity,ContactsActivity::class.java)
        homeActivity.startActivity(intent)
    }

    /**
     * method do (move to settings)
     */
    fun settings(){
        val intent = Intent(homeActivity,SettingsActivity::class.java)
        homeActivity.startActivity(intent)
    }

    /**
     * method open profile
     */
    fun profileActivity(){
        val intent = Intent(homeActivity,ProfileActivity::class.java)
        homeActivity.startActivity(intent)
    }
    /**
     * method open Groups
     */
    fun groupsActivity(){
        val intent = Intent(homeActivity,GroupContactsActivity::class.java)
        homeActivity.startActivity(intent)
    }
    /**
     * method Sign Out
     */
    fun signout(){
        firebaseHandler.logout()
        Toast.makeText(homeActivity, " logout Success ", Toast.LENGTH_LONG).show()
        val intent = Intent(homeActivity,StartActivity::class.java)
        homeActivity.startActivity(intent)
    }

    fun sync() {
        firebaseHandler.sync()

    }


}