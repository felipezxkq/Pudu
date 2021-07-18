package com.pudu.pudu2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_product.*


class ProductActivity : AppCompatActivity() {
    private lateinit var productNameText: EditText
    private lateinit var servingSizeText: EditText
    private lateinit var caloriesText: EditText
    private lateinit var proteinText: EditText
    private lateinit var caffeineText: EditText
    private lateinit var fatText: EditText
    private lateinit var ingredientsListText: EditText
    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        toolbar = findViewById(R.id.toolbar)
        toolbar?.title = "Pudu"
        setSupportActionBar(toolbar)



        productNameText = findViewById<EditText>(R.id.textProductName)
        productNameText.setText(intent.getStringExtra("product_name"))

        servingSizeText = findViewById<EditText>(R.id.textServingSize)
        servingSizeText.setText(intent.getStringExtra("serving_size"))

        caloriesText = findViewById<EditText>(R.id.textCalories)
        if(intent.getStringExtra("energy-kcal_100g") != null){
            caloriesText.setText("Calories: "+intent.getStringExtra("energy-kcal_100g") + "kcal")
        }else{

        }

        proteinText = findViewById<EditText>(R.id.textProtein)
        if(intent.getStringExtra("proteins_100g") != null){
            proteinText.setText("Proteins: "+intent.getStringExtra("proteins_100g") + "g")
        }

        caffeineText = findViewById<EditText>(R.id.textCaffeine)
        if(intent.getStringExtra("caffeine_100g") != null){
            caffeineText.setText("Caffeine: "+intent.getStringExtra("caffeine_100g")+ "g")
        }

        fatText = findViewById<EditText>(R.id.textFat)
        if(intent.getStringExtra("sodium_100g") != null){
            fatText.setText("Sodium: "+intent.getStringExtra("sodium_100g")+ "g")
        }

        ingredientsListText = findViewById<EditText>(R.id.textIngredientsList)
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

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.share->{
                val intent = Intent(applicationContext, ShareActivity::class.java).apply {}
                startActivity(intent)
            }



        }
        return super.onOptionsItemSelected(item)
    }

}