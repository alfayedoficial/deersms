package com.alialfayed.deersms.view.activity

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.alialfayed.deersms.R
import com.alialfayed.deersms.view.adapter.GroupContactsAdabter
import com.alialfayed.deersms.view.adapter.HomeAdabter
import com.alialfayed.deersms.view.fragment.CompletedFragment
import com.alialfayed.deersms.view.fragment.ContactsFragment
import com.alialfayed.deersms.view.fragment.GroupsFragment
import com.alialfayed.deersms.view.fragment.PendingFragment
import com.alialfayed.deersms.viewmodel.GroupContactsViewModel
import kotlinx.android.synthetic.main.activity_group_contacts.*
import kotlinx.android.synthetic.main.activity_home.*

class GroupContactsActivity : AppCompatActivity() {

    private lateinit var groupContactsViewModel: GroupContactsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_contacts)
        groupContactsViewModel = ViewModelProviders.of(this, MyViewModelFactory(this))
            .get(GroupContactsViewModel::class.java)

//        add Viewpager and Tabs
        setupViewPager(tabViewpager_GroupsContacts)
        tabs_GroupContacts!!.setupWithViewPager(tabViewpager_GroupsContacts)

        initComponent()

    }

    private fun initComponent() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = GroupContactsAdabter(supportFragmentManager)
        adapter.addFragment(GroupsFragment(), "Groups")
        adapter.addFragment(ContactsFragment(), "Contacts")
        viewPager.adapter = adapter
    }

    internal class MyViewModelFactory(private val groupContactsActivity: Activity) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return GroupContactsViewModel(groupContactsActivity) as T
        }
    }
}
