package com.pudu.pudu2

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.facebook.share.model.SharePhoto
import com.facebook.share.model.ShareStoryContent
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_sharing.*
import kotlinx.android.synthetic.main.hudbuttons.*


class SharingActivity : AppCompatActivity() {

    var imagePicker: ImageView? = null
    var toolbar: Toolbar? = null

    private lateinit var texto: TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sharing)

        //texto = findViewById(R.id.textView123)
        //texto.setText(intent.getStringExtra("product_name"))

        toolbar = findViewById(R.id.toolbar)
        toolbar?.title = "Pudu"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)





        imagePicker = findViewById<FloatingActionButton>(R.id.pickerImage)

        val galleryButton = findViewById<FloatingActionButton>(R.id.galleryButton)
        val cameraButton = findViewById<ImageButton>(R.id.cameraButton)

        ImagePicker.with(this).saveDir(getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!)

        btnSearch.setOnClickListener{
            val intent = Intent(applicationContext, SearchActivity::class.java).apply {}
            startActivity(intent)
        }

        btnHome.setOnClickListener{
            val intent = Intent(applicationContext, HomeActivity::class.java).apply {}
            startActivity(intent)
        }

        btnScanner.setOnClickListener{
            val intent = Intent(applicationContext, SearchActivity::class.java).apply {}
            startActivity(intent)
        }

        galleryButton.setOnClickListener{

            ImagePicker.with(this).galleryOnly().galleryMimeTypes(arrayOf("image/*")).crop()
                .maxResultSize(640,480).start()

        }

        cameraButton.setOnClickListener{

            ImagePicker.with(this)
                .cameraOnly()
                .crop()
                .maxResultSize(920,1280)
                .start()
        }



        val chatShareBtn = findViewById<ImageButton>(R.id.chatShareBtn)
        chatShareBtn.setOnClickListener {

            val textContent = findViewById<TextInputEditText>(R.id.shareText)
            val text = textContent.getText().toString()
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, text)
            intent.type = "text/plain"

            startActivity(Intent.createChooser(intent, "Please select app: "))
        }
/*
        val facebookShareBtn = findViewById<ImageButton>(R.id.facebookShareBtn)
        facebookShareBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Aún no sirvo para nada")
            builder.setMessage("Sorry")
            builder.setPositiveButton("Accept", null)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
*/


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && requestCode == ImagePicker.REQUEST_CODE){

            imagePicker?.setImageURI(data?.data)
        }

    }




}