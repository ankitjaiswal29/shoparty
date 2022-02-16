package com.example.shoparty_android.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoparty_android.R
import com.example.shoparty_android.adapter.CategoryAdapter1
import com.example.shoparty_android.adapter.WishListAdapter
import com.example.shoparty_android.databinding.ActivityWishListBinding
import com.example.shoparty_android.databinding.FragmentWishListBinding
import com.example.shoparty_android.view.activity.PrivacyPolicyActivity
import com.example.shoparty_android.view.activity.ReturnPolicyActivity
import com.example.shoparty_android.view.activity.SearchActivity
import com.example.shoparty_android.view.activity.ShopingBagActivity
import kotlinx.android.synthetic.main.fragment_categories.*
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