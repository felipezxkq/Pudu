package com.pudu.pudu2

import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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


class SearchActivity : AppCompatActivity() {

    //private var callbackManager:CallbackManager? = null
    private lateinit var binding:ActivitySearchBinding

    private var searchList: List<SearchModel> = ArrayList()
    private val searchListAdapter = SearchListAdapter(searchList)
    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar = findViewById(R.id.toolbar)
        toolbar?.title = "Pudu"
        setSupportActionBar(toolbar)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnScanner.setOnClickListener { initScanner() }

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


/*
        val shareBtn = findViewById<Button>(R.id.shareBtn)
        shareBtn.setOnClickListener {

            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            //intent.putExtra(Intent.EXTRA_TEXT, nombreProducto)
            intent.type = "text/plain"

            startActivity(Intent.createChooser(intent, "Please select app: "))
        }

        callbackManager = CallbackManager.Factory.create();

        val loginButton = findViewById<LoginButton>(R.id.login_button)
        loginButton.setReadPermissions("email")


        // If using in a fragment
        //loginButton.setFragment(this)

        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                var accessToken = AccessToken.getCurrentAccessToken()
                var isLoggedIn = accessToken != null && !accessToken!!.isExpired
                Log.d("ACCESS-TOKEN", accessToken.token)
            }

            override fun onCancel() {
                // App code
            }

            override fun onError(exception: FacebookException) {
                // App code
            }
        })
*/
    }

    private fun searchInFirestore(searchText: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("Products").orderBy("product_name")
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

    private fun initScanner(){
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

        //callbackManager?.onActivityResult(requestCode, resultCode, data)
        //super.onActivityResult(requestCode, resultCode, data)

    }

    private fun returnProducts(code: String){
        val db = FirebaseFirestore.getInstance()
        var productsRef = db.collection("Products");
        var query = productsRef.whereEqualTo("code", code).get()
            .addOnSuccessListener { documents ->
                if(!documents.isEmpty()){
                    val product_data = documents.first()
                    val intent = Intent(this, ProductActivity::class.java)
                    val list = listOf("product_name", "serving_size", "ingredients_text", "energy-kcal_100g", "fat_100g", "proteins_100g", "sugar_100g",
                        "carbohydrates_100g", "traces", "packaging", "packaging_tags", "vitamin_c_100g", "alcohol_100g", "vitamin-a_100g", "vitamin-d_100g",
                        "vitamin-k_100g", "vitamin-b1_100g", "vitamin-b2_100g", "vitamin-pp_100g", "vitamin-b6_100g", "vitamin-b9_100g", "vitamin-b12_100g",
                        "biotin_100g", "pantothenic_acid_100g", "potassium_100g", "calcium_100g", "phosphorus_100g", "iron_100g", "magnesium_100g", "zinc_100g",
                        "copper_100g", "manganese_100g", "selenium_100g", "chromium_100g", "molybdenum_100g", "iodine_100g",
                    "sodium_100g", "caffeine_100g", "nova_group", "additives_n", "ingredients_from_palm_oil_n", "nutriscore_score", "nutriscore_grade")
                    for (item in list){
                        if(product_data.contains(item)){
                            intent.putExtra(item, product_data.getString(item))
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