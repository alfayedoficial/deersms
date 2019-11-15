package com.alialfayed.deersms.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.alialfayed.deersms.view.activity.FQRActivity
import com.alialfayed.deersms.view.activity.SettingsActivity
import java.util.*

/**
 * Class do :
 * Created by ( Eng Ali)
 */
class SettingsViewModel : ViewModel() {
    lateinit var activity: Activity
    lateinit var settingsActivity: SettingsActivity

    fun setSettingsActivity(activity: Activity){
        this.activity = activity
        this.settingsActivity = activity as SettingsActivity
    }

    @SuppressLint("WrongConstant")
    fun setLocale(lang : String){
        var locale  = Locale(lang)
        Locale.setDefault(locale)
        var config : Configuration = Configuration()
        config.locale = locale
        settingsActivity.baseContext.resources.updateConfiguration(config ,settingsActivity.baseContext.resources.displayMetrics)

        var editor = settingsActivity.getSharedPreferences("settings", Context.MODE_PRIVATE).edit()
        editor.putString("MY_LANG",lang)
        editor.apply()
    }
    fun loadLocale(){
        val sharedPreferences = settingsActivity.getSharedPreferences("settings" , Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("MY_LANG","")
        setLocale(language as String)

    }

    fun goToFQRActivity(){
        val intentfqr = Intent(activity, FQRActivity::class.java)
        activity.startActivity(intentfqr)
    }
    fun WhatsappSupport(){
        val number = "+201014775215"
        val message = ""
        var intent = Intent(Intent.ACTION_VIEW);
        intent.data = Uri.parse("http://api.whatsapp.com/send?phone=$number&text=$message");
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(intent)
    }


}