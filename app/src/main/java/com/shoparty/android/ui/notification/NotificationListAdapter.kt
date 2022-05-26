package com.shoparty.android.ui.notification

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.databinding.NotificationListItemBinding
import com.shoparty.android.interfaces.RVItemClickListener
import com.shoparty.android.utils.Utils

class NotificationListAdapter(
    private val list: ArrayList<NotificationListResponse.Result>,
    val context: Context
) : RecyclerView.Adapter<NotificationListAdapter.ViewHolder>() {

    var listener: RVItemClickListener? = null

    fun onItemClick(listener: RVItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_list_item, parent, false) as View
        return ViewHolder(
            view = view,
            listener = listener,
            context = context
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list[position].let {
            holder.bind(it)
        }
    }

    class ViewHolder(
        val view: View,
        val listener: RVItemClickListener?,
        val context: Context) : RecyclerView.ViewHolder(view) {
        private val binding: NotificationListItemBinding? = DataBindingUtil.bind(view)
        init
        { }
        fun bind(modal: NotificationListResponse.Result)
        {
            binding?.tvItemName?.text = modal.title
            binding?.tvItemSubtitle?.text = modal.description
            binding?.tvDate?.text = Utils.convertToCustomFormatDate(modal.created_at)
        }
    }
}