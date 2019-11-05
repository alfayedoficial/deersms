package com.alialfayed.deersms.view.activity

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
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
import com.emredavarci.floatingactionmenu.FloatingActionMenu
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.alialfayed.deersms.R.layout.activity_home)

        homeViewModel =
            ViewModelProviders.of(this, MyViewModelFactory(this)).get(HomeViewModel::class.java)


        setupViewPager(tabViewpager_Home)
        tabs!!.setupWithViewPager(tabViewpager_Home)
        //setting toolbar
        setSupportActionBar(toolbar)

        //floatingActionButton
        floatingActions()

        initComponent()

    }

    private fun initComponent() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    //setting menu in action bar

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item?.itemId
        // select item
        when (id) {
            R.id.profile_Menu -> {
                Toast.makeText(this, "profile", Toast.LENGTH_LONG).show()
            }
            R.id.group_Menu -> {
                Toast.makeText(this, "groups", Toast.LENGTH_LONG).show()
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

    private fun floatingActions() {
        val menu: FloatingActionMenu = floatingMenu

        menu.setClickEvent { index ->
            //            Log.d("TAG", index.toString()) // index of clicked menu item
            when (index) {
                0 -> { homeViewModel.addMessage() }
                1 -> { homeViewModel.addGroups() }
                2 -> { homeViewModel.groupContacts() }
                3 -> { homeViewModel.settings() }
            }
        }


    }

    internal class MyViewModelFactory(private val mActivity: Activity) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(mActivity) as T
        }
    }

}
