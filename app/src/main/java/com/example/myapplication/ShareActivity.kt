package com.example.myapplication

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.app.ActivityManagerCompat

class ShareActivity : AppCompatActivity() {

    var toolbar: Toolbar? = null
    val SOLICITUD_TOMAR_FOTO = 1
    val permisoCamara = android.Manifest.permission.CAMERA
    val permisoWriteStorage = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    val permisoReadStorage = android.Manifest.permission.READ_EXTERNAL_STORAGE

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)

        toolbar = findViewById(R.id.toolbar)
        toolbar?.title = "Pudu"
        setSupportActionBar(toolbar)

        val bTomar = findViewById<Button>(R.id.bTomar)

        bTomar.setOnClickListener{
            dispararIntentTomarFoto()
            pedirPermisos()
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun pedirPermisos(){
        val deboProveerContexto = ActivityCompat.shouldShowRequestPermissionRationale(this, permisoCamara)

        if(deboProveerContexto){
            solicitudPermisos()
        }else{
            solicitudPermisos()
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun solicitudPermisos(){
        requestPermissions(arrayOf(permisoCamara, permisoWriteStorage, permisoReadStorage), SOLICITUD_TOMAR_FOTO)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            SOLICITUD_TOMAR_FOTO ->{
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //tenemos permiso
                    dispararIntentTomarFoto()
                }else{
                    //no tenemos permiso
                    Toast.makeText(this, "No permission to access camera and storage", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    fun dispararIntentTomarFoto(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if(intent.resolveActivity(packageManager) != null){
            startActivityForResult(intent, SOLICITUD_TOMAR_FOTO)
        }
    }
}