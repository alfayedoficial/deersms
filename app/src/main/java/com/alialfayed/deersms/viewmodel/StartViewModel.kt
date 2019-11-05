package com.alialfayed.deersms.viewmodel

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.alialfayed.deersms.view.activity.SignInActivity
import com.alialfayed.deersms.view.activity.SignUpActivity
import com.alialfayed.deersms.view.activity.StartActivity

/**
 * Class do :
 * Created by ( Eng Ali)
 */
class StartViewModel:ViewModel() {
    lateinit var startActivity: StartActivity
    fun setStartActivity(startActivity: Activity){
        this.startActivity = startActivity as StartActivity
    }

    fun goSignInActivity(){
        val startSignIn = Intent(startActivity,SignInActivity::class.java)
        startActivity.startActivity(startSignIn)
    }
    fun goSignUpActivity(){
        val startSignUp = Intent(startActivity,SignUpActivity::class.java)
        startActivity.startActivity(startSignUp)
    }
}