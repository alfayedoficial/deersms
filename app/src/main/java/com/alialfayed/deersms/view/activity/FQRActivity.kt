package com.alialfayed.deersms.view.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.alialfayed.deersms.R
import kotlinx.android.synthetic.main.activity_fqr.*

class FQRActivity : AppCompatActivity() {


    var clickBoolean: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fqr)

        txtfqr1_FQRActivity.setOnClickListener {
            if (clickBoolean) {
                txtfqr1_FQRActivity2.setVisibility(View.VISIBLE)
                txtfqr1_FQRActivity.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                txtfqr2_FQRActivity2.setVisibility(View.GONE)
                txtfqr3_FQRActivity2.setVisibility(View.GONE)
                txtfqr4_FQRActivity2.setVisibility(View.GONE)
                clickBoolean = false
            } else {
                reset()
            }
        }
        txtfqr2_FQRActivity.setOnClickListener {
            if (clickBoolean) {
                txtfqr1_FQRActivity2.setVisibility(View.GONE)
                txtfqr2_FQRActivity2.setVisibility(View.VISIBLE)
                txtfqr2_FQRActivity.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                txtfqr3_FQRActivity2.setVisibility(View.GONE)
                txtfqr4_FQRActivity2.setVisibility(View.GONE)
                clickBoolean = false
            } else {
                reset()
            }
        }
        txtfqr3_FQRActivity.setOnClickListener {
            if (clickBoolean) {
                txtfqr1_FQRActivity2.setVisibility(View.GONE)
                txtfqr2_FQRActivity2.setVisibility(View.GONE)
                txtfqr3_FQRActivity2.setVisibility(View.VISIBLE)
                txtfqr3_FQRActivity.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                txtfqr4_FQRActivity2.setVisibility(View.GONE)
                clickBoolean = false
            } else {
                reset()
            }
        }
        txtfqr4_FQRActivity.setOnClickListener {
            if (clickBoolean) {
                txtfqr1_FQRActivity2.setVisibility(View.GONE)
                txtfqr2_FQRActivity2.setVisibility(View.GONE)
                txtfqr3_FQRActivity2.setVisibility(View.GONE)
                txtfqr4_FQRActivity2.setVisibility(View.VISIBLE)
                txtfqr4_FQRActivity.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                clickBoolean = false
            } else {
                reset()
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }

    fun reset(){
        txtfqr1_FQRActivity2.setVisibility(View.GONE)
        txtfqr2_FQRActivity2.setVisibility(View.GONE)
        txtfqr3_FQRActivity2.setVisibility(View.GONE)
        txtfqr4_FQRActivity2.setVisibility(View.GONE)
        txtfqr1_FQRActivity.setTextColor(ContextCompat.getColor(this, R.color.textcolor))
        txtfqr2_FQRActivity.setTextColor(ContextCompat.getColor(this, R.color.textcolor))
        txtfqr3_FQRActivity.setTextColor(ContextCompat.getColor(this, R.color.textcolor))
        txtfqr4_FQRActivity.setTextColor(ContextCompat.getColor(this, R.color.textcolor))
        clickBoolean = true

    }

}
