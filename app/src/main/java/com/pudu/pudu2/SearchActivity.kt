package com.pudu.pudu2

import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.pudu.pudu2.databinding.ActivitySearchBinding
import com.pudu.pudu2.models.SearchModel
import com.facebook.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.integration.android.IntentIntegrator
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.hudbuttons.*


class SearchActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySearchBinding

    private var searchList: List<SearchModel> = ArrayList()
    private val searchListAdapter = SearchListAdapter(searchList)
    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar = findViewById(R.id.toolbar)
        toolbar?.title = "Pudu"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val btnScanner: ImageButton = findViewById(R.id.btnScanner)
        btnScanner.setOnClickListener { initScanner() }

        btnSearch.setOnClickListener{

        }

        btnHome.setOnClickListener{
            val intent = Intent(applicationContext, HomeActivity::class.java).apply {}
            startActivity(intent)
        }

        /*
        val addProductBtn = findViewById<Button>(R.id.addProductBtn)
        addProductBtn.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        */

        search_list.hasFixedSize()
        search_list.layoutManager = LinearLayoutManager(this)
        search_list.adapter = searchListAdapter

        textSearch.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val searchText: String = textSearch.text.toString()
                if(searchText != ""){
                    searchInFirestore(searchText.lowercase())
                }

            }

        })



    }

    private fun searchInFirestore(searchText: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("Products").orderBy("product_name_lc")
            .startAt(searchText).endAt("$searchText\uf8ff").get().addOnCompleteListener{
                if(it.isSuccessful){
                    searchList = it.result!!.toObjects(SearchModel::class.java)
                    searchListAdapter.searchList = searchList
                    searchListAdapter.notifyDataSetChanged()
                }else{
                    Log.d(TAG, "Error: ${it.exception!!.message}")
                }
            }

    }

    public fun initScanner(){
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("Puduuuuu!!!!!")
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "El valor escaneado es: " + result.contents, Toast.LENGTH_LONG).show()
                returnProducts(result.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }





    }

    private fun returnProducts(code: String){
        val db = FirebaseFirestore.getInstance()
        var productsRef = db.collection("Products");
        var query = productsRef.whereEqualTo("code", code).get()
            .addOnSuccessListener { documents ->
                if(!documents.isEmpty()){
                    val product_data = documents.first()
                    val intent = Intent(this, ProductActivity::class.java)
                    val product_100g_map:Map<String, String> = mapOf("Product name" to "product_name", "Serving size" to "serving_size", "Ingredients" to "ingredients_text",
                        "Calories" to "energy-kcal_100g", "Fat" to "fat_100g", "Proteins" to "proteins_100g", "Sugar" to "sugar_100g",
                        "Carbohydrates" to "carbohydrates_100g", "Traces" to "traces","Packaging" to "packaging","Packaging tags" to "packaging_tags",
                        "Vitamin C" to "vitamin_c_100g", "Alcohol" to "alcohol_100g", "Vitamin A" to "vitamin-a_100g", "Vitamin D" to "vitamin-d_100g",
                        "Vitamin K" to "vitamin-k_100g", "Vitamin B1" to "vitamin-b1_100g", "Vitamin B2" to "vitamin-b2_100g","Vitamin PP" to "vitamin-pp_100g",
                        "Vitamin B6" to "vitamin-b6_100g", "Vitamin B9" to "vitamin-b9_100g", "Vitamin B12" to "vitamin-b12_100g",
                        "Biotin" to "biotin_100g", "Panthotenic Acid" to "pantothenic_acid_100g","Potassium" to "potassium_100g", "Calcium" to "calcium_100g",
                        "Phosphorus" to "phosphorus_100g", "Iron" to "iron_100g", "Magnesium" to "magnesium_100g", "Zinc" to "zinc_100g",
                        "Copper" to "copper_100g", "Manganese" to "manganese_100g", "Selenium" to "selenium_100g", "Chromium" to "chromium_100g", "Molybdenum" to "molybdenum_100g",
                         "Iodine" to "iodine_100g","Sodium" to "sodium_100g", "Caffeine" to "caffeine_100g", "Nova group" to "nova_group", "Additives" to "additives_n")

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
                        "Omega 3" to "omega_3", "Omega 6" to "omega_6", "Omage 9" to "omega_9", "Biotin" to "biotin")

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
                    startActivity(intent)
                }
                else{
                    val alertDialog = AlertDialog.Builder(this)
                    alertDialog.setTitle("Product not found")
                    alertDialog.setMessage("Would you like to contribute by adding this product data?")
                        .setPositiveButton("Yes", DialogInterface.OnClickListener{
                            dialog, id -> goToAddProduct(code)
                        })
                        .setNegativeButton("No", DialogInterface.OnClickListener{
                                dialog, id -> dialog.cancel()
                        })
                    alertDialog.show()
                }

            }
            .addOnFailureListener { exception ->
            }
    }

    private fun goToAddProduct(code: String){
        val intent = Intent(this,AddProductsActivity::class.java)
        intent.putExtra("code",code)
        startActivity(intent)
    }

}