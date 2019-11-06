package com.alialfayed.livedate.viewmodel

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alialfayed.deersms.model.Template
import com.alialfayed.deersms.repo.RepoTemplate


/**
 * Class do :
 * Created by (Eng Ali)
 */
class TemplatesViewModel(mActivity: Activity):ViewModel() {

    var repoTemplate: RepoTemplate = RepoTemplate(mActivity)
    fun insertTemplate(template : Template){
        repoTemplate.addTemplate(template)
    }
    fun getTemplates(): LiveData<List<Template>> {
        return repoTemplate.getAllTemplates()
    }

}