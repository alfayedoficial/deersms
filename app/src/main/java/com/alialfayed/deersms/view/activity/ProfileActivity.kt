package com.alialfayed.deersms.view.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.alialfayed.deersms.R
import com.alialfayed.deersms.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    lateinit var profileViewModel: ProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        profileViewModel.setSettingsActivity(this)

        initComponent()

    }

    private fun initComponent() {
        edtEmail_Profile.setEnabled(false)
        edtEmail_Profile.setText(profileViewModel.getEmail())

        btnEdite_Profile.setOnClickListener {
            profileViewModel.displaySave()
        }

        btnSaveUpdate_Profile.setOnClickListener {
            val oldPassword = edtOldPassword_Profile.text.toString()
           val newPassword =  edtPassword_Profile.text.toString()
            val confirmassword = edtConfirmPassword_Profile.text.toString()
            if (!newPassword.isNullOrEmpty() && !confirmassword.isNullOrEmpty()){
                if(newPassword.equals(confirmassword)){
                    profileViewModel.changePassword(oldPassword , newPassword)
                }else{
                    Toast.makeText(this,"Password not Matched",Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this,"Fields must not be empty",Toast.LENGTH_LONG).show()

            }
            edtOldPassword_Profile.setText("")
            edtPassword_Profile.setText("")
            edtConfirmPassword_Profile.setText("")
        }
    }


    override fun onBackPressed() {
        finish()
    }
}
