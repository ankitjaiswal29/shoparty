package com.shoparty.android.ui.ballons

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.iamkamrul.expandablerecyclerviewlist.listener.ExpandCollapseListener
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityBallonsBinding
import com.shoparty.android.ui.main.mainactivity.DrawerResponse


class BallonsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityBallonsBinding
    private var list: ArrayList<DrawerResponse.Category.ChildCategory> = ArrayList()
    private val adapter = CategoryAdapter(this, list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_ballons)
        initialise()

        val data = intent.extras?.getString("categoryName")
        binding.infoTool.tvTitle.text = data

        list =
            intent?.getParcelableArrayListExtra<DrawerResponse.Category.ChildCategory>("category") as ArrayList<DrawerResponse.Category.ChildCategory>
    }

    private fun initialise() {
        binding.infoTool.tvTitle.setText(getString(R.string.ballons))
        binding.infoTool.ivDrawerBack.setOnClickListener(this)

        val data = listOf(
            Category(
                "New Year",
                listOf(CategoryList("My Spy"), CategoryList("BloodShot"), CategoryList("Midway"))
            ),
            Category(
                "Valentines Day",
                listOf(CategoryList("The Godfather"), CategoryList("The Dark Knight"))
            ),
            Category(
                "Eid Fiter",
                listOf(CategoryList("Apocalypse Now"), CategoryList("Saving Private Ryan"))
            ),
            Category(
                "Eid Adha",
                listOf(CategoryList("My Spy"), CategoryList("BloodShot"), CategoryList("Midway"))
            ),
            Category(
                "Halloween",
                listOf(CategoryList("The Godfather"), CategoryList("The Dark Knight"))
            ),
            Category(
                "Mother Day",
                listOf(CategoryList("Apocalypse Now"), CategoryList("Saving Private Ryan"))
            )

        )


        binding.categoryListRv.setHasFixedSize(true)
        binding.categoryListRv.layoutManager = LinearLayoutManager(this)
        adapter.setExpandCollapseListener(object : ExpandCollapseListener {
            override fun onListItemExpanded(position: Int) {
                Toast.makeText(this@BallonsActivity, position.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onListItemCollapsed(position: Int) {
                Toast.makeText(this@BallonsActivity, position.toString(), Toast.LENGTH_LONG).show()

            }


        })

        binding.categoryListRv.adapter = adapter
        adapter.setExpandableParentItemList(data)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_drawer_back -> {
                onBackPressed()
            }

        }
    }
}