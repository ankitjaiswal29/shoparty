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

class CategoriesFragment : Fragment(){

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
        adapterCategory = CategoryAdapter1( requireContext(),listCategory)

        val gridLayoutManager = GridLayoutManager(requireActivity(), 1)
        binding.categoryListRecycler.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = adapterCategory
        }
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


}




