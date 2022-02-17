package com.shoparty.android.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager

import com.shoparty.android.R
import com.shoparty.android.databinding.FragmentWishListBinding
import com.shoparty.android.ui.activities.search.SearchActivity
import com.shoparty.android.ui.activities.shoppingbag.ShopingBagActivity
import com.shoparty.android.ui.adapters.WishListAdapter
import kotlinx.android.synthetic.main.fragment_deals.*

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

      //  binding.infoTool.tvTitle.setText(getString(R.string.wishlist))

        //binding.infoTool.ivBag.visibility=View.VISIBLE
        //binding.infoTool.ivSearch.visibility=View.VISIBLE
        binding.wishlistRecyclerview.layoutManager = LinearLayoutManager(requireContext())
      binding.dealsSearchBtn.setOnClickListener {
          val intent = Intent (getActivity(), SearchActivity::class.java)
          getActivity()?.startActivity(intent)

      }

        deals_bag_btn.setOnClickListener {
            val intent = Intent (getActivity(), ShopingBagActivity::class.java)
            getActivity()?.startActivity(intent)
        }

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