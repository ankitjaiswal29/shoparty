package com.shoparty.android.ui.main.wishlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager

import com.shoparty.android.R
import com.shoparty.android.databinding.FragmentWishListBinding
import com.shoparty.android.ui.main.mainactivity.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dashboard_toolbar.view.*

class WishListFragment : Fragment() {

    private lateinit var binding: FragmentWishListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      //  val root =  inflater.inflate(R.layout.fragment_wish_list, container, false)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_wish_list, container, false
        )

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).info_tools.tv_title.visibility=View.INVISIBLE
        (activity as MainActivity).info_tools.home_shoparty_icon.visibility=View.INVISIBLE
        (activity as MainActivity).info_tools.home_shoparty_icon2.visibility=View.VISIBLE

        (activity as MainActivity).info_tools.ivBagBtn.visibility=View.VISIBLE
        (activity as MainActivity).info_tools.iv_btnsearch.visibility=View.VISIBLE

        binding.wishlistRecyclerview.layoutManager = LinearLayoutManager(requireContext())


        /*deals_bag_btn.setOnClickListener {
            val intent = Intent (getActivity(), ShopingBagActivity::class.java)
            getActivity()?.startActivity(intent)
        }*/

        // ArrayList of class ItemsViewModel
        val data = ArrayList<String>()
        data.add("Princess Dress")
        data.add("Princess Dress")
        data.add("Princess Dress")
        data.add("Princess Dress")
        data.add("Princess Dress")
        data.add("Princess Dress")
        data.add("Princess Dress")
        data.add("Princess Dress")
        data.add("Princess Dress")
        data.add("Princess Dress")
       // binding.wishlistRecyclerview.adapter = WishListAdapter(data)
        val adapter = WishListAdapter(data)
        binding.wishlistRecyclerview.adapter = adapter
    }

}