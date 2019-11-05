package com.alialfayed.deersms.repo

import android.app.Activity
import android.util.Log
import com.alialfayed.deersms.viewmodel.SignInViewModel
import com.alialfayed.deersms.viewmodel.SignUpViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * Class do :
 * Created by ( Eng Ali)
 */
class FirebaseHandler(activity: Activity) {

    var mAuth: FirebaseAuth
    var signInViewModel: SignInViewModel? = null
    var signUpViewModel: SignUpViewModel? = null
    var currentUser: FirebaseUser? = null

    var activity: Activity? = null

    init {
        this.activity = activity
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth.currentUser
    }

    constructor(activity: Activity, signInViewModel: SignInViewModel) : this(activity) {
        this.signInViewModel = signInViewModel
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth.currentUser
    }

    constructor(activity: Activity, signUpViewModel: SignUpViewModel) : this(activity) {
        this.signUpViewModel = signUpViewModel
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth.currentUser
    }


    fun signUp(email: String, password: String) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
            OnCompleteListener {
                if (it.isSuccessful) {
                    Log.i("signUp", "Success " + email)
                    signUpViewModel?.SignUpSuccessful()
                } else {
                    Log.i("signUp", "Failed")
                    signUpViewModel?.SignUpfailed()
                }
            })
    }

    fun signIn(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(OnCompleteListener {
            if (it.isSuccessful) {
                Log.i("signIn", "Success" + email)
                signInViewModel?.SignInSuccessful()
            } else {
                Log.i("signIn", "Fail")
                signInViewModel?.SignInfailed()
            }
        })
    }
}





