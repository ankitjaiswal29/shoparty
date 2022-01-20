package com.example.shoparty_android.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.shoparty_android.R
import com.example.shoparty_android.adapter.CustomExpandableListAdapter
import com.example.shoparty_android.databinding.ActivityOrderSuccessfulyBinding
import com.example.shoparty_android.databinding.ActivityPrivacyPolicyBinding
import com.example.shoparty_android.utils.ExpandableListData.data
import android.util.DisplayMetrics




class PrivacyPolicyActivity : AppCompatActivity(), View.OnClickListener  {
    private lateinit var binding: ActivityPrivacyPolicyBinding
    private var expandableListView: ExpandableListView? = null
    private var adapter: ExpandableListAdapter? = null
    private var titleList: List<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_privacy_policy)
        initialise()

    }

    private fun initialise() {
        // binding.tvViewordertitle.setOnClickListener(this)
        // binding.btnSave.setOnClickListener(this)

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val width = metrics.widthPixels
       binding.expendableList.setIndicatorBounds(width - GetPixelFromDips(50f), width - GetPixelFromDips(10f));


        if (binding.expendableList != null) {
            val listData = data
            titleList = ArrayList(listData.keys)
            adapter = CustomExpandableListAdapter(this, titleList as ArrayList<String>, listData)
            binding.expendableList!!.setAdapter(adapter)
            binding.expendableList!!.setOnGroupExpandListener { groupPosition ->

               /* Toast.makeText(
                    applicationContext,
                    (titleList as ArrayList<String>)[groupPosition] + " List Expanded.",
                    Toast.LENGTH_SHORT
                ).show()*/
            }
            binding.expendableList!!.setOnGroupCollapseListener { groupPosition ->
                Toast.makeText(
                    applicationContext,
                    (titleList as ArrayList<String>)[groupPosition] + " List Collapsed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.expendableList!!.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
                Toast.makeText(
                    applicationContext,
                    "Clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " +listData[(
                            titleList as
                                    ArrayList<String>
                            )
                            [groupPosition]]!!.get(
                        childPosition
                    ),
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
        }



    }

    fun GetPixelFromDips(pixels: Float): Int {
        // Get the screen's density scale
        val scale = resources.displayMetrics.density
        // Convert the dps to pixels, based on density scale
        return (pixels * scale + 100f).toInt()
    }
    override fun onClick(p0: View?) {

    }
}