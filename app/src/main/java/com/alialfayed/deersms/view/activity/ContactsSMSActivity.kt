package com.alialfayed.deersms.view.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.RawContacts
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alialfayed.deersms.model.Contacts
import com.alialfayed.deersms.view.adapter.ContactsActivityAdabter
import com.alialfayed.deersms.view.adapter.ContactsSMSActivityAdabter
import kotlinx.android.synthetic.main.activity_contacts.*


class ContactsSMSActivity : AppCompatActivity() {

    //    lateinit var splashActivity: SplashActivity
    val REQUEST_CONTACT = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.alialfayed.deersms.R.layout.activity_contacts_sms)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(Manifest.permission.READ_CONTACTS),
                REQUEST_CONTACT
            )
        } else {
            setContacts()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CONTACT) setContacts()
    }

    @SuppressLint("WrongConstant")
    private fun setContacts() {

        val contactsList: ArrayList<Contacts> = ArrayList()
//        val cursor = contentResolver.query(
//            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//            arrayOf(RawContacts.CONTACT_ID, RawContacts.DISPLAY_NAME_PRIMARY),
//            RawContacts.ACCOUNT_TYPE + "= ?",
//            arrayOf("com.whatsapp"),
//            null
//        )
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
           null,
            null,
            null,
            null
        )
        while (cursor!!.moveToNext()) {
            contactsList.add(
                Contacts(
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                )
            )
        }
        cursor.close()
        val adapter = ContactsSMSActivityAdabter(contactsList, this)
        recyclerViewtest.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        recyclerViewtest.adapter = adapter


    }

    override fun onBackPressed() {
        finish()
    }
}
