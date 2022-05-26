package com.shoparty.android.ui.main.drawer.drawer_sub_category

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.shoparty.android.R

import com.shoparty.android.databinding.ActivityDrawersubcategoryBinding
import com.shoparty.android.ui.main.drawer.drawer_main_category.DrawerResponse
import com.shoparty.android.utils.PrefManager


class DrawerSubCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDrawersubcategoryBinding
    private lateinit var drawerSubParentAdapter: DrawerSubParentAdapter

    private var list: ArrayList<DrawerResponse.Category.ChildCategory.ChildCategoryX> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_drawersubcategory)
        if(PrefManager.read(PrefManager.LANGUAGEID, 1)==2){
            binding.mainDrawer.layoutDirection = View.LAYOUT_DIRECTION_RTL
            binding.infoTool.ivDrawerBack.rotation = 0F
        }else {
            binding.mainDrawer.layoutDirection = View.LAYOUT_DIRECTION_LTR
            binding.infoTool.ivDrawerBack.rotation = 180F
        }

        initialise()
        binding.infoTool.tvTitle.text = intent.extras?.getString("categoryName")
        list =
            intent?.getParcelableArrayListExtra<DrawerResponse.Category.ChildCategory.ChildCategoryX>("category") as ArrayList<DrawerResponse.Category.ChildCategory.ChildCategoryX>

        if(list.isNullOrEmpty())
        {
            binding.linearNoData.visibility= View.VISIBLE
            binding.categoryListRv.visibility= View.GONE
        }
        else
        {
            binding.linearNoData.visibility= View.GONE
            binding.categoryListRv.visibility= View.VISIBLE
            drawerListing()
        }
    }

    private fun initialise() {
        binding.infoTool.tvTitle.text = getString(R.string.ballons)

        binding.infoTool.ivDrawerBack.setOnClickListener {
            finish()
        }
    }

    private fun drawerListing()
    {
        drawerSubParentAdapter = DrawerSubParentAdapter(this, list)
        val gridLayoutManager = GridLayoutManager(this, 1)
        binding.categoryListRv.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = drawerSubParentAdapter
        }
    }
}