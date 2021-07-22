package com.pudu.pudu2

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
    private lateinit var product_data: MutableList<String>

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
        product_data = mutableListOf()

        val code = intent.getStringExtra("code")

        fun saveFireStore(){
            val productName = findViewById<TextView>(R.id.textEditProductName)
            val productNameText = productName.text.toString()
            val db = FirebaseFirestore.getInstance()
            var product = hashMapOf(
                "product_name" to productNameText,
                "code" to code
            )

            db.collection("Products").add(product).addOnSuccessListener {
                Toast.makeText(this@AddProductsActivity, "Record added successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this@AddProductsActivity, "Record failed to add", Toast.LENGTH_SHORT).show()
            }

        }

        // Gets all product data from text inputs
        fun getProductData() {
            val texts_and_data = listOf(
                listOf("textEditProductName", "product_name"),
                listOf("textServingSize", "serving_size"),
                listOf("textCalories", "calories"),
                listOf("textProteins", "proteins"),
                listOf("textTotalFat", "total_fat"),
                listOf("textPolyunsaturatedFat", "polyunsaturated_fat"),
                listOf("textMonoUnsaturatedFat", "monounsaturated_fat"),
                listOf("textCholesterol", "cholesterol"),
                listOf("textOmega3", "omega_3"),
                listOf("textOmega6", "omega_6"),
                listOf("textOmega9", "omega_9"),
                listOf("textCarbohydrates", "carbohydrates"),
                listOf("textSugar", "sugar"),
                listOf("textVitaminA", "vitamin_a"),
                listOf("textVitaminB1", "vitamin_b1"),
                listOf("textVitaminB2", "vitamin_b2"),
                listOf("textVitaminB6", "vitamin_b6"),
                listOf("textVitaminB9", "vitamin_b9"),
                listOf("textVitaminB12", "vitamin_b12"),
                listOf("textBiotin", "biotin"),
                listOf("vitaminCtext", "vitamin_c"),
                listOf("textVitaminD", "vitamin_d"),
                listOf("textVitaminK", "vitamin_k"),
                listOf("vitaminPPtext", "vitamin_pp"),
                listOf("textPotassium", "potassium"),
                listOf("textCalcium", "calcium"),
                listOf("textPhosphorus", "phosphorus"),
                listOf("textIron", "iron"),
                listOf("textMagnesium", "magnesium"),
                listOf("textZinc", "zinc"),
                listOf("textCopper", "copper"),
                listOf("textManganese", "manganese"),
                listOf("textSelenium", "selenium"),
                listOf("textChromium", "chromium"),
                listOf("textMolybdenum", "molybdenum"),
                listOf("textIodine", "iodine")
            )

            for (item in texts_and_data) {
                println("Texto: ")
                println(item[0])
                val text =
                    resources.getString(resources.getIdentifier(item[0], "EditText", packageName))
                product_data.add(text)
                println(text)
            }
        }

        val buttonRegisterProduct =findViewById<Button>(R.id.registerProductButton)
        buttonRegisterProduct.setOnClickListener{
            getProductData()
            saveFireStore()
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