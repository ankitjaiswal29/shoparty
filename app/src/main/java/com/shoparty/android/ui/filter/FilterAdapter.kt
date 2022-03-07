package com.shoparty.android.ui.filter

import android.app.ActionBar
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shoparty.android.databinding.FilterRecyclarColorLayoutItemBinding
import java.util.ArrayList
import android.widget.TextView




class FilterAdapter(
    private var languageList: List<FilterModel>,var context: Context
) : RecyclerView.Adapter<FilterAdapter.ViewHolder>() {

    // create an inner class with name ViewHolder
    // It takes a view argument, in which pass the generated class of single_item.xml
    // ie SingleItemBinding and in the RecyclerView.ViewHolder(binding.root) pass it like this
    inner class ViewHolder(val binding: FilterRecyclarColorLayoutItemBinding) : RecyclerView.ViewHolder(binding.root)

    // inside the onCreateViewHolder inflate the view of SingleItemBinding
    // and return new ViewHolder object containing this layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FilterRecyclarColorLayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // bind the items with each item of the list languageList which than will be
    // shown in recycler view
    // to keep it simple we are not setting any image data to view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(languageList[position]){
                // set name of the language from the list
                val item = languageList[position]
                binding.tvLangName.text = item.name



               /* for( item in description){

                    val text = TextView(context)
                    text.layoutParams =
                        ActionBar.LayoutParams(
                            ActionBar.LayoutParams.WRAP_CONTENT,
                            ActionBar.LayoutParams.WRAP_CONTENT
                        )
                    text.text = "" +item
                    binding.tvDescription.addView(text)

                }*/
               // text.text = "" +description[position].toString()
               // binding.tvDescription.addView(text)



                // set description to the text
                // since this is inside "expandedView" its visibility will be gone initially
                // after click on the item we will make the visibility of the "expandedView" visible
                // which will also make the visibility of desc also visible
               // binding.tvDescription.setTags(languageList[position].description)

                holder.binding.rvFilter.setLayoutManager(
                    GridLayoutManager(
                        context,
                        3
                    )
                )
                val filterchildadapter = FilterChildAdapter(context,description)
                holder.binding.rvFilter.setAdapter(filterchildadapter)
                // check if boolean property "extend" is true or false
                // if it is true make the "extendedView" Visible
                binding.expandedView.visibility = if (this.expand) View.VISIBLE else View.GONE
                // on Click of the item take parent card view in our case
                // revert the boolean "expand"
                binding.cardLayout.setOnClickListener {
                    this.expand = !this.expand
                    //notifyDataSetChanged()
                    notifyItemChanged(adapterPosition)
                }

           //     binding.tvDescription.setOnTagClickListener(TagView.OnTagClickListener)
            }
        }
    }
    // return the size of languageList
    override fun getItemCount(): Int {
        return languageList.size
    }
}