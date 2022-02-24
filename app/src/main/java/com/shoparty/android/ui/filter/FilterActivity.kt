package com.shoparty.android.ui.filter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityFilterBinding
import com.shoparty.android.databinding.ActivityTopSellingBinding


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

        binding.rvList.layoutManager = LinearLayoutManager(this)

        // attach adapter to the recycler view
        rvAdapter = FilterAdapter(languageList)
        binding.rvList.adapter = rvAdapter

        val language1 = FilterModel(
            "Java",
            "Java is an Object Oriented Programming language." +
                    " Java is used in all kind of applications like Mobile Applications (Android is Java based), " +
                    "desktop applications, web applications, client server applications, enterprise applications and many more. ",
            false
        )
        val language2 = FilterModel(
            "Kotlin",
            "Kotlin is a statically typed, general-purpose programming language" +
                    " developed by JetBrains, that has built world-class IDEs like IntelliJ IDEA, PhpStorm, Appcode, etc.",
            false
        )
        val language3 = FilterModel(
            "Python",
            "Python is a high-level, general-purpose and a very popular programming language." +
                    " Python programming language (latest Python 3) is being used in web development, Machine Learning applications, " +
                    "along with all cutting edge technology in Software Industry.",
            false
        )
        val language4 = FilterModel(
            "CPP",
            "C++ is a general purpose programming language and widely used now a days for " +
                    "competitive programming. It has imperative, object-oriented and generic programming features. ",
            false
        )

        languageList.add(language1)
        languageList.add(language2)
        languageList.add(language3)
        languageList.add(language4)

        rvAdapter.notifyDataSetChanged()

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