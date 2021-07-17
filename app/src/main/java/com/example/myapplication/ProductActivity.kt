package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

class ProductActivity : AppCompatActivity() {
    private lateinit var productNameText: EditText
    private lateinit var servingSizeText: EditText
    private lateinit var caloriesText: EditText
    private lateinit var proteinText: EditText
    private lateinit var caffeineText: EditText
    private lateinit var fatText: EditText
    private lateinit var ingredientsListText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        productNameText = findViewById<EditText>(R.id.textProductName)
        productNameText.setText(intent.getStringExtra("product_name"))

        servingSizeText = findViewById<EditText>(R.id.textServingSize)
        servingSizeText.setText(intent.getStringExtra("serving_size"))

        caloriesText = findViewById<EditText>(R.id.textCalories)
        caloriesText.setText("Calories: "+intent.getStringExtra("energy-kcal_100g") + "kcal")

        proteinText = findViewById<EditText>(R.id.textProtein)
        proteinText.setText("Proteins: "+intent.getStringExtra("proteins_100g") + "g")

        caffeineText = findViewById<EditText>(R.id.textCaffeine)
        caffeineText.setText("Caffeine: "+intent.getStringExtra("caffeine_100g")+ "g")

        fatText = findViewById<EditText>(R.id.textFat)
        fatText.setText("Sodium: "+intent.getStringExtra("sodium_100g")+ "g")

        ingredientsListText = findViewById<EditText>(R.id.textIngredientsList)
        ingredientsListText.setText(intent.getStringExtra("ingredients_text"))
    }
}