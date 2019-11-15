package com.alialfayed.deersms.view.activity

import android.app.ActivityOptions
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.alialfayed.deersms.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    /**
     * three methods
     * onCreate , durationOfWait , checkPermission
     * @author Ali Al Fayed
     */

    private val SPLASH_DISPLAY_LENGTH = 2300
    private val requestReadState = 2
    private var flag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        /**
         * Check Permission
         */
        while (flag == 0) {
            if (checkPermission(android.Manifest.permission.SEND_SMS) && checkPermission(android.Manifest.permission.READ_CONTACTS) && checkPermission(android.Manifest.permission.READ_PHONE_STATE)) {
                flag = 1
                durationOfWait()
            } else {
                var
                        arrayPermission = arrayOf<String>(
                    android.Manifest.permission.SEND_SMS,
                    android.Manifest.permission.READ_CONTACTS,
                    android.Manifest.permission.READ_PHONE_STATE
                )
                ActivityCompat.requestPermissions(this, arrayPermission, requestReadState)
            }
        }

    }

    private fun durationOfWait() {
        Handler().postDelayed({
            /* Create an Intent that will start the Menu-Activity. */
            val mainIntent = Intent(this, StartActivity::class.java)
            val userInternt = Intent(this, HomeActivity::class.java)

            val animation =
                android.util.Pair<View, String>(imgLogo, getString(R.string.logo_transition))
            val option = ActivityOptions.makeSceneTransitionAnimation(this, animation)

            /**
             * Check currentUser
             */
            if (FirebaseAuth.getInstance().currentUser == null) {
                startActivity(mainIntent, option.toBundle())
            } else {
                startActivity(userInternt)
            }
            this.finish()
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }

    private fun checkPermission(permission: String): Boolean {
        val check: Int = ContextCompat.checkSelfPermission(this, permission)
        return (check == PackageManager.PERMISSION_GRANTED)
    }


}
