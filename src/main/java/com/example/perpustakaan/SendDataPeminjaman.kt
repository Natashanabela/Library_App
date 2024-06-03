package com.example.perpustakaan


import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

//peminjaman
class SendDataPeminjaman : AppCompatActivity() {
    private var id_peminjaman       = ""
    private lateinit var action             : String
    private lateinit var editTextIdMember   : EditText
    private lateinit var editTextIdBuku     : EditText
    private lateinit var editTextTglPinjam  : EditText
    private lateinit var editTextTglKembali : EditText
    private lateinit var buttonAdd          : Button
    private lateinit var buttonEdit         : ImageButton
    private lateinit var buttonDelete       : ImageButton
    private lateinit var buttonBack         : ImageButton
    private lateinit var calendar           : Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_data_peminjaman)

        editTextIdMember    = findViewById(R.id.editTextIdMember)
        editTextIdBuku      = findViewById(R.id.editTextIdBuku)
        editTextTglPinjam   = findViewById(R.id.editTextTglPinjam)
        editTextTglKembali  = findViewById(R.id.editTextTglKembali)

        buttonAdd           = findViewById(R.id.buttonAddMember)
        buttonEdit          = findViewById(R.id.buttonEdit)
        buttonDelete        = findViewById(R.id.buttonDelete)
        buttonBack          = findViewById(R.id.buttonBack)

        calendar = Calendar.getInstance()

        editTextTglPinjam.setOnClickListener {
            showDatePickerDialog(editTextTglPinjam)
        }

        editTextTglKembali.setOnClickListener {
            showDatePickerDialog(editTextTglKembali)
        }

        val bundle = intent.extras
        if (bundle!=null) { //ketika ada isi dari intent akan masuk ke dalam aksi if
            id_peminjaman = bundle.getString("id_peminjaman")!!
            editTextIdMember.setText(bundle.getString("id_member"))
            editTextIdBuku.setText(bundle.getString("id_buku"))
            editTextTglPinjam.setText(bundle.getString("tanggal_peminjaman"))
            editTextTglKembali.setText(bundle.getString("tanggal_pengembalian"))
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
            val intent = Intent(this@SendDataPeminjaman, PagePeminjaman::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun showDatePickerDialog(editText: EditText) {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateEditText(editText)
        }

        DatePickerDialog(
            this,
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun updateEditText(editText: EditText) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        editText.setText(dateFormat.format(calendar.time))
    }

    private fun kirimdata() {
        val url: String = AppConfig().IP_SERVER + "/perpustakaan_web/send_data_peminjaman.php"
        val stringRequest = object : StringRequest(Method.POST,url,
            Response.Listener { response ->
                val jsonObj = JSONObject(response)
                Toast.makeText(this,jsonObj.getString("error_text"),Toast.LENGTH_SHORT).show()
                val intent = Intent(this@SendDataPeminjaman, ViewDataPeminjaman::class.java)
                startActivity(intent)
                finish()
            },
            Response.ErrorListener { _ ->
                Toast.makeText(this,"Gagal Terhubung",Toast.LENGTH_SHORT).show()
            }
        ){
            override fun getParams(): HashMap<String,String>{
                val params = HashMap<String,String>()
                params["id_peminjaman"]         = id_peminjaman
                params["id_member"]             = editTextIdMember.text.toString()
                params["id_buku"]               = editTextIdBuku.text.toString()
                params["tanggal_peminjaman"]    = editTextTglPinjam.text.toString()
                params["tanggal_pengembalian"]  = editTextTglKembali.text.toString()
                params["action"]    = action
                return params
            }
        }
        Volley.newRequestQueue(this).add(stringRequest)
    }
}