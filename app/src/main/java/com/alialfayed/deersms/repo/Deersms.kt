package com.alialfayed.deersms.repo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

/**
 * Class do :
 * Created by ( Eng Ali)
 */
class Deersms : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * Offline Firebase
         */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)}

}