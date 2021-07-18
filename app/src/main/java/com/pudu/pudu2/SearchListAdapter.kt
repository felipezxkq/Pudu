package com.pudu.pudu2

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.pudu.pudu2.models.SearchModel
import kotlinx.android.synthetic.main.search_single_item.view.*


class SearchListAdapter (var searchList: List<SearchModel>): RecyclerView.Adapter<SearchListAdapter.SearchListViewHolder>(){

    class SearchListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(searchModel: SearchModel){
            itemView.single_item_title.text = searchModel.product_name
            itemView.single_item_title.setOnClickListener {
                returnProduct(searchModel.code, itemView)
            }
        }

        private fun returnProduct(code: String, itemView: View){
            val db = FirebaseFirestore.getInstance()
            var productsRef = db.collection("Products");
            var query = productsRef.whereEqualTo("code", code).get()
                .addOnSuccessListener { documents ->
                    if(documents.first() != null){

                        val product_data = documents.first()
                        val intent = Intent(itemView.context, ProductActivity::class.java)
                        if(product_data.contains("product_name")){
                            intent.putExtra("product_name", product_data.getString("product_name"))
                        }
                        if(product_data.contains("serving_size")){
                            intent.putExtra("serving_size", product_data.getString("serving_size"))
                        }
                        if(product_data.contains("ingredients_text")){
                            intent.putExtra("ingredients_text", product_data.getString("ingredients_text"))
                        }
                        if(product_data.contains("energy-kcal_100g")){
                            intent.putExtra("energy-kcal_100g", product_data.getString("energy-kcal_100g"))
                        }
                        if(product_data.contains("fat_100g")){
                            intent.putExtra("fat_100g", product_data.getString("fat_100g"))
                        }
                        if(product_data.contains("proteins_100g")){
                            intent.putExtra("proteins_100g", product_data.getString("proteins_100g"))
                        }
                        if(product_data.contains("sugars_100g")){
                            intent.putExtra("sugars_100g", product_data.getString("sugars_100g"))
                        }
                        if(product_data.contains("sodium_100g")){
                            intent.putExtra("sodium_100g", product_data.getString("sodium_100g"))
                        }
                        if(product_data.contains("caffeine_100g")){
                            intent.putExtra("caffeine_100g", product_data.getString("caffeine_100g"))
                        }
                        itemView.context.startActivity(intent)

                    }
                    else{
                    }

                }
                .addOnFailureListener { exception ->
                }
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