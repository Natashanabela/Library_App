package com.example.perpustakaan

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ViewDataKategori : AppCompatActivity() {
    private lateinit var listviewkategori: ListView
    private lateinit var buttonBack   : ImageButton
    private var arraylist_data_kategori = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_data_kategori)
        listviewkategori = findViewById(R.id.listViewdatakategori)
        buttonBack = findViewById(R.id.buttonBack)

        // Checking if listviewkategori is not null before calling getData
        if (listviewkategori != null) {
            getData()
        }

        listviewkategori.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this@ViewDataKategori, SendDataKategori::class.java)
            intent.putExtra("id_kategori", arraylist_data_kategori[position].id_kategori)
            intent.putExtra("nama_kategori", arraylist_data_kategori[position].nama_kategori)
            startActivity(intent)
            finish()
        }

        buttonBack.setOnClickListener {
            val intent = Intent(this@ViewDataKategori, PageBuku::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getData() {
        val url: String = AppConfig().IP_SERVER + "/perpustakaan_web/view_data_kategori.php"
        val stringRequest = object : StringRequest(Method.GET, url,
            Response.Listener { response ->
                try {
                    val jsonObj = JSONObject(response)
                    if (jsonObj.has("data")) {
                        val jsonArray: JSONArray = jsonObj.getJSONArray("data")
                        var product: Product

                        arraylist_data_kategori.clear()

                        for (i in 0 until jsonArray.length()) {
                            val items = jsonArray.getJSONObject(i)
                            product = Product()
                            product.id_kategori = items.getString("id_kategori")
                            product.nama_kategori = items.getString("nama_kategori")
                            arraylist_data_kategori.add(product)
                        }

                        listviewkategori.adapter = Adapter_Product_Kategori(this@ViewDataKategori, arraylist_data_kategori)
                    } else {
                        Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error parsing JSON", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "${error.message}", Toast.LENGTH_SHORT).show()
            }

        ) {}
        Volley.newRequestQueue(this).add(stringRequest)
    }
}