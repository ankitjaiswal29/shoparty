package com.shoparty.android.ui.main.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.shoparty.android.R
import com.shoparty.android.databinding.FragmentCategoriesBinding
import com.shoparty.android.ui.main.mainactivity.MainActivity
import com.shoparty.android.ui.main.home.HomeCategoriesModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dashboard_toolbar.view.*

class CategoriesFragment : Fragment() {
    lateinit var binding: FragmentCategoriesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_categories, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialise()
    }

    private fun initialise() {

        (activity as MainActivity).info_tools.tv_title.visibility=View.INVISIBLE
        (activity as MainActivity).info_tools.home_shoparty_icon.visibility=View.INVISIBLE
        (activity as MainActivity).info_tools.home_shoparty_icon2.visibility=View.VISIBLE

        (activity as MainActivity).info_tools.ivBagBtn.visibility=View.VISIBLE
        (activity as MainActivity).info_tools.iv_btnsearch.visibility=View.VISIBLE


        val categoryItemList = listOf<HomeCategoriesModel>(
            HomeCategoriesModel("Costumes"),
            HomeCategoriesModel("Themes"),
            HomeCategoriesModel("Party Supply"),
            HomeCategoriesModel("Ballons"),
            HomeCategoriesModel("Birthday Cake"),
            HomeCategoriesModel("Toys"),
            HomeCategoriesModel("Candles"),
             HomeCategoriesModel("Costumes"),
             HomeCategoriesModel("Themes"),
             HomeCategoriesModel("Party Supply"),
             HomeCategoriesModel("Ballons")

            )
            binding.categoryListRecycler.adapter = CategoryAdapter1(categoryItemList)
    }
}