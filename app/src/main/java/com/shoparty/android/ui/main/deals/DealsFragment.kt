package com.shoparty.android.ui.main.deals


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mohammedalaa.seekbar.DoubleValueSeekBarView
import com.mohammedalaa.seekbar.OnDoubleValueSeekBarChangeListener
import com.shoparty.android.R
import com.shoparty.android.databinding.FragmentDealsBinding
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.ui.filter.*
import com.shoparty.android.ui.main.categories.NewArrivalItemLIstAdapter
import com.shoparty.android.ui.main.mainactivity.MainActivity
import com.shoparty.android.ui.main.product_list.ProductListSortingBottomSheetAdapter
import com.shoparty.android.utils.ProgressDialog
import com.shoparty.android.utils.SpacesItemDecoration
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory
import kotlinx.android.synthetic.main.bottomsheet_filter_layout.view.*

class DealsFragment : Fragment(), View.OnClickListener, RecyclerViewClickListener {

    lateinit var binding: FragmentDealsBinding
    lateinit var dialog: BottomSheetDialog

    private lateinit var viewModel: DealsViewModel
    private lateinit var adapterDeals: DealsAdapter

    private val listDeals: ArrayList<DealsResponse.Deals> = ArrayList()

    private var recyvlerviewItemList = ArrayList<RecyclerView>()
    private var filterIconItem = ArrayList<TextView>()
    private lateinit var rvAdapter: FilterAdapter
    var color = false
    var size = false
    var age = false
    var gender = false
    var price = false
    private lateinit var rvcolor: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).manageUi(ivLogo = true, ivBag = true,ivSearch = true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_deals, container, false)
        viewModel = ViewModelProvider(
            this,
            ViewModalFactory(activity?.application!!)
        )[DealsViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        (activity as MainActivity).info_tools.tv_title.visibility = View.INVISIBLE
//        (activity as MainActivity).info_tools.home_shoparty_icon.visibility = View.INVISIBLE
//        (activity as MainActivity).info_tools.home_shoparty_icon2.visibility = View.VISIBLE
//
//        (activity as MainActivity).info_tools.ivBagBtn.visibility = View.VISIBLE
//        (activity as MainActivity).info_tools.iv_btnsearch.visibility = View.VISIBLE

        fillDealsRecyclerView(dealsItemList)
        Deals()
        initilize()
        setObserver()

        val request = DealsRequestModel("1", "1", "10")
        viewModel.getDeals(request)

    }

    private fun setObserver() {

        viewModel.deals.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    listDeals.clear()
                    listDeals.addAll(response.data!! as ArrayList<DealsResponse.Deals>)
                    adapterDeals.notifyDataSetChanged()
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

    private fun initilize() {
        binding.tvSort.setOnClickListener(this)
        binding.tvFilter.setOnClickListener(this)
    }

    private fun Deals() {
        val naItemList = listOf<TopSellingHomeModel>(
            TopSellingHomeModel("Princess Dress", "$10.2"),
            TopSellingHomeModel("Princess Dress", "$10.2"),
            TopSellingHomeModel("Princess Dress", "$10.2"),
            TopSellingHomeModel("Princess Dress", "$10.2"),
            TopSellingHomeModel("Princess Dress", "$10.2"),
            TopSellingHomeModel("Princess Dress", "$10.2"),
        )

        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        binding.dealsItemRecycler.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = DealsAdapter(listDeals, requireContext())
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
        binding.dealsItemRecycler.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = NewArrivalItemLIstAdapter(dealsItemList)
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_filter -> {

                // showBottomsheetFilter()
                val intent = Intent(requireContext(), FilterActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_sort -> {
                showBottomsheetDialog()
            }
            /* R.id.tv_color -> {
                 goneHide(rv_color_recyclarview)
                 iconGoneHide(tv_color)
                 color=!color;
             }
             R.id.tv_size -> {
                 goneHide(rv_size_recyclarview)
                 iconGoneHide(tv_size)
                 size=!size
             }
             R.id.tv_age -> {
                 goneHide(rv_age_recyclarview)
                 iconGoneHide(tv_age)
                 age=!age;
             }
             R.id.tv_gender -> {
                 goneHide(rv_gender_recyclarview)
                 iconGoneHide(tv_gender)
                 gender=!gender
             }
             R.id.tv_price -> {

             }*/
            /* R.id.iv_drawer_back -> {
                 onBackPressed()
             }
             R.id.btn_Applay -> {
                 finish()
             }*/
        }
    }

    private fun showBottomsheetFilter() {

        val view = layoutInflater.inflate(R.layout.bottomsheet_filter_layout, null)
        dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        val tv_color = view.findViewById<TextView>(R.id.tv_color)
        val tvsize = view.findViewById<TextView>(R.id.tv_size)
        val tvage = view.findViewById<TextView>(R.id.tv_age)
        val tvgender = view.findViewById<TextView>(R.id.tv_gender)
        val tvprice = view.findViewById<TextView>(R.id.tv_price)
        rvcolor = view.findViewById<RecyclerView>(R.id.rv_color_recyclarview)
        val double_range_seekbar =
            view.findViewById<DoubleValueSeekBarView>(R.id.double_range_seekbar)
        tv_color.setOnClickListener(this)
        tvsize.setOnClickListener(this)
        tvage.setOnClickListener(this)
        tvgender.setOnClickListener(this)
        tvprice.setOnClickListener(this)
        view.tv_color.setOnClickListener {
            goneHide(view.rv_color_recyclarview, view)
            iconGoneHide(view.tv_color, view)
            color = !color
        }
        view.tv_size.setOnClickListener {
            goneHide(view.rv_size_recyclarview, view)
            iconGoneHide(view.tv_size, view)
            size = !size

        }
        view.tv_age.setOnClickListener {
            goneHide(view.rv_age_recyclarview, view)
            iconGoneHide(view.tv_age, view)
            age = !age

        }
        view.tv_gender.setOnClickListener {
            goneHide(view.rv_gender_recyclarview, view)
            iconGoneHide(view.tv_gender, view)
            gender = !gender

        }
        view.tv_price.setOnClickListener {
            view.cl_price.visibility = View.VISIBLE
            view.tv_price.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_icon_aeroup,
                0
            )
            view.tv_color.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_icon_aeroup,
                0
            )
            view.tv_size.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_icon_aeroup,
                0
            )
            view.tv_gender.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_icon_aeroup,
                0
            )
            view.tv_age.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_icon_aeroup, 0)


            view.rv_gender_recyclarview.visibility = View.VISIBLE
            view.rv_size_recyclarview.visibility = View.VISIBLE
            view.rv_age_recyclarview.visibility = View.VISIBLE
            view.rv_color_recyclarview.visibility = View.VISIBLE

        }

        double_range_seekbar.currentMinValue = 50
        double_range_seekbar.currentMaxValue = 150
        recyvlerviewItemList.add(view.rv_color_recyclarview)
        recyvlerviewItemList.add(view.rv_size_recyclarview)
        recyvlerviewItemList.add(view.rv_age_recyclarview)
        recyvlerviewItemList.add(view.rv_gender_recyclarview)


        filterIconItem.add(view.tv_color)
        filterIconItem.add(view.tv_size)
        filterIconItem.add(view.tv_age)
        filterIconItem.add(view.tv_gender)


        double_range_seekbar.setOnRangeSeekBarViewChangeListener(object :
            OnDoubleValueSeekBarChangeListener {
            override fun onStartTrackingTouch(
                seekBar: DoubleValueSeekBarView?,
                min: Int,
                max: Int
            ) {
                Toast.makeText(requireContext(), min.toString() + max.toString(), Toast.LENGTH_LONG)
                    .show()
            }

            override fun onStopTrackingTouch(seekBar: DoubleValueSeekBarView?, min: Int, max: Int) {

            }

            override fun onValueChanged(
                seekBar: DoubleValueSeekBarView?,
                min: Int,
                max: Int,
                fromUser: Boolean
            ) {

            }

        })

        size(view)
        age(view)
        gender(view)
        filtercolor(view)

        dialog.setCancelable(true)

        // on below line we are setting
        // content view to our view.
        dialog.setContentView(view)

        // on below line we are calling
        // a show method to display a dialog.
        dialog.show()

    }

    private fun filtercolor(view: View) {
        val data = ArrayList<String>()
        data.add("#FFBB86FC")
        data.add("#606060")
        data.add("#FFBB86FC")
        data.add("#FFBB86FC")
        data.add("#E30986")
        data.add("#FFBB86FC")
        data.add("#606060")
        data.add("#E30986")
        data.add("#FFBB86FC")
        data.add("#606060")


        // val rv_color_recyclarview = view?.findViewById<RecyclerView>(R.id.rv_color_recyclarview)

        val gridLayoutManager = GridLayoutManager(requireContext(), 9)
        view.rv_color_recyclarview.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = FilterColorAdapter(data)
        }

    }

    private fun gender(view: View) {

        val data = ArrayList<String>()
        data.add("Babys")
        data.add("Girl")
        data.add("Unisex")
        data.add("Women")


        val gridLayoutManager = GridLayoutManager(requireContext(), 4)
        view.rv_gender_recyclarview?.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = FilterGenderAdapter(data, context)
        }
    }

    private fun age(view: View) {
        val data = ArrayList<String>()
        data.add("Baby 0-2 Years")
        data.add("Toddler 2-4 Years")
        data.add("Adventures 5-7 Years")
        data.add("Pioneers 8+")
        val spanCount = 2 // 2 columns
        val spacing = 10 // 30px
        val includeEdge = false

        view.rv_age_recyclarview?.addItemDecoration(
            SpacesItemDecoration(
                spanCount,
                spacing,
                includeEdge
            )
        )

        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        view.rv_age_recyclarview?.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = FilterAgeAdapter(data, context)
        }
    }

    private fun size(view: View) {
        val data = ArrayList<String>()
        data.add("S")
        data.add("M")
        data.add("XL")
        data.add("XXL")
        data.add("UK6")
        data.add("UK7")
        data.add("UK8")
        data.add("UK9")
        data.add("UK10")

        val spanCount = 5 // 2 columns
        val spacing = 10 // 30px
        val includeEdge = false

        view.rv_size_recyclarview?.addItemDecoration(
            SpacesItemDecoration(
                spanCount,
                spacing,
                includeEdge
            )
        )


        val gridLayoutManager = GridLayoutManager(requireContext(), 5)
        view.rv_size_recyclarview?.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = FilterSizeAdapter(data, context)
        }
    }

    private fun iconGoneHide(clickFilterItem: TextView, view: View) {
        view.tv_price.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            R.drawable.ic_icon_aeroup,
            0
        )
        for (textview in filterIconItem) {

            if (textview == clickFilterItem) {
                textview.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_icon_aeroup,
                    0
                )
            } else {
                textview.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_icon_aeroup,
                    0
                )
            }
        }
    }

    private fun goneHide(clickRecyclerview: RecyclerView, view: View) {
        view.cl_price.visibility = View.VISIBLE
        for (recyclerview in recyvlerviewItemList) {

            if (recyclerview == clickRecyclerview) {
                recyclerview.visibility = View.VISIBLE
            } else {
                recyclerview.visibility = View.VISIBLE
            }
        }
    }

    private fun showBottomsheetDialog() {


        // on below line we are inflating a layout file which we have created.
        val view = layoutInflater.inflate(R.layout.top_selling_bottomsheet_layout, null)
        dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        // on below line we are creating a variable for our button
        // which we are using to dismiss our dialog.
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_top_selling_bottomsheetrecyclar)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val data = ArrayList<String>()
        data.add("Newest To Oldest")
        data.add("Oldest To Newest")
        data.add("Price - Low To High")
        data.add("Price - High To Low")
        val adapter = ProductListSortingBottomSheetAdapter(data, this)
        recyclerView.adapter = adapter

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
        Toast.makeText(requireContext(), pos, Toast.LENGTH_LONG).show()
        dialog.dismiss()

    }

}


