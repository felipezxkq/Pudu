package com.example.myapplication

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
import com.example.myapplication.models.IngredientData
import com.example.myapplication.view.IngredientAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var addsBtn:FloatingActionButton
    private lateinit var recv:RecyclerView
    private lateinit var ingredientsList:ArrayList<IngredientData>
    private lateinit var ingredientAdapter:IngredientAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addsBtn = findViewById(R.id.addingBtn)
        recv = findViewById(R.id.ingredientsRecycler)
        ingredientsList = ArrayList()
        ingredientAdapter = IngredientAdapter(this, ingredientsList)
        recv.layoutManager = LinearLayoutManager(this)
        recv.adapter = ingredientAdapter
        addsBtn.setOnClickListener{ addIngredient() }




        fun saveFireStore(){
            val productName = findViewById<TextView>(R.id.editTextProductName)
            val productNameText = productName.text.toString()
            val db = FirebaseFirestore.getInstance()
            var product: MutableMap<String, Any> = HashMap()
            product["productName"] = productNameText
            db.collection("Product").add(product).addOnSuccessListener {
                Toast.makeText(this@MainActivity, "Record added successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this@MainActivity, "Record failed to add", Toast.LENGTH_SHORT).show()
            }

        }

        val buttonRegisterProduct =findViewById<Button>(R.id.registerProductButton)
        buttonRegisterProduct.setOnClickListener{
            saveFireStore()
        }
    }

    private fun addIngredient() {
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