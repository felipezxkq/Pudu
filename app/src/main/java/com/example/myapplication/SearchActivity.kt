package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.databinding.ActivitySearchBinding
import com.facebook.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.integration.android.IntentIntegrator
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton

class SearchActivity : AppCompatActivity() {

    var nombreProducto: String = "Empty"

    private var callbackManager:CallbackManager? = null

    private lateinit var binding:ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnScanner.setOnClickListener { initScanner() }

        val addProductBtn = findViewById<Button>(R.id.addProductBtn)
        addProductBtn.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }


        val shareBtn = findViewById<Button>(R.id.shareBtn)
        shareBtn.setOnClickListener {

            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, nombreProducto)
            intent.type = "text/plain"

            startActivity(Intent.createChooser(intent, "Please select app: "))
        }


        callbackManager = CallbackManager.Factory.create();

        val loginButton = findViewById<LoginButton>(R.id.login_button)
        loginButton.setReadPermissions("email")
        // If using in a fragment
        // If using in a fragment
        //loginButton.setFragment(this)

        // Callback registration

        // Callback registration
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

        callbackManager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

    }

    private fun returnProducts(code: String){
        val productText = findViewById<EditText>(R.id.editTextProduct)
        val db = FirebaseFirestore.getInstance()
        var productsRef = db.collection("Products");
        var query = productsRef.whereEqualTo("code", code).get()
            .addOnSuccessListener { documents ->
                if(documents.first() != null){
                    productText.setText("${documents.first().data.getValue("product_name")}")
                    nombreProducto = ("${documents.first().data.getValue("product_name")}")

                }
                else{
                    productText.setText("Producto no encontrado!")
                }

            }
            .addOnFailureListener { exception ->
                productText.setText("Error conexión")
            }
    }
}