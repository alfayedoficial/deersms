package com.alialfayed.deersms.view.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.alialfayed.deersms.R
import com.alialfayed.deersms.viewmodel.SignInViewModel
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity(), View.OnClickListener  {

    /**
     * six methods
     * onCreate , durationOfWait , onClick , onBackPressed , initComponent , isNetworkConnected
     * @author Ali Al Fayed
     */

    companion object {
        private var permissionCode = 999
    }

    lateinit var mAuth: FirebaseAuth
    lateinit var mGoogleApiClient: GoogleApiClient
    lateinit var signInViewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        signInViewModel = ViewModelProviders.of(this).get(SignInViewModel::class.java)
        signInViewModel.setSignInActivity(this)

        configrationGoogleClient()
        mAuth = FirebaseAuth.getInstance()


        initComponent()

    }

    private fun initComponent() {
        btnSignIn_SignIn.setOnClickListener(this)
        btnGoogle_SignIn.setOnClickListener(this)
        btnForgetPassword_SignIn.setOnClickListener(this)
        btnCreateAnAccount_SignIn.setOnClickListener(this)
    }

    /**
     * method check by Ids
     */
    override fun onClick(view: View?) {
        var email = edtEmail_SignIn.text.toString().trim()
        var password = edtPassword_SignIn.text.toString().trim()

        when (view?.id) {
            R.id.btnSignIn_SignIn -> {

                if (email.isEmpty()){
                    edtEmail_SignIn.error = "Email Required!\nPlease, Type your Email!"
                    edtEmail_SignIn.requestFocus()

                } else if (password.isEmpty()){
                    edtPassword_SignIn.error = "Password Required!\nPlease, Type your Password!"
                    edtPassword_SignIn.requestFocus()

                } else if (!isNetworkConnected()){
                    AlertDialog.Builder(this@SignInActivity)
                        .setTitle("Error")
                        .setMessage("Your Data transfer and Wifi connection closed!\nOpen Internet Connection and try again!")
                        .setIcon(R.drawable.ic_cancel)
                        .setPositiveButton("Ok") { _, _ ->  }
                        .show()

                } else {
                    signInViewModel.signInCheck(email, password)
                }

            }
            R.id.btnGoogle_SignIn -> {
                signIn()
//                signInViewModel.signInWithGoogle()
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

    override fun onBackPressed() {
        finish()
    }

    fun setMsgAlert(msg: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(msg)
            .setIcon(R.drawable.ic_cancel)
            .setPositiveButton("Ok") { _, _ -> }
            .show()
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
    }

    /**
     * method Sign In With Google
     */
    private fun signIn() {
        val intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(intent, permissionCode)
    }
    private fun configrationGoogleClient() {
        var option = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, option)
            .build()

        mGoogleApiClient.connect() // Do not forget connect :D

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == permissionCode) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                val account = result.signInAccount
                val token = account!!.idToken
                val credential = GoogleAuthProvider.getCredential(token, null)
                firebaseAuthWithGoogle(credential)
            } else {
                Toast.makeText(this, "UnSuccessful logIn ", Toast.LENGTH_LONG).show()
            }

        }
    }
    private fun firebaseAuthWithGoogle(credential: AuthCredential?) {
        mAuth.signInWithCredential(credential!!)
            .addOnFailureListener {
                Toast.makeText(this, "Error :" + it.message, Toast.LENGTH_LONG).show()

            }.addOnSuccessListener {
             signInViewModel.SignInSuccessful()
            }

    }






}
