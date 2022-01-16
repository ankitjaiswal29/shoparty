package com.example.shoparty_android.view.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.shoparty_android.MainActivity
import com.example.shoparty_android.R
import com.example.shoparty_android.view.activity.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.navigation_drawer.*

class HomeFragment : Fragment() {

    private lateinit var drawerLayout: DrawerLayout
    private var productsBool: Boolean=false
    private var serviceBool: Boolean=false
    private var flowersBool: Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.navigation_drawer, container, false)

        drawerLayout = root.findViewById(R.id.drawer_layout)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        below_product_layout.visibility = View.GONE
        below_service_layout.visibility = View.GONE
        below_flowers_layout.visibility = View.GONE

        products_nav_lay.setOnClickListener {
            if (productsBool == false){
                below_product_layout.visibility = View.VISIBLE
                productsBool = true
            }else{
                below_product_layout.visibility = View.GONE
                productsBool = false
            }
        }

        services_nav_lay.setOnClickListener {
            if (serviceBool == false){
                below_service_layout.visibility = View.VISIBLE
                serviceBool = true
            }else{
                below_service_layout.visibility = View.GONE
                serviceBool = false
            }
        }

        flowers_nav_lay.setOnClickListener {
            if (flowersBool == false){
                below_flowers_layout.visibility = View.VISIBLE
                flowersBool = true
            }else{
                below_flowers_layout.visibility = View.GONE
                flowersBool = false
            }
        }

        nav_drawer_btn.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        cross_nav_btn.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        costume_nav_lay.setOnClickListener {
            val intent = Intent(requireActivity(), CostumesActivity::class.java)
            startActivity(intent)
        }

        themes_nav_lay.setOnClickListener {
            val intent = Intent(requireActivity(), ThemesActivity::class.java)
            startActivity(intent)
        }
        party_nav_lay.setOnClickListener {
            val intent = Intent(requireActivity(), PartySupplyActivity::class.java)
            startActivity(intent)
        }
        ballons_nav_lay.setOnClickListener {
            val intent = Intent(requireActivity(), BallonsActivity::class.java)
            startActivity(intent)
        }

        colors_nav_lay.setOnClickListener {
            val intent = Intent(requireActivity(), ColoursActivity::class.java)
            startActivity(intent)
        }
        seasons_nav_lay.setOnClickListener {
            val intent = Intent(requireActivity(), SeasonsActivity::class.java)
            startActivity(intent)
        }
        candles_nav_lay.setOnClickListener {
            val intent = Intent(requireActivity(), CandlesActivity::class.java)
            startActivity(intent)
        }
        signup_nav_lay.setOnClickListener {
            val builder = AlertDialog.Builder(requireActivity(), R.style.CustomAlertDialog)
            val inflater = layoutInflater
            val dialogLayout: View =
                inflater.inflate(R.layout.alert_dialog_signout, null)
            val btn_cancel = dialogLayout.findViewById<Button>(R.id.cancel_btn)
            val btn_save = dialogLayout.findViewById<Button>(R.id.save_btn)

            btn_cancel.setOnClickListener {

            }
            builder.setView(dialogLayout)
            builder.show()
        }

    }


}