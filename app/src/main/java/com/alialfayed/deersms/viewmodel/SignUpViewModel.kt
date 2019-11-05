package com.alialfayed.deersms.viewmodel

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.alialfayed.deersms.repo.FirebaseHandler
import com.alialfayed.deersms.view.activity.SignInActivity

/**
 * Class do :
 * Created by ( Eng Ali)
 */
class SignUpViewModel:ViewModel() {
    lateinit var activity: Activity

    var firebaseHandler:FirebaseHandler?=null

    fun setSignUpActivity(activity: Activity){
        this.activity=activity
        firebaseHandler = FirebaseHandler(activity,this)
    }

    fun signUpCheck(email:String,password:String){
        if(!email.isNullOrEmpty() && password.isNullOrEmpty()){
            firebaseHandler?.signUp(email, password)
        }
    }
    fun start(){
        val start = Intent(activity,SignInActivity::class.java)
        activity.startActivity(start)
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