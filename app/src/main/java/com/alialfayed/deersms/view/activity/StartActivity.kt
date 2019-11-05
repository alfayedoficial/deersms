package com.alialfayed.deersms.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.alialfayed.deersms.R
import com.alialfayed.deersms.viewmodel.StartViewModel
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() , View.OnClickListener {


    lateinit var startViewModel: StartViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        startViewModel = ViewModelProviders.of(this).get(StartViewModel::class.java)
        startViewModel.setStartActivity(this)
        initComponent()

    }

    private fun initComponent() {
        btnSignIn_Start.setOnClickListener(this)
        btnSingUp_Start.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btnSignIn_Start -> { startViewModel.goSignInActivity()}
            R.id.btnSingUp_Start -> { startViewModel.goSignUpActivity()}
        }

    }
}
