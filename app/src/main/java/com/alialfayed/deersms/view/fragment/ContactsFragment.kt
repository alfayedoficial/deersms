package com.alialfayed.deersms.view.fragment


import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.alialfayed.deersms.R
import com.alialfayed.deersms.model.Contacts
import com.alialfayed.deersms.view.activity.SplashActivity

/**
 * A simple [Fragment] subclass.
 */
class ContactsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



//        setContacts()

        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }






}
