package com.alialfayed.deersms.viewmodel

import android.app.Activity
import android.view.View
import androidx.lifecycle.ViewModel
import com.alialfayed.deersms.repo.FirebaseHandler
import com.alialfayed.deersms.view.activity.ProfileActivity
import kotlinx.android.synthetic.main.activity_profile.*

/**
 * Class do :
 * Created by ( Eng Ali)
 */
class ProfileViewModel : ViewModel(){
    lateinit var activity: Activity
    lateinit var profileActivity: ProfileActivity
    lateinit var firebaseHandler: FirebaseHandler

    fun setSettingsActivity(activity: Activity) {
        this.activity = activity
        this.profileActivity = activity as ProfileActivity
        firebaseHandler = FirebaseHandler(activity, this)
    }

   fun getEmail() :String?{

       val name = firebaseHandler.currentUser?.email
       return name
    }

    fun changePassword(oldPassword: String, newPassword: String) {
        firebaseHandler.changePassword(oldPassword,newPassword)
        displayUpdate()
    }

    fun displaySave(){
        activity.edtEmail_Profile.setVisibility(View.GONE)
        activity.btnEdite_Profile.setVisibility(View.GONE)
        activity.edtConfirmPassword_Profile.setVisibility(View.VISIBLE)
        activity.edtPassword_Profile.setVisibility(View.VISIBLE)
        activity.btnSaveUpdate_Profile.setVisibility(View.VISIBLE)
        activity.edtOldPassword_Profile.setVisibility(View.VISIBLE)
    }
    fun displayUpdate(){
        activity.edtEmail_Profile.setVisibility(View.VISIBLE)
        activity.btnEdite_Profile.setVisibility(View.VISIBLE)
        activity.edtConfirmPassword_Profile.setVisibility(View.GONE)
        activity.edtPassword_Profile.setVisibility(View.GONE)
        activity.btnSaveUpdate_Profile.setVisibility(View.GONE)
        activity.edtOldPassword_Profile.setVisibility(View.GONE)
    }
}