package com.shoparty.android.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.shoparty.android.R
import com.shoparty.android.models.custommodel.TopSellingHomeModel
import com.shoparty.android.ui.activities.CustomizeActivity
import com.shoparty.android.ui.adapters.TopSellingHomeAdapter
import kotlinx.android.synthetic.main.fragment_new_arrival_item_details.*

class NewArrivalItemDetailsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_arrival_item_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillTopSellingRecyclerView(topSellingItemList)


        na_details_customize_btn.setOnClickListener {
            val intent = Intent(requireActivity(), CustomizeActivity::class.java)
            startActivity(intent)
        }
    }

    private val topSellingItemList = listOf<TopSellingHomeModel>(
        TopSellingHomeModel("Princess Dress","$10.2"),
        TopSellingHomeModel("Princess Dress","$10.2"),
        TopSellingHomeModel("Princess Dress","$10.2"),
    )

    private fun fillTopSellingRecyclerView(teachers: List<TopSellingHomeModel>) {
        na_details_you_may_like_recycler.adapter = TopSellingHomeAdapter(topSellingItemList)
        na_details_customer_also_bought_recycler.adapter = TopSellingHomeAdapter(topSellingItemList)

    }
}