package com.alialfayed.deersms.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProviders
import com.alialfayed.deersms.R
import com.alialfayed.deersms.viewmodel.SettingsViewModel
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() , View.OnClickListener{

    lateinit var settingsViewModel: SettingsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        settingsViewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        settingsViewModel.setSettingsActivity(this)
        settingsViewModel.loadLocale()
        initComponent()



    }

    private fun initComponent(){
        btnArabic.setOnClickListener(this)
        btnEnglish.setOnClickListener(this)
        btnFQR.setOnClickListener(this)
        btnWhatsappSupport.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
       when(view?.id){
           R.id.btnArabic ->{
               settingsViewModel.setLocale("ar")
               finish()
               val intentrestart = Intent(this, SplashActivity::class.java)
               startActivity(intentrestart)
           }
           R.id.btnEnglish ->{
               settingsViewModel.setLocale("en")
               finish()
               val intentrestart = Intent(this, SplashActivity::class.java)
               startActivity(intentrestart)
           }
           R.id.btnFQR -> {
               settingsViewModel.goToFQRActivity()
           }
           R.id.btnWhatsappSupport -> {
               settingsViewModel.WhatsappSupport()
           }
       }
    }


    override fun onBackPressed() {
        finish()
    }
}
