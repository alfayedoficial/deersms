package com.alialfayed.deersms.viewmodel

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.alialfayed.deersms.R
import com.alialfayed.deersms.view.activity.SignInActivity
import com.alialfayed.deersms.view.activity.SignUpActivity
import com.alialfayed.deersms.view.activity.StartActivity
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.activity_start.*

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
        val animation =
            android.util.Pair<View, String>(startActivity.imageView2,startActivity.getString(R.string.logo_transition))
        val option = ActivityOptions.makeSceneTransitionAnimation(startActivity, animation)
        val startSignIn = Intent(startActivity,SignInActivity::class.java)
        startActivity.startActivity(startSignIn ,option.toBundle())
    }
    fun goSignUpActivity(){
        val animation =
            android.util.Pair<View, String>(startActivity.imageView2,startActivity.getString(R.string.logo_transition))
        val option = ActivityOptions.makeSceneTransitionAnimation(startActivity, animation)
        val startSignUp = Intent(startActivity,SignUpActivity::class.java)
        startActivity.startActivity(startSignUp , option.toBundle())
    }
}