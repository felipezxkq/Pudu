package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.models.SearchModel
import kotlinx.android.synthetic.main.search_single_item.view.*


class SearchListAdapter (var searchList: List<SearchModel>): RecyclerView.Adapter<SearchListAdapter.SearchListViewHolder>(){

    class SearchListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(searchModel: SearchModel){
            itemView.single_item_title.text = searchModel.product_name

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_single_item, parent, false)
        return SearchListViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchListViewHolder, position: Int) {
        holder.bind(searchList[position])
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

}