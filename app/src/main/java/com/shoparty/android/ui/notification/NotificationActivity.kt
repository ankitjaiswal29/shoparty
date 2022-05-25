package com.shoparty.android.ui.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shoparty.android.R
import com.shoparty.android.databinding.ActivityNotificationBinding
import com.shoparty.android.utils.PrefManager
import com.shoparty.android.utils.ProgressDialog
import com.shoparty.android.utils.apiutils.Resource
import com.shoparty.android.utils.apiutils.ViewModalFactory


class NotificationActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityNotificationBinding
    private lateinit var viewModel: NotificationListViewModel
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification)
        if(PrefManager.read(PrefManager.LANGUAGEID, 1)==2){
            binding.mainLayoutNotification.layoutDirection = View.LAYOUT_DIRECTION_RTL
            binding.infoTool.ivDrawerBack.rotation = 0F
        }else {
            binding.mainLayoutNotification.layoutDirection = View.LAYOUT_DIRECTION_LTR
            binding.infoTool.ivDrawerBack.rotation = 180F
        }

        initialise()
        viewModel.notificationList(PrefManager.read(PrefManager.LANGUAGEID,1).toString()) //api call
        setObserver()
    }


    private fun initialise()
    {
        binding.infoTool.tvTitle.text = getString(R.string.notification)
        viewModel = ViewModelProvider(this,
            ViewModalFactory(application))[NotificationListViewModel::class.java]

        binding.infoTool.ivDrawerBack.setOnClickListener {
            finish()
        }
    }

    private fun setObserver() {
        viewModel.notificationlist.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    if (response.data.isNullOrEmpty())
                    {
                        binding.clNoData.visibility = View.VISIBLE
                        binding.recyclernotification.visibility = View.GONE
                    }
                    else {
                        binding.clNoData.visibility = View.GONE
                        binding.recyclernotification.visibility = View.VISIBLE
                        setNotificationListAdapter(response.data)
                    }
                }
                is Resource.Loading -> {
                    ProgressDialog.showProgressBar(this)
                }
                is Resource.Error -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setNotificationListAdapter(data: List<NotificationListResponse.Result>?) {
       var adapter = NotificationListAdapter(data!! as ArrayList<NotificationListResponse.Result>,this)
        binding.recyclernotification.layoutManager = LinearLayoutManager(this)
        binding.recyclernotification.adapter = adapter
    }


}