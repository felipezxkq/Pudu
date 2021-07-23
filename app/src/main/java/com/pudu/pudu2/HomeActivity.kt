package com.pudu.pudu2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.oAuthProvider
import kotlinx.android.synthetic.main.activity_home.*

enum class ProviderType {
    BASIC,
    GOOGLE,
    FACEBOOK
}

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Setup
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val  provider = bundle?.getString("provider")

        setup(email ?:"", provider ?:"")

        //GUARDADO DE DATOS

        val prefs: SharedPreferences.Editor = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.apply()

    }

    private fun setup(email: String, provider: String){
        title = "Home"
        emailTexView.text = email
        providerTextView.text = provider


        val logOutButton = findViewById<Button>(R.id.logOutButton)
        logOutButton.setOnClickListener{

            val prefs: SharedPreferences.Editor = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            if(provider == ProviderType.FACEBOOK.name){
                LoginManager.getInstance().logOut()
            }
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }

        searchButton.setOnClickListener{
            val intent = Intent(applicationContext, SearchActivity::class.java).apply {}
            startActivity(intent)

        }
    }
}