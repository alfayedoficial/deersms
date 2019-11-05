package com.alialfayed.deersms.view.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.alialfayed.deersms.R
import com.alialfayed.deersms.viewmodel.SignUpViewModel
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var signUpViewModel: SignUpViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // connect to view model
        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)
        signUpViewModel.setSignUpActivity(this)

        initComponent()

    }

    private fun initComponent() {
        btnSignUp_SignUp.setOnClickListener(this)
        btn_Terms_And_Conditions.setOnClickListener(this)
        btn_Privacy_Policy.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        var email = edtEmail_SignUp.text.toString()
        var password = edtPassword_SignUp.text.toString()
        var confirmPassword = edtCofirmPassword_SignUp.text.toString()

        when (view?.id) {
            R.id.btnSignUp_SignUp -> {
                if (!TextUtils.isEmpty(email) && TextUtils.isEmpty(password) && TextUtils.isEmpty(
                        confirmPassword
                    )
                ) {
                    if (password.equals(confirmPassword)) {
                        signUpViewModel.signUpCheck(email, password)
                    } else {
                        FancyToast.makeText(
                            this, getString(R.string.message_confirm_error),
                            FancyToast.DEFAULT, FancyToast.ERROR, false
                        )
                    }
                } else {
                    FancyToast.makeText(
                        this, getString(R.string.message_empty_error),
                        FancyToast.DEFAULT, FancyToast.ERROR, false
                    )
                }
            }
            R.id.btn_Terms_And_Conditions -> {
                signUpViewModel.termsAndConditions()
            }
            R.id.btn_Privacy_Policy -> {
                signUpViewModel.termsAndConditions()
            }

        }
    }

}
