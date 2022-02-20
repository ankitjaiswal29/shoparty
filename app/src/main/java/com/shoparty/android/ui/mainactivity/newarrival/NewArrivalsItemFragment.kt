package com.shoparty.android.ui.mainactivity.newarrival

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.shoparty.android.R
import com.shoparty.android.ui.mainactivity.deals.TopSellingHomeModel
import com.shoparty.android.ui.filter.FilterActivity
import com.shoparty.android.ui.mainactivity.categories.NewArrivalItemLIstAdapter

import kotlinx.android.synthetic.main.fragment_new_arrivals_item.*

class NewArrivalsItemFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_arrivals_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillNewArrivalItemRecyclerView(naItemList)

        na_filter_btn.setOnClickListener {
            val intent = Intent(requireActivity(), FilterActivity::class.java)
            startActivity(intent)
        }

        na_sort_btn.setOnClickListener {
            val sortBottomsheetFragment: SortBottomsheetFragment =
                SortBottomsheetFragment.newInstance()
            sortBottomsheetFragment.show(
                requireActivity().supportFragmentManager,
                "SortBottomsheetFragment"
            )
        }
    }
    private val naItemList = listOf<TopSellingHomeModel>(
        TopSellingHomeModel("Princess Dress","$10.2"),
        TopSellingHomeModel("Princess Dress","$10.2"),
        TopSellingHomeModel("Princess Dress","$10.2"),
        TopSellingHomeModel("Princess Dress","$10.2"),
        TopSellingHomeModel("Princess Dress","$10.2"),
        TopSellingHomeModel("Princess Dress","$10.2"),

        )

    private fun fillNewArrivalItemRecyclerView(teachers: List<TopSellingHomeModel>) {
        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        na_item_recycler.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = NewArrivalItemLIstAdapter(naItemList)
        }
    }
}