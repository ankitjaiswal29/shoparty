package com.shoparty.android.ui.main.deals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.shoparty.android.R
import com.shoparty.android.ui.main.mainactivity.MainActivity
import com.shoparty.android.ui.main.categories.NewArrivalItemLIstAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dashboard_toolbar.view.*

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


        (activity as MainActivity).info_tools.tv_title.visibility=View.INVISIBLE
        (activity as MainActivity).info_tools.home_shoparty_icon.visibility=View.INVISIBLE
        (activity as MainActivity).info_tools.home_shoparty_icon2.visibility=View.VISIBLE

        (activity as MainActivity).info_tools.iv_bag_btn.visibility=View.VISIBLE
        (activity as MainActivity).info_tools.iv_btnsearch.visibility=View.VISIBLE

        fillDealsRecyclerView(dealsItemList)
        Deals()

       /* deals_search_btn.setOnClickListener {
            val intent = Intent (getActivity(), SearchActivity::class.java)
            getActivity()?.startActivity(intent)
        }
        deals_bag_btn.setOnClickListener {
            val intent = Intent (getActivity(), ShopingBagActivity::class.java)
            getActivity()?.startActivity(intent)
        }*/
    }

    private fun Deals() {
         val naItemList = listOf<TopSellingHomeModel>(
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),
            TopSellingHomeModel("Princess Dress","$10.2"),

            )

             val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        deals_item_recycler.apply {
                layoutManager = gridLayoutManager
                setHasFixedSize(true)
                isFocusable = false
                adapter = DealsAdapter(naItemList)
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