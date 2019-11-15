package com.alialfayed.deersms.view.activity

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.alialfayed.deersms.R
import com.alialfayed.deersms.viewmodel.ForgetPasswodViewModel
import com.alialfayed.deersms.viewmodel.SignInViewModel
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.activity_sign_in.*

class ForgetPasswordActivity : AppCompatActivity() {

     lateinit var forgetPasswodViewModel: ForgetPasswodViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        
        forgetPasswodViewModel = ViewModelProviders.of(this).get(ForgetPasswodViewModel::class.java)
        forgetPasswodViewModel.setForgetPasswordInActivity(this)


        btnForgetPassword.setOnClickListener {
            var email = edtEmail_ForgetPassword.text.toString().trim()

            if (email.isEmpty()) {
                edtEmail_ForgetPassword.error = "Email Required!\nPlease, Type your Email!"
                edtEmail_ForgetPassword.requestFocus()

            }else if (!isNetworkConnected()) {
                AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Your Data transfer and Wifi connection closed!\nOpen Internet Connection and try again!")
                    .setIcon(R.drawable.ic_cancel)
                    .setPositiveButton("Ok") { _, _ -> }
                    .show()
            }else{
                forgetPasswodViewModel.forgetPassword(email)
            }
        }


    }

    //    private fun initComponent() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
    override fun onBackPressed() {
        finish()
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
    }



}
