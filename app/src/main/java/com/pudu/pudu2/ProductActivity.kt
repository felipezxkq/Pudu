package com.pudu.pudu2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_product.*


class ProductActivity : AppCompatActivity() {
    private lateinit var productNameText: TextView
    private lateinit var servingSizeText: TextView
    private lateinit var caloriesText: TextView
    private lateinit var proteinText: TextView
    private lateinit var caffeineText: TextView
    private lateinit var fatText: TextView
    private lateinit var ingredientsListText: TextView
    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        toolbar = findViewById(R.id.toolbar)
        toolbar?.title = "Pudu"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)



        productNameText = findViewById<TextView>(R.id.textProductName)
        productNameText.setText(intent.getStringExtra("Product name"))

        servingSizeText = findViewById<TextView>(R.id.textServingSize)
        servingSizeText.setText(intent.getStringExtra("Serving size"))


        val product_100g_map:Map<String, String> = mapOf(
            "Calories" to "energy-kcal_100g", "Fat" to "fat_100g", "Proteins" to "proteins_100g", "Sugar" to "sugar_100g",
            "Carbohydrates" to "carbohydrates_100g",
            "Vitamin C" to "vitamin_c_100g", "Alcohol" to "alcohol_100g", "Vitamin A" to "vitamin-a_100g", "Vitamin D" to "vitamin-d_100g",
            "Vitamin K" to "vitamin-k_100g", "Vitamin B1" to "vitamin-b1_100g", "Vitamin B2" to "vitamin-b2_100g","Vitamin PP" to "vitamin-pp_100g",
            "Vitamin B6" to "vitamin-b6_100g", "Vitamin B9" to "vitamin-b9_100g", "Vitamin B12" to "vitamin-b12_100g",
            "Biotin" to "biotin_100g", "Panthotenic Acid" to "pantothenic_acid_100g","Potassium" to "potassium_100g", "Calcium" to "calcium_100g",
            "Phosphorus" to "phosphorus_100g", "Iron" to "iron_100g", "Magnesium" to "magnesium_100g", "Zinc" to "zinc_100g",
            "Copper" to "copper_100g", "Manganese" to "manganese_100g", "Selenium" to "selenium_100g", "Chromium" to "chromium_100g", "Molybdenum" to "molybdenum_100g",
            "Iodine" to "iodine_100g","Sodium" to "sodium_100g", "Caffeine" to "caffeine_100g")

        val product_serving_map:Map<String, String> = mapOf(
            "Calories" to "calories","Protein" to "proteins","Sugar" to "sugar","Carbohydrates" to "carbohydrates","Vitamin C" to "vitamin_c", "Vitamin A" to "vitamin_a",
            "Vitamin D" to "vitamin_d", "Vitamin K" to "vitamin_k", "Vitamin B1" to "vitamin_b1", "Vitamin B2" to "vitamin_b2", "Vitamin B6" to "vitamin_b6",
            "Vitamin B9" to "vitamin_b9", "Vitamin B12" to "vitamin_b12", "Vitamin PP" to "vitamin_pp", "Potassium" to "potassium", "Calcium" to "calcium",
            "Phosphorus" to "phosphorus", "Iron" to "iron", "Magnesium" to "magnesium", "Zinc" to "zinc","Copper" to "copper", "Manganese" to "manganese",
            "Selenium" to "selenium", "Chromium" to "chromium", "Molybdenum" to "molybdenum", "Iodine" to "iodine", "Sodium" to "sodium", "Caffeine" to "caffeine",
            "Total fat" to "total_fat", "Polyunsaturated fat" to "polyunsaturated_fat", "Monounsaturated fat" to "monounsaturated_fat", "Cholesterol" to "cholesterol",
            "Omega 3" to "omega_3", "Omega 6" to "omega_6", "Omage 9" to "omega_9", "Biotin" to "biotin")

        var linearLayout: LinearLayout = findViewById(R.id.nutritional_info)
        val view: View = layoutInflater.inflate(R.layout.product_data_item, null)
        view.findViewById<TextView>(R.id.textKeyItem).setText("hola")
        view.findViewById<TextView>(R.id.textDataItem).setText("hola")


        if(intent.getStringExtra("serving type") == "per 100g"){
            for(element in product_100g_map){
                if(intent.getStringExtra(element.key) != "" && intent.getStringExtra(element.key) != null){
                    val view: View = layoutInflater.inflate(R.layout.product_data_item, null)
                    view.findViewById<TextView>(R.id.textKeyItem).setText(element.key)
                    view.findViewById<TextView>(R.id.textDataItem).setText(intent.getStringExtra(element.key))
                    linearLayout.addView(view)
                }

            }
        }else{
            for(element in product_serving_map){
                if(intent.getStringExtra(element.key) != "" && intent.getStringExtra(element.key) != null){
                    println("Data is: ")
                    println(intent.getStringExtra(element.key))
                    val view: View = layoutInflater.inflate(R.layout.product_data_item, null)
                    view.findViewById<TextView>(R.id.textKeyItem).setText(element.key)
                    view.findViewById<TextView>(R.id.textDataItem).setText(intent.getStringExtra(element.key))
                    linearLayout.addView(view)
                }
            }
        }


        /*
        caloriesText = findViewById<TextView>(R.id.textCalories)
        if(intent.getStringExtra("energy-kcal_100g") != null){
            caloriesText.setText("Calories: "+intent.getStringExtra("energy-kcal_100g") + "kcal")
        }else if(intent.getStringExtra("calories") != null){
            caloriesText.setText("Calories: "+intent.getStringExtra("calories") + "kcal")

        }

        proteinText = findViewById<TextView>(R.id.textProtein)
        if(intent.getStringExtra("proteins_100g") != null){
            proteinText.setText("Proteins: "+intent.getStringExtra("proteins_100g") + "g")
        }

        caffeineText = findViewById<TextView>(R.id.textCaffeine)
        if(intent.getStringExtra("caffeine_100g") != null){
            caffeineText.setText("Caffeine: "+intent.getStringExtra("caffeine_100g")+ "g")
        }

        fatText = findViewById<TextView>(R.id.textFat)
        if(intent.getStringExtra("sodium_100g") != null){
            fatText.setText("Sodium: "+intent.getStringExtra("sodium_100g")+ "g")
        }

        ingredientsListText = findViewById<TextView>(R.id.textIngredientsList)
        if(intent.getStringExtra("ingredients_text")!= null){
            ingredientsListText.setText(intent.getStringExtra("ingredients_text"))
        }

        val inflater:LayoutInflater = layoutInflater
        //inflater.inflate(R.layout.te, nutritional_info)
        val list = listOf( listOf("Processing level: ", intent.getStringExtra("nova_group"), ""),
            listOf("Vitamin A: ", intent.getStringExtra("vitamin-a_100g"), "g"))
        for(element in list){
            if(intent.getStringExtra(element[0])!=null){

            }
        }
        */


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.share->{
                val intent = Intent(applicationContext, SharingActivity::class.java).apply {}
                intent.putExtra("product_name", intent.getStringExtra("product_name"))
                startActivity(intent)
            }



        }
        return super.onOptionsItemSelected(item)
    }

}