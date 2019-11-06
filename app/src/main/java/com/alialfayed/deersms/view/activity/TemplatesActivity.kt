package com.alialfayed.deersms.view.activity

import android.annotation.SuppressLint
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alialfayed.deersms.R
import com.alialfayed.deersms.model.Template
import com.alialfayed.deersms.view.adapter.TemplatesAdabter
import com.alialfayed.livedate.viewmodel.TemplatesViewModel
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_templates.*
import java.util.ArrayList

class TemplatesActivity : AppCompatActivity() {

    private lateinit var adapter: TemplatesAdabter
    private lateinit var viewModel: TemplatesViewModel
    internal var templateListt = ArrayList<Template>()

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_templates)

        adapter = TemplatesAdabter(this)
        val layoutManager: LinearLayoutManager = LinearLayoutManager(this)
        txtAllTemplates.layoutManager = layoutManager
        txtAllTemplates.adapter = adapter

        viewModel = ViewModelProviders.of(this,MyViewModelFactory(this)).get(TemplatesViewModel::class.java)

        /**
         * Templates
         */

        val templateOne = Template()
        templateOne.templateText = "How Are You ?"
        viewModel.insertTemplate(templateOne)
        val templateTwo = Template()
        templateTwo.templateText = "I love You "
        viewModel.insertTemplate(templateTwo)
        val templateTree = Template()
        templateTree.templateText = "Good Morning"
        viewModel.insertTemplate(templateTree)
        val templateFour= Template()
        templateFour.templateText = "Happy Birthday"
        viewModel.insertTemplate(templateFour)
        val templateFive= Template()
        templateFive.templateText = "Call you later"
        viewModel.insertTemplate(templateFive)



//        template.templateText = "ya rab"
//        viewModel.insertTemplate(template)

        btnAddTemplates_Templates.setOnClickListener {

            var txtTemplate = edtAddTemplates_Templates.text.toString().trim()

            if(!txtTemplate.isNullOrEmpty()){
                val template = Template()
                template.templateText = txtTemplate
                viewModel.insertTemplate(template)
                edtAddTemplates_Templates.text.clear()
            }


        }

    }

    override fun onStart() {
        super.onStart()
        viewModel.getTemplates().observe(this,object :Observer<List<Template>>{
            override fun onChanged(t: List<Template>?) {
                if (t != null){
                    adapter.setAdapter(t)
                }
            }
        })
    }

    internal class MyViewModelFactory(private val mActivity: Activity) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TemplatesViewModel(mActivity) as T
        }
    }
}


