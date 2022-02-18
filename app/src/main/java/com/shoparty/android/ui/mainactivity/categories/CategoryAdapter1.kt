package com.shoparty.android.ui.mainactivity.categories

import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.R
import com.shoparty.android.utils.inflate
import com.shoparty.android.ui.mainactivity.home.HomeCategoriesModel

import kotlinx.android.synthetic.main.category_item1.view.*

class CategoryAdapter1(private val itemList: List<HomeCategoriesModel>): RecyclerView.Adapter<CategoryAdapter1.CategoryViewModel>() {

    inner class CategoryViewModel(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewModel {
        return CategoryViewModel(parent.inflate(R.layout.category_item1))
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: CategoryViewModel, position: Int) {
        val items = itemList[position]
        holder.itemView.apply {
            category_name.text = items.name
            category_root_lay.setOnClickListener {
                findNavController().navigate(R.id.categoryItemListFragment)
            }
        }
    }
}