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
import org.json.JSONObject
//buku
class ViewDataBuku : AppCompatActivity() {
    private lateinit var listviewbuku : ListView
    private lateinit var buttonBack   : ImageButton
    var arraylist_data_buku = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_data_buku)
        listviewbuku = findViewById(R.id.listViewdatabuku)
        buttonBack = findViewById(R.id.buttonBack)
        getData()

        listviewbuku.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@ViewDataBuku, SendDataBuku::class.java)
            intent.putExtra("image", arraylist_data_buku[position].image)
            intent.putExtra("id_buku", arraylist_data_buku[position].id_buku)
            intent.putExtra("judul", arraylist_data_buku[position].judul)
            intent.putExtra("pengarang", arraylist_data_buku[position].pengarang)
            intent.putExtra("penerbit", arraylist_data_buku[position].penerbit)
            intent.putExtra("id_kategori", arraylist_data_buku[position].id_kategori)
            intent.putExtra("tahun_terbit", arraylist_data_buku[position].tahun_terbit)
            intent.putExtra("jumlah_halaman", arraylist_data_buku[position].jumlah_halaman)
            intent.putExtra("status_buku", arraylist_data_buku[position].status_buku)
            startActivity(intent)
            finish()
        }
        buttonBack.setOnClickListener {
            val intent = Intent(this@ViewDataBuku, PageBuku::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getData() {
        val url: String = AppConfig().IP_SERVER + "/perpustakaan_web/view_data_buku.php"
        val stringRequest = object : StringRequest(Method.GET,url,
            Response.Listener { response ->
                val jsonObj = JSONObject(response)
                val jsonArray = jsonObj.getJSONArray("data")
                var product: Product
                arraylist_data_buku.clear()
                for (i in 0..jsonArray.length()-1) {
                    val item = jsonArray.getJSONObject(i)
                    product = Product()
                    product.image = AppConfig().IP_SERVER + "/perpustakaan_web/" + item.getString("image")
                    product.id_buku = item.getString("id_buku")
                    product.judul = item.getString("judul")
                    product.pengarang = item.getString("pengarang")
                    product.penerbit = item.getString("penerbit")
                    product.id_kategori = item.getString("id_kategori")
                    product.tahun_terbit = item.getString("tahun_terbit")
                    product.jumlah_halaman = item.getString("jumlah_halaman")
                    product.status_buku = item.getString("status_buku")
                    arraylist_data_buku.add(product)
                }
                listviewbuku.adapter = Adapter_Product_Buku(this@ViewDataBuku, arraylist_data_buku)
            },
            Response.ErrorListener { _ ->
                Toast.makeText(this,"Gagal Terhubung",Toast.LENGTH_SHORT).show()
            }
        ){}
        Volley.newRequestQueue(this).add(stringRequest)
    }
}
