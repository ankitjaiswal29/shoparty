package com.example.shoparty_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.shoparty_android.view.activity.AddCardActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_fragment)
        bottom_navigatin_view.setupWithNavController(navController)

        Handler().postDelayed({
            val intent = Intent(this, AddCardActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}