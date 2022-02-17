package com.shoparty.android.ui.activities.privacypolicy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import androidx.databinding.DataBindingUtil

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityPrivacyPolicyBinding
import com.shoparty.android.models.custommodel.PrivacyPolicyModel
import com.shoparty.android.ui.adapters.PrivacyPolicyAdapter


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
        binding.infoTool.back.setOnClickListener(this)
        binding.infoTool.tvTitle.setText("PrivacyPolicy")
        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.rv_privacyPolicyRecyclar)
        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<PrivacyPolicyModel>()
        data.add(PrivacyPolicyModel("Lorem Ipsum is simply dummy text of the","Lorem Ipsum is simply dummy text of printing typesetting industry. Lorem Ipsum industry's standard dummy text ever since the"))
        data.add(PrivacyPolicyModel("Lorem Ipsum is simply dummy text of the","Lorem Ipsum is simply dummy text of printing typesetting industry. Lorem Ipsum industry's standard dummy text ever since the"))
        data.add(PrivacyPolicyModel("Lorem Ipsum is simply dummy text of the","Lorem Ipsum is simply dummy text of printing typesetting industry. Lorem Ipsum industry's standard dummy text ever since the"))
        data.add(PrivacyPolicyModel("Lorem Ipsum is simply dummy text of the","Lorem Ipsum is simply dummy text of printing typesetting industry. Lorem Ipsum industry's standard dummy text ever since the"))
        data.add(PrivacyPolicyModel("Lorem Ipsum is simply dummy text of the","Lorem Ipsum is simply dummy text of printing typesetting industry. Lorem Ipsum industry's standard dummy text ever since the"))


        val adapter = PrivacyPolicyAdapter(data,)
        recyclerview.adapter = adapter

        //------------------------------------------------------------------------

       /* val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val width = metrics.widthPixels
       binding.expendableList.setIndicatorBounds(width - GetPixelFromDips(50f), width - GetPixelFromDips(10f));


        if (binding.expendableList != null) {
            val listData = data
            titleList = ArrayList(listData.keys)
            adapter = CustomExpandableListAdapter(this, titleList as ArrayList<String>, listData)
            binding.expendableList!!.setAdapter(adapter)
            binding.expendableList!!.setOnGroupExpandListener { groupPosition ->

               *//* Toast.makeText(
                    applicationContext,
                    (titleList as ArrayList<String>)[groupPosition] + " List Expanded.",
                    Toast.LENGTH_SHORT
                ).show()*//*
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
*/


    }

    fun GetPixelFromDips(pixels: Float): Int {
        // Get the screen's density scale
        val scale = resources.displayMetrics.density
        // Convert the dps to pixels, based on density scale
        return (pixels * scale + 100f).toInt()
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.back -> {
                onBackPressed()
            }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}