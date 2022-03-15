package com.shoparty.android.ui.main.categories

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.shoparty.android.R
import com.shoparty.android.databinding.FragmentCategoriesBinding
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.ui.main.topselling.TopSellingActivity
import com.shoparty.android.utils.Constants
import com.shoparty.android.utils.ProgressDialog
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory

class CategoriesFragment : Fragment() ,RecyclerViewClickListener{

    lateinit var binding: FragmentCategoriesBinding
    private lateinit var viewModel: CategoryViewModel
    private lateinit var adapterCategory: CategoryAdapter1

    private val listCategory: ArrayList<CategoryResponse.Category> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_categories, container, false)
        viewModel = ViewModelProvider(
            this,
            ViewModalFactory(activity?.application!!)
        )[CategoryViewModel::class.java]

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialise()
        setObserver()
        val request = CategoryRequestModel("1")
        viewModel.getCategory(request)
    }

    fun initialise() {
        adapterCategory = CategoryAdapter1( requireContext(),listCategory,this)

        val gridLayoutManager = GridLayoutManager(requireActivity(), 1)
        binding.categoryListRecycler.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = adapterCategory
        }

       /* adapterCategory.onItemClick(object :  RecyclerViewClickListener{
            override fun click(pos: String) {
                startActivity(Intent(activity, TopSellingActivity::class.java))
            }
        })*/

    }

    private fun setObserver() {

        viewModel.category.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    listCategory.clear()
                    listCategory.addAll(response.data!! as ArrayList<CategoryResponse.Category>)
                    adapterCategory.notifyDataSetChanged()
                }
                is Resource.Loading -> {
                    ProgressDialog.showProgressBar(requireContext())
                }
                is Resource.Error -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        requireContext(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        requireContext(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun click(Proudct_id: String) {
       // Toast.makeText(requireContext(),Proudct_id.toString(),Toast.LENGTH_LONG).show()
        val intent = Intent(requireActivity(), TopSellingActivity::class.java)
        intent.putExtra(Constants.PRODUCTID,Proudct_id )
        startActivity(intent)
    }
}

//        (activity as MainActivity).info_tools.tv_title.visibility=View.INVISIBLE
//        (activity as MainActivity).info_tools.home_shoparty_icon.visibility=View.INVISIBLE
//        (activity as MainActivity).info_tools.home_shoparty_icon2.visibility=View.VISIBLE
//
//        (activity as MainActivity).info_tools.ivBagBtn.visibility=View.VISIBLE
//        (activity as MainActivity).info_tools.iv_btnsearch.visibility=View.VISIBLE


//        val categoryItemList = listOf<HomeCategoriesModel>(
//            HomeCategoriesModel("Costumes"),
//            HomeCategoriesModel("Themes"),
//            HomeCategoriesModel("Party Supply"),
//            HomeCategoriesModel("Ballons"),
//            HomeCategoriesModel("Birthday Cake"),
//            HomeCategoriesModel("Toys"),
//            HomeCategoriesModel("Candles"),
//            HomeCategoriesModel("Costumes"),
//            HomeCategoriesModel("Themes"),
//            HomeCategoriesModel("Party Supply"),
//            HomeCategoriesModel("Ballons")
//
//        )

//    binding.categoryListRecycler.adapter = CategoryAdapter1(categoryItemList,requireContext())
//}



