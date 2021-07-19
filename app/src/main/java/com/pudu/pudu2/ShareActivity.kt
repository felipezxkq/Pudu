package com.pudu.pudu2

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ShareActivity : AppCompatActivity() {

    var toolbar: Toolbar? = null
    val SOLICITUD_TOMAR_FOTO = 1
    val permisoCamara = android.Manifest.permission.CAMERA
    val permisoWriteStorage = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    val permisoReadStorage = android.Manifest.permission.READ_EXTERNAL_STORAGE

    var ivFoto:ImageView? = null
    var urlFotoActual = ""

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)

        toolbar = findViewById(R.id.toolbar)
        toolbar?.title = "Pudu"
        setSupportActionBar(toolbar)

        val bTomar = findViewById<Button>(R.id.bTomar)
        ivFoto = findViewById(R.id.ivFoto)

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
                if(grantResults.size > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[2] == PackageManager.PERMISSION_GRANTED){
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

            var archivoFoto:File? = null

            archivoFoto = crearArchivoImagen()

            if(archivoFoto != null){
                val urlFoto = FileProvider.getUriForFile(this, "com.pudu.pudu2", archivoFoto)

                intent.putExtra(MediaStore.EXTRA_OUTPUT, urlFoto)
            }



            startActivityForResult(intent, SOLICITUD_TOMAR_FOTO)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            SOLICITUD_TOMAR_FOTO->{
                if(resultCode == Activity.RESULT_OK){
                    //obtener imagen
                    //Log.d("ACTIVITY_RESULT", resultCode.toString())

                    /*val extras = data?.extras
                    val imageBitmap = extras?.get("data") as Bitmap
                    */

                    val uri = Uri.parse(urlFotoActual)
                    val stream = contentResolver.openInputStream(uri)
                    val imageBitmap = BitmapFactory.decodeStream(stream)
                    ivFoto!!.setImageBitmap(imageBitmap)
                    añadirImagenGaleria()

                }else{
                    //canceló la captura
                }
            }
        }
    }

    fun crearArchivoImagen(): File{
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val nombreArchivoImagen = "JPEG_" + timestamp + " "

        //val directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val directorio = Environment.getExternalStorageDirectory()
        val directorioPictures = File(directorio.absolutePath + "/Pictures/")
        val imagen = File.createTempFile(nombreArchivoImagen, ".jpg", directorioPictures)
        urlFotoActual = "file://" + imagen.absolutePath
        return imagen
    }



    fun añadirImagenGaleria() {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = File(urlFotoActual)
            mediaScanIntent.data = Uri.fromFile(f)
            sendBroadcast(mediaScanIntent)
        }
    }
}