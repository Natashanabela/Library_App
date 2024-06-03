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
//peminjaman
class ViewDataPeminjaman : AppCompatActivity() {
    private lateinit var listviewpeminjaman : ListView
    private lateinit var buttonBack   : ImageButton
    var arraylist_data_peminjaman = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_data_peminjaman)
        listviewpeminjaman = findViewById(R.id.listViewdatapeminjaman)
        buttonBack = findViewById(R.id.buttonBack)
        getData()

        listviewpeminjaman.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@ViewDataPeminjaman, SendDataPeminjaman::class.java)
            intent.putExtra("id_peminjaman", arraylist_data_peminjaman[position].id_peminjaman)
            intent.putExtra("id_member", arraylist_data_peminjaman[position].id_member)
            intent.putExtra("id_buku", arraylist_data_peminjaman[position].id_buku)
            intent.putExtra("tanggal_peminjaman", arraylist_data_peminjaman[position].tanggal_peminjaman)
            intent.putExtra("tanggal_pengembalian", arraylist_data_peminjaman[position].tanggal_pengembalian)

            startActivity(intent)
            finish()
        }

        buttonBack.setOnClickListener {
            val intent = Intent(this@ViewDataPeminjaman, PagePeminjaman::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getData() {
        val url: String = AppConfig().IP_SERVER + "/perpustakaan_web/view_data_peminjaman.php"
        val stringRequest = object : StringRequest(Method.GET,url,
            Response.Listener { response ->
                val jsonObj = JSONObject(response)
                val jsonArray = jsonObj.getJSONArray("data")
                var product: Product
                arraylist_data_peminjaman.clear()
                for (i in 0..jsonArray.length()-1) {
                    val item = jsonArray.getJSONObject(i)
                    product = Product()
                    product.id_peminjaman = item.getString("id_peminjaman")
                    product.id_member = item.getString("id_member")
                    product.id_buku = item.getString("id_buku")
                    product.tanggal_peminjaman = item.getString("tanggal_peminjaman")
                    product.tanggal_pengembalian = item.getString("tanggal_pengembalian")

                    arraylist_data_peminjaman.add(product)
                }
                listviewpeminjaman.adapter = Adapter_Product_Peminjaman(this@ViewDataPeminjaman, arraylist_data_peminjaman)
            },
            Response.ErrorListener { _ ->
                Toast.makeText(this,"Gagal Terhubung",Toast.LENGTH_SHORT).show()
            }
        ){}
        Volley.newRequestQueue(this).add(stringRequest)
    }
}
