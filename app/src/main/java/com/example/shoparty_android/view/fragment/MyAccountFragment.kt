package com.example.shoparty_android.view.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoparty_android.R
import com.example.shoparty_android.adapter.MyAccountAdapter
import com.example.shoparty_android.adapter.RecyclerViewClickListener
import com.example.shoparty_android.model.MyAccountModel
import com.example.shoparty_android.view.activity.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MyAccountFragment : Fragment(), RecyclerViewClickListener {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val root =  inflater.inflate(R.layout.fragment_my_account, container, false)
        val recyclerview = root.findViewById<RecyclerView>(R.id.recyclerview);
        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(requireContext())

        // ArrayList of class ItemsViewModel
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


        val adapter = MyAccountAdapter(data,this)
        recyclerview.adapter = adapter

        return root
     //   return inflater.inflate(R.layout.fragment_my_account, container, false)


        // getting the recyclerview by its id

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



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
           // startActivity(Intent(this, VouchersActivity::class.java))
            "idwishlist" -> {
                val intent = Intent (getActivity(), WishListActivity::class.java)
                getActivity()?.startActivity(intent)
            }
                //startActivity(Intent(this, WishListActivity::class.java))

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
               // startActivity(Intent(this, PrivacyPolicyActivity::class.java))

            "idreturnpolicy" ->{
                val intent = Intent (getActivity(), ReturnPolicyActivity::class.java)
                getActivity()?.startActivity(intent)
            }
               // startActivity(Intent(this, ReturnPolicyActivity::class.java))
            "idsignout" ->{
                val builder = AlertDialog.Builder(context, R.style.CustomAlertDialog)
                val inflater = layoutInflater
                val dialogLayout: View =
                    inflater.inflate(R.layout.alert_dialog_signout2, null)
                val btn_cancel = dialogLayout.findViewById<Button>(R.id.btn_cancel)
                val btn_yes = dialogLayout.findViewById<Button>(R.id.btn_yes)
                builder.setView(dialogLayout)
                builder.show()

                btn_yes.setOnClickListener {

                }
                btn_cancel.setOnClickListener {

                }
                /* builder.setView(dialogLayout)
                 builder.show()*/
            }


        }
    }
}