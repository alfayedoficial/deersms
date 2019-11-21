package com.alialfayed.deersms.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alialfayed.deersms.R

class EditMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_message)

//        initComponent()

    }

    //    private fun initComponent() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
    override fun onBackPressed() {
        finish()
    }
}
