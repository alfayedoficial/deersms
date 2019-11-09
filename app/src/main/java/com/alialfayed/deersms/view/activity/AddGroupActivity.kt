package com.alialfayed.deersms.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alialfayed.deersms.R

class AddGroupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_group)
//        initComponent()

    }

//    private fun initComponent() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }

    override fun onBackPressed() {
        finish()
    }
}
