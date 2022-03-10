package com.shoparty.android.ui.main.home

import android.app.AlertDialog
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
import com.shoparty.android.R
import com.shoparty.android.databinding.FragmentHomeBinding
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.ui.main.deals.TopSellingHomeModel
import com.shoparty.android.ui.main.mainactivity.MainActivity
import com.shoparty.android.ui.main.topselling.TopSellingActivity
import com.shoparty.android.ui.search.SearchActivity
import com.shoparty.android.utils.ProgressDialog
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory
import com.smarteist.autoimageslider.SliderView

class HomeFragment : Fragment(), RecyclerViewClickListener, View.OnClickListener {

    lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    private val topBannerList: ArrayList<HomeResponse.TopBanner> = ArrayList()
    private lateinit var adapterContest: HomeCategoriesAdapter

    private val bottomBannerList: ArrayList<HomeResponse.BottomBanner> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        viewModel = ViewModelProvider(
            this,
            ViewModalFactory(activity?.application!!)
        )[HomeViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).manageUi(ivLogo = true, ivBag = true)
        initialise()
    }

    private fun initialise() {
        binding.tvSearch.setOnClickListener(this)
        binding.tvViewall.setOnClickListener(this)
        val imageList: ArrayList<String> = ArrayList()
        imageList.add("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg")
        imageList.add("https://images.ctfassets.net/hrltx12pl8hq/4plHDVeTkWuFMihxQnzBSb/aea2f06d675c3d710d095306e377382f/shutterstock_554314555_copy.jpg")
        imageList.add("https://media.istockphoto.com/photos/child-hands-formig-heart-shape-picture-id951945718?k=6&m=951945718&s=612x612&w=0&h=ih-N7RytxrTfhDyvyTQCA5q5xKoJToKSYgdsJ_mHrv0=")
        setImageInSlider(imageList, binding.imageSliderr)

        Topselling()
        HomeCategory()
        season()
        HomeTheamRecyclar()
        NewArrival()
        TopsellingSubcategories()
        OfferDiscoutItem()
        Brands()
        setObserver()
        val request = HomeRequestModel("1")
        viewModel.getDashboardData(request)
    }

    private fun setObserver() {

        viewModel.dashboardResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    topBannerList.clear()
                    topBannerList.addAll(response.data?.top_banner!! as ArrayList<HomeResponse.TopBanner>)
                    adapterContest.notifyDataSetChanged()
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

    private fun Brands() {
//        val homeOffersList = listOf<HomeCategoriesModel>(
//            HomeCategoriesModel("Up To 10% Off"),
//            HomeCategoriesModel("Up To 10% Off"),
//            HomeCategoriesModel("Up To 10% Off"),
//            HomeCategoriesModel("Up To 10% Off")
//        )

        adapterContest = HomeCategoriesAdapter(topBannerList, requireContext())
        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        binding.homeBondsRecycler.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = adapterContest
        }
    }

    private fun OfferDiscoutItem() {
        val homeOffersList = listOf<HomeCategoriesModel>(
            HomeCategoriesModel("Up To 10% Off"),
            HomeCategoriesModel("Up To 10% Off")
        )

        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        binding.homeOffersRecycler.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = HomeOffersAdapter(homeOffersList)
        }
    }

    private fun TopsellingSubcategories() {
        val tsSubCategoriesList = listOf<HomeCategoriesModel>(
            HomeCategoriesModel("Mask"),
            HomeCategoriesModel("Mask"),
            HomeCategoriesModel("Mask"),
            HomeCategoriesModel("Mask")
        )

        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        binding.tsSubcategoriesRecycler.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = TopSellingSubcategoriesAdapter(tsSubCategoriesList)
        }
    }

    private fun NewArrival() {
        val newArrivalsList = listOf<HomeCategoriesModel>(
            HomeCategoriesModel("Toys Kids"),
            HomeCategoriesModel("Toys Kids"),
            HomeCategoriesModel("Toys Kids"),
            HomeCategoriesModel("Toys Kids")
        )

        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
        binding.homeNewArrivalsRecycler.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            isFocusable = false
            adapter = NewArrivalsHomeAdapter(newArrivalsList, requireContext())
        }
    }

    private fun HomeTheamRecyclar() {

        val themesList = listOf<HomeCategoriesModel>(
            HomeCategoriesModel("Unicorn"),
            HomeCategoriesModel("Mermaid"),
            HomeCategoriesModel("Unicorn"),
            HomeCategoriesModel("Mermaid"),
        )

        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
//        binding.homeThemesRecycler.apply {
//            layoutManager = gridLayoutManager
//            setHasFixedSize(true)
//            isFocusable = false
//            adapter = HomeCategoriesAdapter(themesList, requireContext())
//        }


    }

    private fun season() {
        val seasonsItemList = listOf<HomeCategoriesModel>(
            HomeCategoriesModel("Ballons"),
            HomeCategoriesModel("Ballons"),
            HomeCategoriesModel("Ballons"),
            HomeCategoriesModel("Ballons"),
            HomeCategoriesModel("Ballons")
        )

        binding.seasonsRecycler.adapter = HomeSeasonsAdapter(seasonsItemList, requireContext())

    }

    private fun HomeCategory() {
        val categoryList = listOf<HomeCategoriesModel>(
            HomeCategoriesModel("Ballons"),
            HomeCategoriesModel("Party Supply"),
        )

        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)
