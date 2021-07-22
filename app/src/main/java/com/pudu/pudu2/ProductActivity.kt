package com.pudu.pudu2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
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
        productNameText.setText(intent.getStringExtra("product_name"))

        servingSizeText = findViewById<TextView>(R.id.textServingSize)
        servingSizeText.setText(intent.getStringExtra("serving_size"))

        caloriesText = findViewById<TextView>(R.id.textCalories)
        if(intent.getStringExtra("energy-kcal_100g") != null){
            caloriesText.setText("Calories: "+intent.getStringExtra("energy-kcal_100g") + "kcal")
        }else{

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