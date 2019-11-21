package com.alialfayed.deersms.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.alialfayed.deersms.viewmodel.CurrentSIMViewModel



class CurrentSIMActivity : AppCompatActivity() {
    private lateinit var currentSIMViewModel: CurrentSIMViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.alialfayed.deersms.R.layout.activity_current_sim)
        currentSIMViewModel = ViewModelProviders.of(this,MyViewModelFactory(this)).get(CurrentSIMViewModel::class.java)

    }

    internal class MyViewModelFactory(val currentSIMActivity: CurrentSIMActivity):ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CurrentSIMViewModel(currentSIMActivity) as T
        }

    }
}
