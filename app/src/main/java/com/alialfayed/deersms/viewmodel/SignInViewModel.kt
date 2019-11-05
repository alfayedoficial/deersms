package com.alialfayed.deersms.viewmodel

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.alialfayed.deersms.R
import com.alialfayed.deersms.repo.FirebaseHandler
import com.alialfayed.deersms.view.activity.ForgetPasswordActivity
import com.alialfayed.deersms.view.activity.HomeActivity
import com.alialfayed.deersms.view.activity.SignInActivity
import com.alialfayed.deersms.view.activity.SignUpActivity
import com.shashank.sony.fancytoastlib.FancyToast


/**
 * Class do :
 * Created by ( Eng Ali)
 */

class SignInViewModel : ViewModel() {
    lateinit var activity: Activity
    lateinit var signInActivity: SignInActivity
    var firebaseHandler: FirebaseHandler? = null

    fun setSignInActivity(activity: Activity) {
        this.activity = activity
        this.signInActivity = activity as SignInActivity
        firebaseHandler = FirebaseHandler(activity, this)
    }

    /**
     * method check if editText is not null or empty
     */
    fun signInCheck(email: String, password: String) {
        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            signInActivity.disableLayout(false)
            firebaseHandler?.signIn(email, password)
            Toast.makeText(activity, "test done", Toast.LENGTH_LONG).show()
        } else {
            FancyToast.makeText(
                activity,
                activity.getString(R.string.message_empty_error),
                FancyToast.DEFAULT,
                FancyToast.ERROR,
                false
            )
            signInActivity.disableLayout(true)
        }
    }

    /**
     * method go to home Activity After check method on viewmodel
     */
    fun SignInSuccessful() {
        val start = Intent(activity, HomeActivity::class.java)
        activity.startActivity(start)
        activity.finish()

    }

    /**
     * method go to home Activity After check method on viewmodel
     */
    fun SignInfailed() {
        signInActivity.disableLayout(true)
    }

    /**
     * method go to SignUp Activity
     */
    fun goCreatAnAcount() {
        val intentAcount = Intent(activity, SignUpActivity::class.java)
        activity.startActivity(intentAcount)
    }

    /**
     * method go to ForgetPassword Activity
     */
    fun goForgetPassword() {
        val intentAcount = Intent(activity, ForgetPasswordActivity::class.java)
        activity.startActivity(intentAcount)
    }


}