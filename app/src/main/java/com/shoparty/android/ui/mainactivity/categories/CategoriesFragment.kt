package com.shoparty.android.ui.mainactivity.categories

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shoparty.android.R
import com.shoparty.android.ui.mainactivity.home.HomeCategoriesModel
import com.shoparty.android.ui.search.SearchActivity
import com.shoparty.android.ui.shoppingbag.ShopingBagActivity

import kotlinx.android.synthetic.main.fragment_categories.*

class CategoriesFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillCategoryRecyclerView(categoryItemList)
        category_search_btn.setOnClickListener {
            val intent = Intent (getActivity(), SearchActivity::class.java)
            getActivity()?.startActivity(intent)
        }

        category_bag_btn.setOnClickListener {
            val intent = Intent (getActivity(), ShopingBagActivity::class.java)
            getActivity()?.startActivity(intent)
        }
    }

    private val categoryItemList = listOf<HomeCategoriesModel>(
        HomeCategoriesModel("Costumes"),
        HomeCategoriesModel("Themes"),
        HomeCategoriesModel("Party Supply"),
        HomeCategoriesModel("Ballons"),
        HomeCategoriesModel("Birthday Cake"),
        HomeCategoriesModel("Toys"),
        HomeCategoriesModel("Candles"),

        )

    private fun fillCategoryRecyclerView(seasons: List<HomeCategoriesModel>) {
        category_list_recycler.adapter = CategoryAdapter1(categoryItemList)
    }
}