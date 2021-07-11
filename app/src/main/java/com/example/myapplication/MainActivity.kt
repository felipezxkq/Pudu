package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




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


}