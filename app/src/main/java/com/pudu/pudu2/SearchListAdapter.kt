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
                        val list = listOf("product_name", "serving_size", "ingredients_text", "energy-kcal_100g", "fat_100g", "proteins_100g", "sugar_100g",
                            "carbohydrates_100g", "traces", "packaging", "packaging_tags", "vitamin_c_100g", "alcohol_100g", "vitamin-a_100g", "vitamin-d_100g",
                            "vitamin-k_100g", "vitamin-b1_100g", "vitamin-b2_100g", "vitamin-pp_100g", "vitamin-b6_100g", "vitamin-b9_100g", "vitamin-b12_100g",
                            "biotin_100g", "pantothenic_acid_100g", "potassium_100g", "calcium_100g", "phosphorus_100g", "iron_100g", "magnesium_100g", "zinc_100g",
                            "copper_100g", "manganese_100g", "selenium_100g", "chromium_100g", "molybdenum_100g", "iodine_100g",
                            "sodium_100g", "caffeine_100g", "nova_group", "additives_n", "ingredients_from_palm_oil_n", "nutriscore_score", "nutriscore_grade",
                        "allergens", "allergens_tags", "main_category")
                        for (item in list){
                            if(product_data.contains(item)){
                                intent.putExtra(item, product_data.getString(item))
                            }
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