package com.pudu.pudu2

import android.Manifest
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL
import com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS
import com.arthenica.mobileffmpeg.FFmpeg
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.integration.android.IntentIntegrator
import com.ibm.cloud.sdk.core.http.HttpMediaType
import com.ibm.cloud.sdk.core.security.IamAuthenticator
import com.ibm.watson.speech_to_text.v1.SpeechToText
import com.ibm.watson.speech_to_text.v1.model.RecognizeOptions
import com.pudu.pudu2.databinding.ActivitySearchBinding
import com.pudu.pudu2.models.SearchModel
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.hudbuttons.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread


class SearchActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySearchBinding

    private var searchList: List<SearchModel> = ArrayList()
    private val searchListAdapter = SearchListAdapter(searchList)
    var toolbar: Toolbar? = null

    // Audio permissions
    private val REQUEST_RECORD_AUDIO_PERMISSION = 200
    private val permissions = arrayOf<String>(Manifest.permission.RECORD_AUDIO)
    private var audioRecordingPermissionGranted = false

    // Audio stuff
    private var mediaRecorder: MediaRecorder? = null
    private var recordedFileName: String? = null
    private var convertedFileName: String? = null
    private var mainHandler: Handler? = null
    private var isRecording = false
    private var startRecordingButton: ImageButton? = null
    private val API_KEY = "VRNQcdFO9rLmmFhIsThfLktysyWYcpjIEHNduvtZKTKa"
    private val URL = "https://api.us-south.speech-to-text.watson.cloud.ibm.com/instances/5baa5957-dafe-4fac-a238-b80b77097ce1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar = findViewById(R.id.toolbar)
        toolbar?.title = "Pudu"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)


        // Scanner button
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val btnScanner: ImageButton = findViewById(R.id.btnScanner)
        btnScanner.setOnClickListener { initScanner() }

        // HOME
        btnSearch.setOnClickListener{
        }
        btnHome.setOnClickListener{
            val intent = Intent(applicationContext, HomeActivity::class.java).apply {}
            startActivity(intent)
        }


        // Search functionality
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

        // Voice to text search
        mainHandler = Handler()
        isRecording = false
        val mediaRecorder = MediaRecorder()
        requestPermissions(permissions, REQUEST_RECORD_AUDIO_PERMISSION)
        startRecordingButton = findViewById(R.id.activity_main_recordSpeech);
        startRecordingButton?.setOnClickListener{
            if(!isRecording){
                if(audioRecordingPermissionGranted){
                    try{
                        startAudioRecording()
                    }catch(e:Exception){

                    }
                }
            }else{
                thread(start = true) {
                    convertSpeech()
                }
            }
        }

    }

    private fun toggleRecording(){
        isRecording = !isRecording
        mainHandler!!.post {
            if (isRecording) {
                startRecordingButton!!.setBackgroundResource(R.mipmap.mic_on)
            } else {
                startRecordingButton!!.setBackgroundResource(R.mipmap.mic_blocked)
            }
        }
    }

    private fun startAudioRecording(){
        toggleRecording()
        val uuid: String = UUID.randomUUID().toString()
        recordedFileName = filesDir.path + "/" + uuid + ".3gp"
        convertedFileName = filesDir.path + "/" + uuid + ".mp3"

        mediaRecorder = MediaRecorder()
        mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        mediaRecorder!!.setOutputFile(recordedFileName)

        mediaRecorder!!.prepare()
        mediaRecorder!!.start()
    }

    private fun convertSpeech(){
        toggleRecording()
        mediaRecorder!!.stop();
        mediaRecorder!!.reset();
        mediaRecorder!!.release();

        val rc = FFmpeg.execute(
            String.format(
                "-i %s -c:a libmp3lame %s",
                recordedFileName,
                convertedFileName
            )
        )

        if (rc == RETURN_CODE_SUCCESS) {
            println("RECORD SUCCESS")
            //convertedFileName?.let { playRecording(it) };
            val authenticator: IamAuthenticator = IamAuthenticator(API_KEY)
            val speechToText: SpeechToText = SpeechToText(authenticator)
            speechToText.serviceUrl = URL

            val audioFile: File = File(convertedFileName)


            val options: RecognizeOptions = RecognizeOptions.Builder()
                .audio(audioFile)
                .contentType(HttpMediaType.AUDIO_MP3)
                .model("en-AU_NarrowbandModel")
                .build()


            val transcript = speechToText.recognize(options).execute().result
            println("RESULTADOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOSADASDAS")
            if(transcript.results.size>0){
                println(transcript.results.get(0))
                val confidence: Double = transcript.results.get(0).alternatives.get(0).confidence
                val result: String = transcript.results.get(0).alternatives.get(0).transcript
                if(confidence>0.4){
                    textSearch.setText(result)
                }else{
                    runOnUiThread(Runnable() {
                        run() {
                            Toast.makeText(this, "Sorry I didn't understand. Please try again", Toast.LENGTH_LONG).show()
                        }
                    })
                }

            }else{
                runOnUiThread(Runnable() {
                    run() {
                        Toast.makeText(this, "Did you say something? Please try again", Toast.LENGTH_LONG).show()
                    }
                })
            }

        }else if(rc == RETURN_CODE_CANCEL){
            // canceled by user
        }else{
            // error
        }

    }

    private fun playRecording(fileName: String){
        val player = MediaPlayer()
        player.setDataSource(fileName)
        player.prepare()
        player.start()
    }

    private fun searchInFirestore(searchText: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("Products").orderBy("product_name_lc")
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

    public fun initScanner(){
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
    }

    private fun returnProducts(code: String){
        val db = FirebaseFirestore.getInstance()
        var productsRef = db.collection("Products");
        var query = productsRef.whereEqualTo("code", code).get()
            .addOnSuccessListener { documents ->
                if(!documents.isEmpty()){
                    val product_data = documents.first()
                    val intent = Intent(this, ProductActivity::class.java)
                    val product_100g_map:Map<String, String> = mapOf("Product name" to "product_name", "Serving size" to "serving_size", "Ingredients" to "ingredients_text",
                        "Calories" to "energy-kcal_100g", "Fat" to "fat_100g", "Proteins" to "proteins_100g", "Sugar" to "sugar_100g",
                        "Carbohydrates" to "carbohydrates_100g", "Traces" to "traces","Packaging" to "packaging","Packaging tags" to "packaging_tags",
                        "Vitamin C" to "vitamin_c_100g", "Alcohol" to "alcohol_100g", "Vitamin A" to "vitamin-a_100g", "Vitamin D" to "vitamin-d_100g",
                        "Vitamin K" to "vitamin-k_100g", "Vitamin B1" to "vitamin-b1_100g", "Vitamin B2" to "vitamin-b2_100g","Vitamin PP" to "vitamin-pp_100g",
                        "Vitamin B6" to "vitamin-b6_100g", "Vitamin B9" to "vitamin-b9_100g", "Vitamin B12" to "vitamin-b12_100g",
                        "Biotin" to "biotin_100g", "Panthotenic Acid" to "pantothenic_acid_100g","Potassium" to "potassium_100g", "Calcium" to "calcium_100g",
                        "Phosphorus" to "phosphorus_100g", "Iron" to "iron_100g", "Magnesium" to "magnesium_100g", "Zinc" to "zinc_100g",
                        "Copper" to "copper_100g", "Manganese" to "manganese_100g", "Selenium" to "selenium_100g", "Chromium" to "chromium_100g", "Molybdenum" to "molybdenum_100g",
                         "Iodine" to "iodine_100g","Sodium" to "sodium_100g", "Caffeine" to "caffeine_100g", "Nova group" to "nova_group", "Additives" to "additives_n"
                    , "produced_per_footprint" to "produced_per_footprint", "Carbon footprint" to "carbon_footprint", "Water usage" to "water_usage", "Land usage" to "land_usage"
                    , "Comments" to "comments_en")

                    val product_serving_map:Map<String, String> = mapOf("Product name" to "product_name", "Serving size" to "serving_size", "Ingredients" to "ingredients_text",
                        "Traces" to "traces","Packaging" to "packaging","Packaging tags" to "packaging_tags",
                        "Nova group" to "nova_group", "Additives" to "additives_n",
                        "Ingredients from palm oil" to "ingredients_from_palm_oil_n", "Nutriscore" to "nutriscore_score", "Nutriscore grade" to "nutriscore_grade",
                        "Calories" to "calories","Protein" to "proteins","Sugar" to "sugar","Carbohydrates" to "carbohydrates","Vitamin C" to "vitamin_c", "Vitamin A" to "vitamin_a",
                        "Vitamin D" to "vitamin_d", "Vitamin K" to "vitamin_k", "Vitamin B1" to "vitamin_b1", "Vitamin B2" to "vitamin_b2", "Vitamin B6" to "vitamin_b6",
                        "Vitamin B9" to "vitamin_b9", "Vitamin B12" to "vitamin_b12", "Vitamin PP" to "vitamin_pp", "Potassium" to "potassium", "Calcium" to "calcium",
                        "Phosphorus" to "phosphorus", "Iron" to "iron", "Magnesium" to "magnesium", "Zinc" to "zinc","Copper" to "copper", "Manganese" to "manganese",
                        "Selenium" to "selenium", "Chromium" to "chromium", "Molybdenum" to "molybdenum", "Iodine" to "iodine", "Sodium" to "sodium", "Caffeine" to "caffeine",
                        "Total fat" to "total_fat", "Polyunsaturated fat" to "polyunsaturated_fat", "Monounsaturated fat" to "monounsaturated_fat", "Cholesterol" to "cholesterol",
                        "Omega 3" to "omega_3", "Omega 6" to "omega_6", "Omega 9" to "omega9", "Biotin" to "biotin", "Poto" to "poto"
                        , "produced_per_footprint" to "produced_per_footprint", "Carbon footprint (kg of CO2)" to "carbon_footprint", "Water usage (lts)" to "water_usage", "Land usage (m2)" to "land_usage"
                        , "Comments" to "comments_en")

                    if(product_data.contains("energy-kcal_100g") && product_data.getString("energy-kcal_100g") != ""){
                        intent.putExtra("serving_type", "per 100g")
                        for (item in product_100g_map){
                            intent.putExtra(item.key, product_data.getString(item.value))
                        }
                    }
                    else{
                        intent.putExtra("serving_type", "per serving")
                        for (item in product_serving_map){
                            intent.putExtra(item.key, product_data.getString(item.value))
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


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
        ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_RECORD_AUDIO_PERMISSION -> audioRecordingPermissionGranted =
                grantResults[0] == PackageManager.PERMISSION_GRANTED
        }
        if (!audioRecordingPermissionGranted) {
            finish()
        }
    }

}