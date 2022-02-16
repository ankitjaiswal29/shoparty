package com.example.shoparty_android.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shoparty_android.R
import com.example.shoparty_android.adapter.NewArrivalItemLIstAdapter
import com.example.shoparty_android.model.TopSellingHomeModel
import com.example.shoparty_android.view.activity.SearchActivity
import com.example.shoparty_android.view.activity.ShopingBagActivity
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.fragment_category_item_list.*
import kotlinx.android.synthetic.main.fragment_deals.*

class DealsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_deals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillDealsRecyclerView(dealsItemList)

        deals_search_btn.setOnClickListener {
            val intent = Intent (getActivity(), SearchActivity::class.java)
            getActivity()?.startActivity(intent)
        }
        deals_bag_btn.setOnClickListener {
            val intent = Intent (getActivity(), ShopingBagActivity::class.java)
            getActivity()?.startActivity(intent)
        }
    }

    private val dealsItemList = listOf<TopSellingHomeModel>(
        TopSellingHomeModel("Princess Dress", "$10.2"),
        TopSellingHomeModel("Princess Dress", "$10.2"),
        TopSellingHomeModel("Princess Dress", "$10.2"),
        TopSellingHomeModel("Princess Dress", "$10.2"),
        TopSellingHomeModel("Princess Dress", "$10.2"),
        TopSellingHomeModel("Princess Dress", "$10.2"),

        )

    private fun fillDealsRecyclerView(deals: List<TopSellingHomeModel>) {
        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        deals_item_recycler.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = NewArrivalItemLIstAdapter(dealsItemList)
        }

    }
}