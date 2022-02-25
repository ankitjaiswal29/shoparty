package com.shoparty.android.ui.main.myaccount

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.shoparty.android.R
import com.shoparty.android.databinding.FragmentMyAccountBinding
import com.shoparty.android.ui.myaccount.MyAccountModel
import com.shoparty.android.ui.aboutus.AboutUsActivity
import com.shoparty.android.ui.address.addaddress.getaddress.AddressActivity
import com.shoparty.android.ui.contactus.ContactUsActivity
import com.shoparty.android.ui.myaccount.MyProfileActivity
import com.shoparty.android.ui.myorders.myorder.MyOrdersActivity
import com.shoparty.android.ui.privacypolicy.PrivacyPolicyActivity
import com.shoparty.android.ui.returnpolicy.ReturnPolicyActivity
import com.shoparty.android.ui.termsandcondition.TermAndConditionActivity
import com.shoparty.android.ui.vouchers.VouchersActivity
import com.shoparty.android.ui.wishlist.WishListActivity
import com.shoparty.android.interfaces.RecyclerViewClickListener
import com.shoparty.android.ui.main.mainactivity.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dashboard_toolbar.view.*

class MyAccountFragment : Fragment(), RecyclerViewClickListener {
    private lateinit var binding: FragmentMyAccountBinding
    private lateinit var myaccountAdapter: MyAccountAdapter
    var dialog: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_account, container, false)
            init()
            return binding.root
    }

    fun init()
    {
        (activity as MainActivity).info_tools.tv_title.visibility=View.INVISIBLE
        (activity as MainActivity).info_tools.home_shoparty_icon.visibility=View.INVISIBLE
        (activity as MainActivity).info_tools.home_shoparty_icon2.visibility=View.VISIBLE

        (activity as MainActivity).info_tools.ivBagBtn.visibility=View.INVISIBLE
        (activity as MainActivity).info_tools.iv_btnsearch.visibility=View.INVISIBLE

        val data = ArrayList<MyAccountModel>()
        data.add(MyAccountModel(R.drawable.ic_myorder_icon,"idmyorder",getString(R.string.my_order) ))
        data.add(MyAccountModel(R.drawable.ic_vouchers_icon,"idvoucher",getString(R.string.vouchers) ))

        data.add(MyAccountModel(R.drawable.ic_wishlist_icon,"idwishlist",getString(R.string.wishlist) ))
        data.add(MyAccountModel(R.drawable.ic_myprofile_icon,"idmyprofile",getString(R.string.my_profile) ))

        data.add(MyAccountModel(R.drawable.ic_address_icon,"idaddress",getString(R.string.address_book) ))
        data.add(MyAccountModel(R.drawable.ic_rate_our_icon,"idrate",getString(R.string.rate_our_app) ))


        data.add(MyAccountModel(R.drawable.ic_contact_icon,"idcontact",getString(R.string.contact_us) ))
        data.add(MyAccountModel(R.drawable.ic_aboutus_icon,"idabout",getString(R.string.about_us) ))

        data.add(MyAccountModel(R.drawable.ic_term_and_conditon_icon,"idtermcondition",getString(R.string.terms_and_conditions) ))
        data.add(MyAccountModel(R.drawable.ic_privacy_policy_icon,"iprivacypolicy",getString(R.string.privacy_policy) ))

        data.add(MyAccountModel(R.drawable.ic_return_policy_icon,"idreturnpolicy",getString(R.string.return_policy) ))
        data.add(MyAccountModel(R.drawable.ic_sign_out_icon,"idsignout",getString(R.string.sign_out) ))


        myaccountAdapter = MyAccountAdapter(requireContext(),data,this)
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter = myaccountAdapter
    }



    override fun click(pos: String) {
        when(pos) {
            "idmyorder" ->{
                val intent = Intent (getActivity(), MyOrdersActivity::class.java)
                getActivity()?.startActivity(intent)
            }


            "idvoucher" ->{
                val intent = Intent (getActivity(), VouchersActivity::class.java)
                getActivity()?.startActivity(intent)
            }

            "idwishlist" -> {
                val intent = Intent (getActivity(), WishListActivity::class.java)
                getActivity()?.startActivity(intent)
            }


            "idmyprofile" -> {
                val intent = Intent (getActivity(), MyProfileActivity::class.java)
                getActivity()?.startActivity(intent)

            }//startActivity(Intent(this, MyProfileActivity::class.java))
            "idaddress" ->
            {
                val intent = Intent (getActivity(), AddressActivity::class.java)
                getActivity()?.startActivity(intent)
               // startActivity(Intent(this, AddressActivity::class.java))
            }
            //  "idrate" -> startActivity(Intent(this, Ra::class.java))
            "idcontact" ->{
                val intent = Intent (getActivity(), ContactUsActivity::class.java)
                getActivity()?.startActivity(intent)
            } //startActivity(Intent(this, ContactUsActivity::class.java))
            "idabout" ->{
                val intent = Intent (getActivity(), AboutUsActivity::class.java)
                getActivity()?.startActivity(intent)
            }//startActivity(Intent(this, AboutUsActivity::class.java))

            "idtermcondition" -> {
                val intent = Intent (getActivity(), TermAndConditionActivity::class.java)
                getActivity()?.startActivity(intent)
            }
               // startActivity(Intent(this, TermAndConditionActivity::class.java))
            "iprivacypolicy" ->{
                val intent = Intent (getActivity(), PrivacyPolicyActivity::class.java)
                getActivity()?.startActivity(intent)
            }
            "idreturnpolicy" ->{
                val intent = Intent (getActivity(), ReturnPolicyActivity::class.java)
                getActivity()?.startActivity(intent)
            }
            "idsignout" ->{
                dialog = Dialog(requireContext())
                dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                dialog!!.setContentView(R.layout.alert_dialog_signout2)
                val lp = WindowManager.LayoutParams()
                lp.copyFrom(dialog!!.getWindow()?.attributes)
                lp.width = WindowManager.LayoutParams.MATCH_PARENT
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT
                lp.gravity = Gravity.CENTER
                dialog!!.window?.attributes = lp
                dialog!!.setCanceledOnTouchOutside(false)
                val btn_cancel = dialog!!.findViewById<Button>(R.id.btn_cancel)
                val btn_yes = dialog!!.findViewById<Button>(R.id.btn_yes)
                btn_yes.setOnClickListener {
                }
                btn_cancel.setOnClickListener {
                dialog!!.dismiss()
                }
                dialog!!.show()
            }
        }
    }
}