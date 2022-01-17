package com.example.shoparty_android.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shoparty_android.R
import com.example.shoparty_android.adapter.TopSellingItemAdapter
import com.example.shoparty_android.model.TopSellingHomeModel
import kotlinx.android.synthetic.main.activity_top_selling_item.*
import kotlinx.android.synthetic.main.fragment_top_selling_item_list.*

class TopSellingItemListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_selling_item_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillTopSellingItemRecyclerView(topSellingItemList)

    }
    private val topSellingItemList = listOf<TopSellingHomeModel>(
        TopSellingHomeModel("Princess Dress","$10.2"),
        TopSellingHomeModel("Princess Dress","$10.2"),
        TopSellingHomeModel("Princess Dress","$10.2"),
        TopSellingHomeModel("Princess Dress","$10.2"),
        TopSellingHomeModel("Princess Dress","$10.2"),
        TopSellingHomeModel("Princess Dress","$10.2"),

        )

    private fun fillTopSellingItemRecyclerView(teachers: List<TopSellingHomeModel>) {
        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        top_selling_item_recycler.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = TopSellingItemAdapter(topSellingItemList)
        }
    }
}