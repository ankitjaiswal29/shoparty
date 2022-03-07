package com.shoparty.android.ui.privacypolicy

import android.os.Bundle
import android.view.View
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityPrivacyPolicyBinding


class PrivacyPolicyActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPrivacyPolicyBinding
    private var expandableListView: ExpandableListView? = null
    private var adapter: ExpandableListAdapter? = null
    private var titleList: List<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_privacy_policy)
        initialise()

    }

    private fun initialise() {
        binding.infoTool.ivDrawerBack.setOnClickListener(this)
        binding.infoTool.tvTitle.setText("PrivacyPolicy")

        privacyPolicyListing()

    }

    private fun privacyPolicyListing() {
        val recyclerview = findViewById<RecyclerView>(R.id.rv_privacyPolicyRecyclar)
        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<PrivacyPolicyModel>()
        data.add(
            PrivacyPolicyModel(
                "Lorem Ipsum is simply dummy text of the",
                "Lorem Ipsum is simply dummy text of printing typesetting industry. Lorem Ipsum industry's standard dummy text ever since the"
            )
        )
        data.add(
            PrivacyPolicyModel(
                "Lorem Ipsum is simply dummy text of the",
                "Lorem Ipsum is simply dummy text of printing typesetting industry. Lorem Ipsum industry's standard dummy text ever since the"
            )
        )
        data.add(
            PrivacyPolicyModel(
                "Lorem Ipsum is simply dummy text of the",
                "Lorem Ipsum is simply dummy text of printing typesetting industry. Lorem Ipsum industry's standard dummy text ever since the"
            )
        )
        data.add(
            PrivacyPolicyModel(
                "Lorem Ipsum is simply dummy text of the",
                "Lorem Ipsum is simply dummy text of printing typesetting industry. Lorem Ipsum industry's standard dummy text ever since the"
            )
        )
        data.add(
            PrivacyPolicyModel(
                "Lorem Ipsum is simply dummy text of the",
                "Lorem Ipsum is simply dummy text of printing typesetting industry. Lorem Ipsum industry's standard dummy text ever since the"
            )
        )


        val adapter = PrivacyPolicyAdapter(data)
        recyclerview.adapter = adapter
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_drawer_back -> {
                onBackPressed()
            }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}