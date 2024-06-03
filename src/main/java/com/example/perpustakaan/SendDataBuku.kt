package com.example.perpustakaan


import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import android.provider.MediaStore
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import java.io.IOException
import com.squareup.picasso.Picasso
import android.util.Base64
import android.widget.ImageButton
import java.io.ByteArrayOutputStream


class SendDataBuku : AppCompatActivity() {
    private lateinit var imageView          : ImageView
    private lateinit var bitmap             : Bitmap
    private var resId     = 0
    private lateinit var editTextIdBuku         : EditText
    private lateinit var action                 : String
    private lateinit var editTextJudul          : EditText
    private lateinit var editTextPengarang      : EditText
    private lateinit var editTextPenerbit       : EditText
    private lateinit var editTextIdKategori     : EditText
    private lateinit var editTextTahunTerbit    : EditText
    private lateinit var editTextJumlahHalaman  : EditText
    private lateinit var spinnerStatusBuku      : Spinner
    private lateinit var buttonAdd          : Button
    private lateinit var buttonEdit         : ImageButton
    private lateinit var buttonDelete       : ImageButton
    private lateinit var buttonBack         : ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_data_buku)

        imageView           = findViewById(R.id.clickToUploadImg)
        editTextIdBuku          = findViewById(R.id.editTextIdBuku)
        editTextJudul           = findViewById(R.id.editTextJudul)
        editTextPengarang       = findViewById(R.id.editTextPengarang)
        editTextPenerbit        = findViewById(R.id.editTextPenerbit)
        editTextIdKategori      = findViewById(R.id.editTextIdKategori)
        editTextTahunTerbit     = findViewById(R.id.editTextTahunTerbit)
        editTextJumlahHalaman   = findViewById(R.id.editTextJumlahHalaman)
        spinnerStatusBuku      = findViewById(R.id.spinnerStatusBuku)


        buttonAdd           = findViewById(R.id.buttonAdd)
        buttonEdit          = findViewById(R.id.buttonEdit)
        buttonDelete        = findViewById(R.id.buttonDelete)
        buttonBack          = findViewById(R.id.buttonBack)

        val statusList = arrayOf("Ready", "Tidak Ready")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statusList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerStatusBuku.adapter = adapter



        val bundle = intent.extras
        if (bundle!=null) { //ketika ada isi dari intent akan masuk ke dalam aksi if
            Picasso.get().load(bundle.getString("image")).into(imageView)
            editTextIdBuku.setText(bundle.getString("id_buku"))
            editTextJudul.setText(bundle.getString("judul"))
            editTextPengarang.setText(bundle.getString("pengarang"))
            editTextPenerbit.setText(bundle.getString("penerbit"))
            editTextIdKategori.setText(bundle.getString("id_kategori"))
            editTextTahunTerbit.setText(bundle.getString("tahun_terbit"))
            editTextJumlahHalaman.setText(bundle.getString("jumlah_halaman"))
            val statusBuku = bundle.getString("status_buku")
            val selectedIndex = statusList.indexOf(statusBuku)

            if (selectedIndex != -1) {
                spinnerStatusBuku.setSelection(selectedIndex)
            } else {
                spinnerStatusBuku.setSelection(0)
            }

            buttonAdd.visibility = View.GONE
        }
        else { //sedangkan jika tidak ada data dari intent maka akan masuk ke dalam aski else
            buttonEdit.visibility   = View.GONE
            buttonDelete.visibility = View.GONE
        }

        imagePick()

        buttonAdd.setOnClickListener {
            action = "add"
            kirimdata()
        }

        buttonEdit.setOnClickListener {
            action = "edit"
            updatedata()
        }

        buttonDelete.setOnClickListener {
            action = "delete"
            hapusdata()
        }

        buttonBack.setOnClickListener {
            val intent = Intent(this@SendDataBuku, PageBuku::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun imagePick() {
        val activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) { val data = result.data!!
                val uri = data.data
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                    imageView.setImageBitmap(bitmap)
                    resId = 1
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        imageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            activityResultLauncher.launch(intent)
        }
    }

    private fun kirimdata() {
        if (resId == 1) {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val bytes = byteArrayOutputStream.toByteArray()
            val base64Image = Base64.encodeToString(bytes, Base64.DEFAULT)
            val url: String = AppConfig().IP_SERVER + "/perpustakaan_web/send_data_buku.php"
            val stringRequest = object : StringRequest(Method.POST, url,
                Response.Listener { response ->
                        val jsonObj = JSONObject(response)
                        Toast.makeText(this, jsonObj.getString("error_text"), Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this@SendDataBuku, ViewDataBuku::class.java)
                        startActivity(intent)

                    finish()
                },
                Response.ErrorListener { _ ->
                    Toast.makeText(this, "Gagal Terhubung", Toast.LENGTH_SHORT).show()
                }
            ) {
                override fun getParams(): HashMap<String, String> {
                    val params = HashMap<String, String>()
                    params["image"] = base64Image
                    params["id_buku"] = editTextIdBuku.text.toString()
                    params["judul"] = editTextJudul.text.toString()
                    params["pengarang"] = editTextPengarang.text.toString()
                    params["penerbit"] = editTextPenerbit.text.toString()
                    params["id_kategori"] = editTextIdKategori.text.toString()
                    params["tahun_terbit"] = editTextTahunTerbit.text.toString()
                    params["jumlah_halaman"] = editTextJumlahHalaman.text.toString()
                    params["status_buku"] = spinnerStatusBuku.selectedItem.toString()
                    params["action"] = action
                    params["resId"] = resId.toString()
                    return params
                }
            }
            Volley.newRequestQueue(this).add(stringRequest)
        } else {
            Toast.makeText(this, "Select the image first", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updatedata() {
        if (resId == 1) {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val bytes = byteArrayOutputStream.toByteArray()
            val base64Image = Base64.encodeToString(bytes, Base64.DEFAULT)
            val url: String = AppConfig().IP_SERVER + "/perpustakaan_web/send_data_buku.php"
            val stringRequest = object : StringRequest(Method.POST, url,
                Response.Listener { response ->
                    val jsonObj = JSONObject(response)
                    Toast.makeText(this, jsonObj.getString("error_text"), Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this@SendDataBuku, ViewDataBuku::class.java)
                    startActivity(intent)

                    finish()
                },
                Response.ErrorListener { _ ->
                    Toast.makeText(this, "Gagal Terhubung", Toast.LENGTH_SHORT).show()
                }
            ) {
                override fun getParams(): HashMap<String, String> {
                    val params = HashMap<String, String>()
                    params["image"] = base64Image
                    params["id_buku"] = editTextIdBuku.text.toString()
                    params["judul"] = editTextJudul.text.toString()
                    params["pengarang"] = editTextPengarang.text.toString()
                    params["penerbit"] = editTextPenerbit.text.toString()
                    params["id_kategori"] = editTextIdKategori.text.toString()
                    params["tahun_terbit"] = editTextTahunTerbit.text.toString()
                    params["jumlah_halaman"] = editTextJumlahHalaman.text.toString()
                    params["status_buku"] = spinnerStatusBuku.selectedItem.toString()
                    params["action"] = action
                    params["resId"] = resId.toString()
                    return params
                }
            }
            Volley.newRequestQueue(this).add(stringRequest)
        } else {
            val url: String = AppConfig().IP_SERVER + "/perpustakaan_web/send_data_buku.php"
            val stringRequest = object : StringRequest(Method.POST, url,
                Response.Listener { response ->
                    val jsonObj = JSONObject(response)
                    Toast.makeText(this, jsonObj.getString("error_text"), Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this@SendDataBuku, ViewDataBuku::class.java)
                    startActivity(intent)

                    finish()
                },
                Response.ErrorListener { _ ->
                    Toast.makeText(this, "Gagal Terhubung", Toast.LENGTH_SHORT).show()
                }
            ) {
                override fun getParams(): HashMap<String, String> {
                    val params = HashMap<String, String>()
                    params["image"] = ""
                    params["id_buku"] = editTextIdBuku.text.toString()
                    params["judul"] = editTextJudul.text.toString()
                    params["pengarang"] = editTextPengarang.text.toString()
                    params["penerbit"] = editTextPenerbit.text.toString()
                    params["id_kategori"] = editTextIdKategori.text.toString()
                    params["tahun_terbit"] = editTextTahunTerbit.text.toString()
                    params["jumlah_halaman"] = editTextJumlahHalaman.text.toString()
                    params["status_buku"] = spinnerStatusBuku.selectedItem.toString()
                    params["action"] = action
                    params["resId"] = resId.toString()
                    return params
                }
            }
            Volley.newRequestQueue(this).add(stringRequest)
        }
    }
    private fun hapusdata(){
        val url: String = AppConfig().IP_SERVER + "/perpustakaan_web/send_data_buku.php"
        val stringRequest = object : StringRequest(Method.POST, url,
            Response.Listener { response ->
                val jsonObj = JSONObject(response)
                Toast.makeText(this, jsonObj.getString("error_text"), Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(this@SendDataBuku, ViewDataBuku::class.java)
                startActivity(intent)

                finish()
            },
            Response.ErrorListener { _ ->
                Toast.makeText(this, "Gagal Terhubung", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): HashMap<String, String> {
                val params = HashMap<String, String>()
                params["image"] = ""
                params["id_buku"] = editTextIdBuku.text.toString()
                params["judul"] = editTextJudul.text.toString()
                params["pengarang"] = editTextPengarang.text.toString()
                params["penerbit"] = editTextPenerbit.text.toString()
                params["id_kategori"] = editTextIdKategori.text.toString()
                params["tahun_terbit"] = editTextTahunTerbit.text.toString()
                params["jumlah_halaman"] = editTextJumlahHalaman.text.toString()
                params["status_buku"] = spinnerStatusBuku.selectedItem.toString()
                params["action"] = action
                params["resId"] = resId.toString()
                return params
            }
        }
        Volley.newRequestQueue(this).add(stringRequest)
    }
}
