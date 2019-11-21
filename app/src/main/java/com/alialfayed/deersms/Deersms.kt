package com.alialfayed.deersms

import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

/**
 * Class do :
 * Created by ( Eng Ali)
 */
class Deersms : Application() {
    override fun onCreate() {
        super.onCreate()
        /**
         * Offline Firebase
         */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}