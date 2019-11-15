package com.alialfayed.deersms.viewmodel

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.alialfayed.deersms.repo.FirebaseHandler
import com.alialfayed.deersms.view.activity.SignInActivity
import com.alialfayed.deersms.view.activity.SignUpActivity
import kotlinx.android.synthetic.main.activity_sign_up.*

/**
 * Class do :
 * Created by ( Eng Ali)
 */
class SignUpViewModel:ViewModel() {
    lateinit var activity: Activity
    lateinit var signUpActivity: SignUpActivity

    var firebaseHandler:FirebaseHandler?=null

    fun setSignUpActivity(activity: Activity){
        this.activity=activity
        this.signUpActivity=activity as SignUpActivity
        firebaseHandler = FirebaseHandler(activity,this)
    }

    fun signUpCheck(email:String,password:String){
        if(!email.isNullOrEmpty() && !password.isNullOrEmpty()){
//            Toast.makeText(activity, "test done", Toast.LENGTH_LONG).show()
            signUpActivity.disableLayout(false)
            firebaseHandler?.signUp(email, password)
        }
    }
    fun SignUpSuccessful(){
        val start = Intent(activity, SignInActivity::class.java)
        activity.startActivity(start)
    }
    fun SignUpfailed(){
        signUpActivity.disableLayout(true)
    }

    /**
     * this method called when btn_Terms_And_Conditions Button in the Register activity, it takes you to website
     */
    fun termsAndConditions() {
        val url = "http://alialfayed.com/cv/Privacy_Policy.html"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        activity.startActivity(i)
    }


}