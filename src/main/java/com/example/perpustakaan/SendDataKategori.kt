package com.example.perpustakaan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject


class SendDataKategori : AppCompatActivity() {
    private lateinit var editTextIdKategori       : EditText
    private lateinit var action                 : String
    private lateinit var editTextNamaKategori     : EditText
    private lateinit var buttonAdd          : Button
    private lateinit var buttonEdit         : ImageButton
    private lateinit var buttonDelete       : ImageButton
    private lateinit var buttonBack         : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_data_kategori)

        editTextIdKategori          = findViewById(R.id.editTextIdKategori)
        editTextNamaKategori        = findViewById(R.id.editTextNamaKategori)

        buttonAdd           = findViewById(R.id.buttonAddKategori)
        buttonEdit          = findViewById(R.id.buttonEdit)
        buttonDelete        = findViewById(R.id.buttonDelete)
        buttonBack          = findViewById(R.id.buttonBack)

        val bundle = intent.extras
        if (bundle!=null) { //ketika ada isi dari intent akan masuk ke dalam aksi if
            editTextIdKategori.setText(bundle.getString("id_kategori"))
            editTextNamaKategori.setText(bundle.getString("nama_kategori"))

            buttonAdd.visibility = View.GONE
        }
        else { //sedangkan jika tidak ada data dari intent maka akan masuk ke dalam aski else
            buttonEdit.visibility   = View.GONE
            buttonDelete.visibility = View.GONE
        }

        buttonAdd.setOnClickListener {
            action = "add"
            kirimdata()
        }

        buttonEdit.setOnClickListener {
            action = "edit"
            kirimdata()
        }

        buttonDelete.setOnClickListener {
            action = "delete"
            kirimdata()
        }

        buttonBack.setOnClickListener {
            val intent = Intent(this@SendDataKategori, PageBuku::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun kirimdata() {
        val url: String = AppConfig().IP_SERVER + "/perpustakaan_web/send_data_kategori.php"
        val stringRequest = object : StringRequest(Method.POST,url,
            Response.Listener { response ->
                Log.d("Response","Server respond:$response")
                try {
                    val jsonObj = JSONObject(response)
                    Toast.makeText(this,jsonObj.getString("error_text"),Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@SendDataKategori, ViewDataKategori::class.java)
                    startActivity(intent)
                }catch (e:JSONException){
                    e.printStackTrace()
                    Toast.makeText(this,"invalid respond",Toast.LENGTH_SHORT).show()
                }
                finish()
            },
            Response.ErrorListener { _ ->
                Toast.makeText(this,"Gagal Terhubung",Toast.LENGTH_SHORT).show()
            }
        ){
            override fun getParams(): HashMap<String,String>{
                val params = HashMap<String,String>()
                params["id_kategori"]         = editTextIdKategori.text.toString()
                params["nama_kategori"]       = editTextNamaKategori.text.toString()

                params["action"]            = action
                return params
            }
        }
        Volley.newRequestQueue(this).add(stringRequest)
    }
}