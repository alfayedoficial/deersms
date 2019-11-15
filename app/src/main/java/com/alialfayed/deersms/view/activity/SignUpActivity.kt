package com.alialfayed.deersms.view.activity

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.alialfayed.deersms.R
import com.alialfayed.deersms.viewmodel.SignUpViewModel
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    /**
     * six methods
     * onCreate , durationOfWait , onClick , onBackPressed , initComponent , isNetworkConnected
     * @author Ali Al Fayed
     */

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
        var email = edtEmail_SignUp.text.toString().trim()
        var password = edtPassword_SignUp.text.toString().trim()
        var confirmPassword = edtCofirmPassword_SignUp.text.toString().trim()

        when (view?.id) {
            R.id.btnSignUp_SignUp -> {

                if (email.isEmpty()){
                    edtEmail_SignUp.error = "Email Required!\nPlease, Type your Email!"
                    edtEmail_SignUp.requestFocus()

                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    edtEmail_SignUp.error = "Valid Email Required!\nlike: example@example.com"
                    edtEmail_SignUp.requestFocus()

                } else if (password.isEmpty()){
                    edtPassword_SignUp.error = "Password Required!\nPlease, Type your Password!"
                    edtPassword_SignUp.requestFocus()

                } else if (password.length < 6){
                    edtPassword_SignUp.error = "Password should be at least 6 characters long!"
                    edtPassword_SignUp.requestFocus()

                } else if (confirmPassword.isEmpty()){
                    edtCofirmPassword_SignUp.error = "Confirm Password Required!\nPlease, Confirm your Password!"
                    edtCofirmPassword_SignUp.requestFocus()

                } else if (password != confirmPassword){
                    AlertDialog.Builder(this@SignUpActivity)
                        .setTitle("Error")
                        .setMessage("Two passwords incompatible!")
                        .setIcon(R.drawable.ic_cancel)
                        .setPositiveButton("Ok") { _, _ ->  }
                        .show()

                } else if (!isNetworkConnected()){
                    AlertDialog.Builder(this@SignUpActivity)
                        .setTitle("Error")
                        .setMessage("Your Data transfer and Wifi connection closed!\nOpen Internet Connection and try again!")
                        .setIcon(R.drawable.ic_cancel)
                        .setPositiveButton("Ok") { _, _ ->  }
                        .show()
                } else {
                    signUpViewModel.signUpCheck(email, password)
                }


                /**
                 * another handel form
                 */

//                if (!email.isNullOrEmpty() && !password.isNullOrEmpty() && !confirmPassword.isNullOrEmpty()) {
//                    if (password.equals(confirmPassword)) {
//                        signUpViewModel.signUpCheck(email, password)
//                    } else {
//                        FancyToast.makeText(
//                            this, getString(R.string.message_confirm_error),
//                            FancyToast.DEFAULT, FancyToast.ERROR, false
//                        )
//                    }
//                } else {
//                    FancyToast.makeText(
//                        this, getString(R.string.message_empty_error),
//                        FancyToast.DEFAULT, FancyToast.ERROR, false
//                    )
//                }
            }
            R.id.btn_Terms_And_Conditions -> {
                signUpViewModel.termsAndConditions()
            }
            R.id.btn_Privacy_Policy -> {
                signUpViewModel.termsAndConditions()
            }

        }
    }

    fun disableLayout(status: Boolean) {
        edtEmail_SignUp.setEnabled(status)
        edtPassword_SignUp.setEnabled(status)
        edtCofirmPassword_SignUp.setEnabled(status)
        btnSignUp_SignUp.setEnabled(status)
        btn_Terms_And_Conditions.setEnabled(status)
        btn_Privacy_Policy.setEnabled(status)

    }

    override fun onBackPressed() {
        finish()
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
    }

}