//        binding.homeCategoriesRecycler.apply {
//            layoutManager = gridLayoutManager
//            setHasFixedSize(true)
//            isFocusable = false
//            adapter = HomeCategoriesAdapter(categoryList, requireContext())
//        }

    }

    private fun Topselling() {
        val topSellingItemList = listOf<TopSellingHomeModel>(
            TopSellingHomeModel("Princess Dress", "$10.2"),
            TopSellingHomeModel("Princess Dress", "$10.2"),
            TopSellingHomeModel("Princess Dress", "$10.2"),
            TopSellingHomeModel("Princess Dress", "$10.2"),
            TopSellingHomeModel("Princess Dress", "$10.2"),
            TopSellingHomeModel("Princess Dress", "$10.2"),
        )
        binding.topSellingRecycler.adapter = TopSellingHomeAdapter(topSellingItemList, this)
    }

    private fun setImageInSlider(images: ArrayList<String>, imageSlider: SliderView) {
        val adapter = MySliderImageAdapter(requireContext())
        adapter.renewItems(images)
        imageSlider.setSliderAdapter(adapter)
        imageSlider.isAutoCycle = true
        imageSlider.startAutoCycle()
    }

    override fun click(pos: String) {
        val intent = Intent(activity, TopSellingActivity::class.java)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvSearch -> {
                val intent = Intent(activity, SearchActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_viewall -> {
                val intent = Intent(activity, TopSellingActivity::class.java)
                startActivity(intent)
            }
        }

    }


    private fun showdialog() {
        val builder = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialogWithMargin)
        val inflater = layoutInflater
        val dialogLayout: View =
            inflater.inflate(R.layout.alert_dialog_location, null)
        val tvallow = dialogLayout.findViewById<TextView>(R.id.tv_allow)
        val tvallow_usingapp = dialogLayout.findViewById<TextView>(R.id.tv_allow_whileusig_app)
        val tv_dont_allow = dialogLayout.findViewById<TextView>(R.id.tv_dont_allow)
        builder.setView(dialogLayout)
        val builderinstance = builder.show()
        tvallow.setOnClickListener {
            builder.setCancelable(true)
            Toast.makeText(requireContext(), "done", Toast.LENGTH_LONG).show()
            builderinstance.dismiss()
        }
        tvallow_usingapp.setOnClickListener {
            Toast.makeText(requireContext(), "dfdsf", Toast.LENGTH_LONG).show()
            builderinstance.dismiss()
        }

    }
}