package com.alialfayed.deersms.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.alialfayed.deersms.R
import kotlinx.android.synthetic.main.activity_choose_plan.*

class ChoosePlanActivity : AppCompatActivity() {

    private lateinit var radioGroup_choosePlan: RadioGroup
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_plan)

        radioGroup_choosePlan = findViewById(R.id.radioGroup_ChooseYourPlan)
        radioGroup_choosePlan.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.radioBtnCurrentSIM_ChooseYourPlan -> {
                    txtMoodCurrentSIM.setVisibility(View.VISIBLE)
                    txtMoodCurrentSIM.text = "Current SIM Plan"
                    txtMoodCurrentSIM_Details.setVisibility(View.VISIBLE)
                    txtMoodCurrentSIM_Details.text = "This plan take send SMS Permission and user can be send SMS to his the same current SIM"
                    // display
                    txtMoodWhatsApp.setVisibility(View.GONE)
                    txtMoodWhatsApp_Details.setVisibility(View.GONE)
                    txtMoodNoPlan.setVisibility(View.GONE)
                    txtMoodNoPlan_Details.setVisibility(View.GONE)
                }
                R.id.radioBtnWhatsApp_ChooseYourPlan -> {
                    txtMoodWhatsApp.setVisibility(View.VISIBLE)
                    txtMoodWhatsApp.text = "WhatsApp Plan"
                    txtMoodWhatsApp_Details.setVisibility(View.VISIBLE)
                    txtMoodWhatsApp_Details.text = "this plan take send WhatsApp messages automatic"
                    // display
                    txtMoodCurrentSIM.setVisibility(View.GONE)
                    txtMoodCurrentSIM_Details.setVisibility(View.GONE)
                    txtMoodNoPlan.setVisibility(View.GONE)
                    txtMoodNoPlan_Details.setVisibility(View.GONE)

                }
                R.id.radioBtnNoPlan_ChooseYourPlan -> {
                    txtMoodNoPlan.setVisibility(View.VISIBLE)
                    txtMoodNoPlan.text = "No Plan"
                    txtMoodNoPlan_Details.setVisibility(View.VISIBLE)
                    txtMoodNoPlan_Details.text = "this plan give user make custom plan"
                    // display
                    txtMoodWhatsApp.setVisibility(View.GONE)
                    txtMoodWhatsApp_Details.setVisibility(View.GONE)
                    txtMoodCurrentSIM.setVisibility(View.GONE)
                    txtMoodCurrentSIM_Details.setVisibility(View.GONE)
                }
            }
        }

        btnConfirm_ChooseYourPlan.setOnClickListener {
            if (radioBtnCurrentSIM_ChooseYourPlan.isChecked){
                startActivity(Intent(this,CurrentSIMActivity::class.java))
                finish()
            }else if(radioBtnWhatsApp_ChooseYourPlan.isChecked){
                startActivity(Intent(this,WhatsAppActivity::class.java))
                finish()
            }else if(radioBtnNoPlan_ChooseYourPlan.isChecked){
                startActivity(Intent(this,AddMessageActivity::class.java))
                finish()

            }
        }
    }
    override fun onBackPressed() {
        finish()
    }

}
