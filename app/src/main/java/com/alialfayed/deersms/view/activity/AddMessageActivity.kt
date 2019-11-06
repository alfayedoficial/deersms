package com.alialfayed.deersms.view.activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProviders
import com.alialfayed.deersms.viewmodel.AddMessageViewModel
import kotlinx.android.synthetic.main.activity_add_message.*


class AddMessageActivity : AppCompatActivity() {

    private val requestSendSMS = 2
    private val requestReadState = 2

    lateinit var addMessageViewModel: AddMessageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.alialfayed.deersms.R.layout.activity_add_message)

        val numberText = edtNumber_AddMessage.text.toString().trim()
        val messageText = edtMessage_AddMessage.text.toString().trim()

        initComponent()
        addMessageViewModel = ViewModelProviders.of(this).get(AddMessageViewModel::class.java)
        addMessageViewModel.setAddMessage(this)

        imageBtnSend_AddMessage.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.SEND_SMS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.SEND_SMS),
                    requestSendSMS
                )
            } else if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_PHONE_STATE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_PHONE_STATE),
                    requestReadState
                )
            } else {
                addMessageViewModel.sendSMS()
            }

        }

        imageBtnContacts_AddMessage.setOnClickListener {
            addMessageViewModel.contacts()
        }

        imageBtnAttach_AddMessage.setOnClickListener {
            addMessageViewModel.template()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        val numberText = edtNumber_AddMessage.text.toString().trim()
        val messageText = edtMessage_AddMessage.text.toString().trim()
        if (requestCode == requestSendSMS && requestCode == requestReadState) {
            addMessageViewModel.sendSMS()
        }
    }

    private fun initComponent() {


        imageBtnContacts_AddMessage.setOnClickListener {
            //          val intent = Intent(Intent.ACTION_DEFAULT, ContactsContract.Contacts.CONTENT_URI)
//          startActivityForResult(intent, Constants.P)
        }
    }

    //TODO fix Result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == addMessageViewModel.resultTemplate && resultCode == Activity.RESULT_OK){
            val result1 = data!!.getStringExtra("result1")
            edtMessage_AddMessage.setText(result1)
        }
    }


}
