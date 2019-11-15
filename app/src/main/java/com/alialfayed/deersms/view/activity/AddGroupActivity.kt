package com.alialfayed.deersms.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.alialfayed.deersms.R
import com.alialfayed.deersms.viewmodel.AddGroupViewModel

class AddGroupActivity : AppCompatActivity() {

    private lateinit var addGroupViewModel:AddGroupViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_group)

        addGroupViewModel = ViewModelProviders.of(this).get(AddGroupViewModel::class.java)
        addGroupViewModel.setAddGroup(this)

//        initComponent()

    }



    override fun onBackPressed() {
        finish()
    }
}
