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


class SendDataMember : AppCompatActivity() {
        private lateinit var editTextIdMember       : EditText
        private lateinit var action                 : String
        private lateinit var editTextNamaMember     : EditText
        private lateinit var editTextJenisKelamin   : EditText
        private lateinit var editTextNomorTelp      : EditText
        private lateinit var buttonAdd          : Button
        private lateinit var buttonEdit         : ImageButton
        private lateinit var buttonDelete       : ImageButton
        private lateinit var buttonBack         : ImageButton

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_send_data_member)

            editTextIdMember          = findViewById(R.id.editTextIdMember)
            editTextNamaMember          = findViewById(R.id.editTextNamaMember)
            editTextJenisKelamin        = findViewById(R.id.editTextJenisKelamin)
            editTextNomorTelp           = findViewById(R.id.editTextNomorTelp)

            buttonAdd           = findViewById(R.id.buttonAddMember)
            buttonEdit          = findViewById(R.id.buttonEdit)
            buttonDelete        = findViewById(R.id.buttonDelete)
            buttonBack          = findViewById(R.id.buttonBack)

            val bundle = intent.extras
            if (bundle!=null) { //ketika ada isi dari intent akan masuk ke dalam aksi if
                editTextIdMember.setText(bundle.getString("id_member"))
                editTextNamaMember.setText(bundle.getString("nama_member"))
                editTextJenisKelamin.setText(bundle.getString("jenis_kelamin"))
                editTextNomorTelp.setText(bundle.getString("nomor_telepon"))
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
                val intent = Intent(this@SendDataMember, ViewDataMember::class.java)
                startActivity(intent)
                finish()
            }
        }

        private fun kirimdata() {
            val url: String = AppConfig().IP_SERVER + "/perpustakaan_web/send_data_member.php"
            val stringRequest = object : StringRequest(Method.POST,url,
                Response.Listener { response ->
                    Log.d("Response","Server respond:$response")
                    try {
                        val jsonObj = JSONObject(response)
                        Toast.makeText(this,jsonObj.getString("error_text"),Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@SendDataMember, ViewDataMember::class.java)
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
                    params["id_member"]         = editTextIdMember.text.toString()
                    params["nama_member"]       = editTextNamaMember.text.toString()
                    params["jenis_kelamin"]     = editTextJenisKelamin.text.toString()
                    params["nomor_telepon"]     = editTextNomorTelp.text.toString()
                    params["action"]            = action
                    return params
                }
            }
            Volley.newRequestQueue(this).add(stringRequest)
        }
    }
