package com.example.shoparty_android.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shoparty_android.R
import kotlinx.android.synthetic.main.activity_candles.*

class CandlesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candles)

        candles_back_arrow_btn.setOnClickListener {

        }
    }
}