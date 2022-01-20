package com.example.shoparty_android.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shoparty_android.R
import com.example.shoparty_android.adapter.CategoryAdapter1
import com.example.shoparty_android.adapter.HomeSeasonsAdapter
import com.example.shoparty_android.model.HomeCategoriesModel
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.seasons_recycler

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