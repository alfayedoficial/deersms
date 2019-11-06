package com.alialfayed.deersms.repo

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alialfayed.deersms.db.TemplateDao
import com.alialfayed.deersms.db.TemplateDatabase
import com.alialfayed.deersms.model.Template

/**
 * Class do :
 * Created by ( Eng Ali)
 */
class RepoTemplate (context: Context) {
    lateinit var templateDao: TemplateDao
     var allTemplate : LiveData<List<Template>> = MutableLiveData()

    init {
        var templateDatabase:TemplateDatabase = TemplateDatabase.getInstance(context)
        templateDao = templateDatabase.getDaoInstance()
        allTemplate = templateDao.getTemplate()
    }

    fun getAllTemplates():LiveData<List<Template>>{
        allTemplate = templateDao.getTemplate()
        return  allTemplate
    }

    fun addTemplate(template: Template){
        class AddTemplate: AsyncTask<Unit,Unit,Unit>(){
            override fun doInBackground(vararg p0: Unit?) {
                templateDao.addTemplate(template)
            }
        }
        var addtemplate = AddTemplate()
        addtemplate.execute()
    }
}