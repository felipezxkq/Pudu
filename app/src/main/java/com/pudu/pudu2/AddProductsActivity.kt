package com.pudu.pudu2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pudu.pudu2.models.IngredientData
import com.pudu.pudu2.view.IngredientAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore

class AddProductsActivity : AppCompatActivity() {
    private lateinit var addsBtn:FloatingActionButton
    private lateinit var recv:RecyclerView
    private lateinit var ingredientsList:ArrayList<IngredientData>
    private lateinit var ingredientAdapter:IngredientAdapter
    private lateinit var product_data: MutableMap<String, String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_products)



        addsBtn = findViewById(R.id.addingBtn)
        recv = findViewById(R.id.ingredientsRecycler)
        ingredientsList = ArrayList()
        ingredientAdapter = IngredientAdapter(this, ingredientsList)
        recv.layoutManager = LinearLayoutManager(this)
        recv.adapter = ingredientAdapter
        addsBtn.setOnClickListener{ addIngredient() }
        product_data = mutableMapOf()

        val code = intent.getStringExtra("code")

        fun saveFireStore(){
            val productName = findViewById<TextView>(R.id.textEditProductName)
            val productNameText = productName.text.toString()
            val db = FirebaseFirestore.getInstance()
            var product = hashMapOf(
                "code" to code
            )
            for(element in product_data){
                if(element.value!=""){
                    product.put(element.key, element.value)
                }
            }
            product.put("product_name_lc", productNameText.lowercase())


            db.collection("Products").add(product).addOnSuccessListener {
                Toast.makeText(this@AddProductsActivity, "Record added successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this@AddProductsActivity, "Record failed to add", Toast.LENGTH_SHORT).show()
            }

        }

        // Gets all product data from text inputs
        fun getProductData() {
            var data = findViewById<EditText>(R.id.textEditProductName).text.toString()
            product_data.put("product_name", data)

            data = findViewById<EditText>(R.id.textServingSize).text.toString()
            val data2:String = findViewById<EditText>(R.id.textServingType).text.toString()
            data = data + " " + data2
            product_data.put("serving_size", data)

            data = findViewById<EditText>(R.id.textCalories).text.toString()
            product_data.put("calories", data)

            data = findViewById<EditText>(R.id.textProteins).text.toString()
            product_data.put("proteins", data)

            data = findViewById<EditText>(R.id.textSodium).text.toString()
            product_data.put("sodium", data)

            data = findViewById<EditText>(R.id.textCaffeine).text.toString()
            product_data.put("caffeine", data)

            data = findViewById<EditText>(R.id.textTotalFat).text.toString()
            product_data.put("total_fat", data)

            data = findViewById<EditText>(R.id.textPolyinsaturatedFat).text.toString()
            product_data.put("polyunsaturated_fat", data)

            data = findViewById<EditText>(R.id.textMonoUnsaturatedFat).text.toString()
            product_data.put("monounsaturated_fat", data)

            data = findViewById<EditText>(R.id.textCholesterol).text.toString()
            product_data.put("cholesterol", data)

            data = findViewById<EditText>(R.id.textOmega3).text.toString()
            product_data.put("omega_3", data)

            data = findViewById<EditText>(R.id.textOmega6).text.toString()
            product_data.put("omega_6", data)

            data = findViewById<EditText>(R.id.textOmega9).text.toString()
            product_data.put("omega_9", data)

            data = findViewById<EditText>(R.id.textCarbohydrates).text.toString()
            product_data.put("carbohydrates", data)

            data = findViewById<EditText>(R.id.textSugar).text.toString()
            product_data.put("sugar", data)

            data = findViewById<EditText>(R.id.textVitaminA).text.toString()
            product_data.put("vitamin_a", data)

            data = findViewById<EditText>(R.id.textVitaminB1).text.toString()
            product_data.put("vitamin_b1", data)

            data = findViewById<EditText>(R.id.textVitaminB2).text.toString()
            product_data.put("vitamin_b2", data)

            data = findViewById<EditText>(R.id.textVitaminB6).text.toString()
            product_data.put("vitamin_b6", data)

            data = findViewById<EditText>(R.id.textVitaminB9).text.toString()
            product_data.put("vitamin_b9", data)

            data = findViewById<EditText>(R.id.textVitaminB12).text.toString()
            product_data.put("vitamin_b12", data)

            data = findViewById<EditText>(R.id.textBiotin).text.toString()
            product_data.put("biotin", data)

            data = findViewById<EditText>(R.id.vitaminCtext).text.toString()
            product_data.put("vitamin_c", data)

            data = findViewById<EditText>(R.id.textVitaminD).text.toString()
            product_data.put("vitamin_d", data)

            data = findViewById<EditText>(R.id.textVitaminK).text.toString()
            product_data.put("vitamin_k", data)

            data = findViewById<EditText>(R.id.vitaminPPtext).text.toString()
            product_data.put("vitamin_pp", data)

            data = findViewById<EditText>(R.id.textPotassium).text.toString()
            product_data.put("potassium", data)

            data = findViewById<EditText>(R.id.textCalcium).text.toString()
            product_data.put("calcium", data)

            data = findViewById<EditText>(R.id.textPhosphorus).text.toString()
            product_data.put("phosphorus", data)

            data = findViewById<EditText>(R.id.textIron).text.toString()
            product_data.put("iron", data)

            data = findViewById<EditText>(R.id.textMagnesium).text.toString()
            product_data.put("magnesium", data)

            data = findViewById<EditText>(R.id.textZinc).text.toString()
            product_data.put("zinc", data)

            data = findViewById<EditText>(R.id.textCopper).text.toString()
            product_data.put("copper", data)

            data = findViewById<EditText>(R.id.textManganese).text.toString()
            product_data.put("manganese", data)

            data = findViewById<EditText>(R.id.textSelenium).text.toString()
            product_data.put("selenium", data)

            data = findViewById<EditText>(R.id.textChromium).text.toString()
            product_data.put("chromium", data)

            data = findViewById<EditText>(R.id.textMolybdenum).text.toString()
            product_data.put("molybdenum", data)

            data = findViewById<EditText>(R.id.textIodine).text.toString()
            product_data.put("iodine", data)
        }

        val buttonRegisterProduct =findViewById<Button>(R.id.registerProductButton)
        buttonRegisterProduct.setOnClickListener{
            getProductData()
            saveFireStore()
            val intentSearch = Intent(this,SearchActivity::class.java)
            intentSearch.putExtra("code",code)
            startActivity(intentSearch)
        }

    }

    fun addIngredient() {
        val inflater = LayoutInflater.from(this)
        val v = inflater.inflate(R.layout.add_ingredient_item, null)
        val ingredientName = v.findViewById<EditText>(R.id.ingredientName)

        val addDialog = AlertDialog.Builder(this)

        addDialog.setView(v)
        addDialog.setPositiveButton("ok"){
                dialog,_->
            val name = ingredientName.text.toString()
            ingredientsList.add(IngredientData(name))
            ingredientAdapter.notifyDataSetChanged()
            Toast.makeText(this, "Adding ingredient", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel"){
                dialog,_->
            dialog.dismiss()
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
        }
        addDialog.create()
        addDialog.show()
    }






}