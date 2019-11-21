package com.alialfayed.deersms.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.alialfayed.deersms.R
import com.alialfayed.deersms.view.adapter.HomeAdabter
import com.alialfayed.deersms.view.fragment.CompletedFragment
import com.alialfayed.deersms.view.fragment.PendingFragment
import com.alialfayed.deersms.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        homeViewModel =
            ViewModelProviders.of(this, MyViewModelFactory(this)).get(HomeViewModel::class.java)


        setupViewPager(tabViewpager_Home)
        tabs!!.setupWithViewPager(tabViewpager_Home)
        //setting toolbar
        setSupportActionBar(toolbar)
        initComponent()

    }
    //setting menu in action bar

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun initComponent() {
        imageBtnAddMessage.setOnClickListener(this)
        imageBtnAddGroup.setOnClickListener(this)
        imageBtnGroupContacts.setOnClickListener(this)
        imageBtnSettings.setOnClickListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item?.itemId
        // select item
        when (id) {
            R.id.profile_Menu -> {
                homeViewModel.profileActivity()
            }
            R.id.sync_Menu -> {
                startActivity(Intent(this, HomeActivity::class.java))
            }
            R.id.rate_Menu -> {
            }
            R.id.signOut_Menu -> {
                homeViewModel.signout()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = HomeAdabter(supportFragmentManager)
        adapter.addFragment(PendingFragment(), "Pending")
        adapter.addFragment(CompletedFragment(), "Completed")
        viewPager.adapter = adapter
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.imageBtnAddMessage -> {
                homeViewModel.addMessage()
            }
            R.id.imageBtnAddGroup -> {
                homeViewModel.addGroups()
            }
            R.id.imageBtnGroupContacts -> {
                homeViewModel.groupContacts()
//                homeViewModel.contacts()
            }
            R.id.imageBtnSettings -> {
                homeViewModel.settings()
            }
        }

    }

    internal class MyViewModelFactory(private val mActivity: Activity) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(mActivity) as T
        }
    }

    override fun onBackPressed() {
        finish()
    }


}
