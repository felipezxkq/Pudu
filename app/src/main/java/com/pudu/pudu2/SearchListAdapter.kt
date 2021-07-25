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
                        val product_100g_map:Map<String, String> = mapOf("Product name" to "product_name", "Serving size" to "serving_size", "Ingredients" to "ingredients_text",
                            "Calories" to "energy-kcal_100g", "Fat" to "fat_100g", "Proteins" to "proteins_100g", "Sugar" to "sugar_100g",
                            "Carbohydrates" to "carbohydrates_100g", "Traces" to "traces","Packaging" to "packaging","Packaging tags" to "packaging_tags",
                            "Vitamin C" to "vitamin_c_100g", "Alcohol" to "alcohol_100g", "Vitamin A" to "vitamin-a_100g", "Vitamin D" to "vitamin-d_100g",
                            "Vitamin K" to "vitamin-k_100g", "Vitamin B1" to "vitamin-b1_100g", "Vitamin B2" to "vitamin-b2_100g","Vitamin PP" to "vitamin-pp_100g",
                            "Vitamin B6" to "vitamin-b6_100g", "Vitamin B9" to "vitamin-b9_100g", "Vitamin B12" to "vitamin-b12_100g",
                            "Biotin" to "biotin_100g", "Panthotenic Acid" to "pantothenic_acid_100g","Potassium" to "potassium_100g", "Calcium" to "calcium_100g",
                            "Phosphorus" to "phosphorus_100g", "Iron" to "iron_100g", "Magnesium" to "magnesium_100g", "Zinc" to "zinc_100g",
                            "Copper" to "copper_100g", "Manganese" to "manganese_100g", "Selenium" to "selenium_100g", "Chromium" to "chromium_100g", "Molybdenum" to "molybdenum_100g",
                            "Iodine" to "iodine_100g","Sodium" to "sodium_100g", "Caffeine" to "caffeine_100g", "Nova group" to "nova_group", "Additives" to "additives_n"
                            , "produced_per_footprint" to "produced_per_footprint", "Carbon footprint" to "carbon_footprint", "Water usage" to "water_usage", "Land usage" to "land_usage"
                            , "Comments" to "comments_en")

                        val product_serving_map:Map<String, String> = mapOf("Product name" to "product_name", "Serving size" to "serving_size", "Ingredients" to "ingredients_text",
                            "Traces" to "traces","Packaging" to "packaging","Packaging tags" to "packaging_tags",
                            "Nova group" to "nova_group", "Additives" to "additives_n",
                            "Ingredients from palm oil" to "ingredients_from_palm_oil_n", "Nutriscore" to "nutriscore_score", "Nutriscore grade" to "nutriscore_grade",
                            "Calories" to "calories","Protein" to "proteins","Sugar" to "sugar","Carbohydrates" to "carbohydrates","Vitamin C" to "vitamin_c", "Vitamin A" to "vitamin_a",
                            "Vitamin D" to "vitamin_d", "Vitamin K" to "vitamin_k", "Vitamin B1" to "vitamin_b1", "Vitamin B2" to "vitamin_b2", "Vitamin B6" to "vitamin_b6",
                            "Vitamin B9" to "vitamin_b9", "Vitamin B12" to "vitamin_b12", "Vitamin PP" to "vitamin_pp", "Potassium" to "potassium", "Calcium" to "calcium",
                            "Phosphorus" to "phosphorus", "Iron" to "iron", "Magnesium" to "magnesium", "Zinc" to "zinc","Copper" to "copper", "Manganese" to "manganese",
                            "Selenium" to "selenium", "Chromium" to "chromium", "Molybdenum" to "molybdenum", "Iodine" to "iodine", "Sodium" to "sodium", "Caffeine" to "caffeine",
                            "Total fat" to "total_fat", "Polyunsaturated fat" to "polyunsaturated_fat", "Monounsaturated fat" to "monounsaturated_fat", "Cholesterol" to "cholesterol",
                            "Omega 3" to "omega_3", "Omega 6" to "omega_6", "Omega 9" to "omega_9", "Biotin" to "biotin"
                            , "produced_per_footprint" to "produced_per_footprint", "Carbon footprint (kg of CO2)" to "carbon_footprint", "Water usage (lts)" to "water_usage", "Land usage (m2)" to "land_usage"
                            , "Comments" to "comments_en")

                        if(product_data.contains("energy-kcal_100g") && product_data.getString("energy-kcal_100g") != ""){
                            intent.putExtra("serving_type", "per 100g")
                            for (item in product_100g_map){
                                intent.putExtra(item.key, product_data.getString(item.value))
                            }
                        }
                        else{
                            intent.putExtra("serving_type", "per serving")
                            for (item in product_serving_map){
                                intent.putExtra(item.key, product_data.getString(item.value))
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