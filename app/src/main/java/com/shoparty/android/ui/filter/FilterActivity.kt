package com.shoparty.android.ui.filter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityFilterBinding
import com.shoparty.android.databinding.ActivityTopSellingBinding
import com.shoparty.android.ui.main.home.HomeCategoriesModel
import com.shoparty.android.ui.main.home.TopSellingSubcategoriesAdapter
import kotlinx.android.synthetic.main.fragment_home.*


class FilterActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding: ActivityFilterBinding
    private var languageList = ArrayList<FilterModel>()
    private lateinit var rvAdapter: FilterAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding= DataBindingUtil.setContentView(this, R.layout.activity_filter)
        initialise()
    }

    private fun initialise() {

       val data=ArrayList<String>()
        data.add("#FFBB86FC")
        data.add("#606060")
        data.add("#FFBB86FC")
        data.add("#FFBB86FC")
        data.add("#E30986")
        data.add("#FFBB86FC")
        data.add("#606060")
        data.add("#E30986")
        data.add("#FFBB86FC")
        data.add("#606060")


        val gridLayoutManager = GridLayoutManager(this, 9)
        binding.rvColorRecyclarview.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = FilterColorAdapter(data)
        }

      /*  binding.rvList.layoutManager = LinearLayoutManager(this)

        // attach adapter to the recycler view
        rvAdapter = FilterAdapter(languageList,this)
        binding.rvList.adapter = rvAdapter
val data=ArrayList<String>()
        data.add("dfd")
        data.add("dfd")
        data.add("dfd")
        data.add("dfd")
        data.add("dfd")

        val data2=ArrayList<String>()
        data2.add("rrr")
        data2.add("rrrr")
        data2.add("rrr")
        data2.add("rrr")
        data2.add("rrr")
        val data3=ArrayList<String>()
        data3.add("ggg")
        data3.add("ggg")
        data3.add("gggg")
        data3.add("gggg")
        data3.add("gggrr")

        val language1 = FilterModel(
            "Color",
            data,
            false
        )
        val language2 = FilterModel(
            "Size",
            data2,
            false
        )
        val language3 = FilterModel(
            "Age",
            data3,
            false
        )
        val language4 = FilterModel(
            "Gender",
            data2,
            false
        )
        val language5 = FilterModel(
            "Price",
            data,
            false
        )

        languageList.add(language1)
        languageList.add(language2)
        languageList.add(language3)
        languageList.add(language4)
        languageList.add(language5)

        rvAdapter.notifyDataSetChanged()
*/
        // create new objects
        // add some row data
        binding.infoTool.ivDrawerBack.setOnClickListener(this)



    }

    override fun onClick(v: View?) {
        when(v?.id) {
            /* R.id.btnCancel -> {
                 val intent = Intent(this, CancelOrderActivity::class.java)
                 intent.putExtra("key","Ongoeing")
                 startActivity(intent)
             }*/
            R.id.iv_drawer_back -> {
                onBackPressed()
            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }
}