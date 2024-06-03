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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONObject
//member
class ViewDataMember : AppCompatActivity() {
    private lateinit var listviewmember : ListView
    private lateinit var buttonBack         : ImageButton
    private lateinit var buttonAdd          : FloatingActionButton
    var arraylist_data_member = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_member)

        buttonBack          = findViewById(R.id.buttonBack)
        listviewmember = findViewById(R.id.listViewdatamember)
        buttonAdd          = findViewById(R.id.buttonaddmember)
        getData()


        listviewmember.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@ViewDataMember, SendDataMember::class.java)
            intent.putExtra("id_member", arraylist_data_member[position].id_member)
            intent.putExtra("nama_member", arraylist_data_member[position].nama_member)
            intent.putExtra("jenis_kelamin", arraylist_data_member[position].jenis_kelamin)
            intent.putExtra("nomor_telepon", arraylist_data_member[position].nomor_telepon)
            startActivity(intent)
            finish()
        }

        buttonAdd.setOnClickListener {
            val intent = Intent(this@ViewDataMember, SendDataMember::class.java)
            startActivity(intent)
            finish()
        }

        buttonBack.setOnClickListener {
            val intent = Intent(this@ViewDataMember, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getData() {
        val url: String = AppConfig().IP_SERVER + "/perpustakaan_web/view_data_member.php"
        val stringRequest = object : StringRequest(Method.GET,url,
            Response.Listener { response ->
                val jsonObj = JSONObject(response)
                val jsonArray = jsonObj.getJSONArray("data")
                var product: Product
                arraylist_data_member.clear()
                for (i in 0..jsonArray.length()-1) {
                    val items = jsonArray.getJSONObject(i)
                    product = Product()
                    product.id_member = items.getString("id_member")
                    product.nama_member = items.getString("nama_member")
                    product.jenis_kelamin = items.getString("jenis_kelamin")
                    product.nomor_telepon = items.getString("nomor_telepon")
                    arraylist_data_member.add(product)
                }
                listviewmember.adapter = Adapter_Product_Member(this@ViewDataMember, arraylist_data_member)
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "${error.message}", Toast.LENGTH_SHORT).show()
            }

        ){}
        Volley.newRequestQueue(this).add(stringRequest)
    }
}
