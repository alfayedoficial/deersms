package com.alialfayed.deersms.view.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.alialfayed.deersms.R
import com.alialfayed.deersms.viewmodel.SignInViewModel
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var signInViewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        signInViewModel = ViewModelProviders.of(this).get(SignInViewModel::class.java)
        signInViewModel.setSignInActivity(this)

        initComponent()


    }

    private fun initComponent() {
        btnSignIn_SignIn.setOnClickListener(this)
        btnGoogle_SignIn.setOnClickListener(this)
        btnForgetPassword_SignIn.setOnClickListener(this)
        btnCreateAnAccount_SignIn.setOnClickListener(this) }

    /**
     * method check by Ids
     */
    override fun onClick(view: View?) {
        var email = edtEmail_SignIn.text.toString()
        var password = edtPassword_SignIn.text.toString()

        when (view?.id) {
            R.id.btnSignIn_SignIn -> {
                if (!TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                    signInViewModel.signInCheck(email, password)
                }
            }
            R.id.btnGoogle_SignIn -> {
            }
            R.id.btnForgetPassword_SignIn -> {
                signInViewModel.goForgetPassword()
            }
            R.id.btnCreateAnAccount_SignIn -> {
                signInViewModel.goCreatAnAcount()
            }
        }
    }

    fun disableLayout(status: Boolean) {
        edtEmail_SignIn.setEnabled(status)
        edtPassword_SignIn.setEnabled(status)
        btnSignIn_SignIn.setEnabled(status)
        btnGoogle_SignIn.setEnabled(status)
        btnForgetPassword_SignIn.setEnabled(status)
        btnCreateAnAccount_SignIn.setEnabled(status)
        if (!status) {
            progressBar_SignIn.setVisibility(View.VISIBLE)
        } else {
            progressBar_SignIn.setVisibility(View.GONE)
        }
    }


}
