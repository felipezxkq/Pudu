package com.pudu.pudu2.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.pudu.pudu2.LoginActivity
import com.pudu.pudu2.R
import com.pudu.pudu2.SearchActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        startTimer()






    }

    fun startTimer(){
        object: CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
                val intent = Intent(applicationContext, LoginActivity::class.java).apply {}
                startActivity(intent)
                finish()
            }
        }.start()
    }
}