package com.example.shoparty_android.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shoparty_android.R
import com.example.shoparty_android.adapter.TopSellingHomeAdapter
import com.example.shoparty_android.adapter.TopSellingItemAdapter
import com.example.shoparty_android.model.TopSellingHomeModel
import kotlinx.android.synthetic.main.activity_top_selling_item.*
import kotlinx.android.synthetic.main.fragment_home.*

class TopSellingItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_selling_item)


    }


}