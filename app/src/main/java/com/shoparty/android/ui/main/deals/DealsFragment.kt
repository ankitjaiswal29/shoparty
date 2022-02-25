package com.shoparty.android.ui.main.deals

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shoparty.android.R
import com.shoparty.android.databinding.FragmentDealsBinding
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.ui.filter.FilterActivity
import com.shoparty.android.ui.main.mainactivity.MainActivity
import com.shoparty.android.ui.main.categories.NewArrivalItemLIstAdapter
import com.shoparty.android.ui.main.topselling.TopSellingBottomSheetAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dashboard_toolbar.view.*

import kotlinx.android.synthetic.main.fragment_deals.*

class DealsFragment : Fragment(),View.OnClickListener, RecyclerViewClickListener {

    lateinit var binding: FragmentDealsBinding
    lateinit var dialog:BottomSheetDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
          binding= DataBindingUtil.inflate(inflater,R.layout.fragment_deals, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        (activity as MainActivity).info_tools.tv_title.visibility=View.INVISIBLE
        (activity as MainActivity).info_tools.home_shoparty_icon.visibility=View.INVISIBLE
        (activity as MainActivity).info_tools.home_shoparty_icon2.visibility=View.VISIBLE

        (activity as MainActivity).info_tools.ivBagBtn.visibility=View.VISIBLE
        (activity as MainActivity).info_tools.iv_btnsearch.visibility=View.VISIBLE

        fillDealsRecyclerView(dealsItemList)
        Deals()
        initilize()

       /* deals_search_btn.setOnClickListener {
            val intent = Intent (getActivity(), SearchActivity::class.java)
            getActivity()?.startActivity(intent)
        }
        deals_bag_btn.setOnClickListener {
            val intent = Intent (getActivity(), ShopingBagActivity::class.java)
            getActivity()?.startActivity(intent)
        }*/
    }

    private fun initilize() {
        binding.tvSort.setOnClickListener(this)
        binding.tvFilter.setOnClickListener(this)
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
        binding.dealsItemRecycler.apply {
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

    override fun onClick(v: View?) {
        when(v?.id) {
             R.id.tv_filter -> {
                 val intent = Intent(requireContext(), FilterActivity::class.java)
                 startActivity(intent)
             }
            R.id.tv_sort -> {
                showBottomsheetDialog()
            }
        }
    }
    private fun showBottomsheetDialog() {

        dialog = BottomSheetDialog(requireContext())

        // on below line we are inflating a layout file which we have created.
        val view = layoutInflater.inflate(R.layout.top_selling_bottomsheet_layout, null)

        // on below line we are creating a variable for our button
        // which we are using to dismiss our dialog.
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_top_selling_bottomsheetrecyclar)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val data = ArrayList<String>()
        data.add("Newest To Oldest")
        data.add("Oldest To Newest")
        data.add("Price - Low To High")
        data.add("Price - High To Low")
        val adapter= TopSellingBottomSheetAdapter(data,this)
        recyclerView.adapter=adapter

        /* btnClose.setOnClickListener {
            // on below line we are calling a dismiss
            // method to close our dialog.
            dialog.dismiss()
        }*/
        // below line is use to set cancelable to avoid
        // closing of dialog box when clicking on the screen.
        dialog.setCancelable(true)

        // on below line we are setting
        // content view to our view.
        dialog.setContentView(view)

        // on below line we are calling
        // a show method to display a dialog.
        dialog.show()


    }

    override fun click(pos: String) {
        Toast.makeText(requireContext(),pos, Toast.LENGTH_LONG).show()
        dialog.dismiss();
    }

}