package com.alialfayed.deersms.viewmodel

import androidx.lifecycle.ViewModel
import com.alialfayed.deersms.repo.FirebaseHandler
import com.alialfayed.deersms.view.activity.CurrentSIMActivity

/**
 * Class do :
 * Created by ( Eng Ali)
 */
class CurrentSIMViewModel(val currentSIMActivity: CurrentSIMActivity):ViewModel() {
    private  var firebaseHandler: FirebaseHandler = FirebaseHandler(currentSIMActivity,this)
}