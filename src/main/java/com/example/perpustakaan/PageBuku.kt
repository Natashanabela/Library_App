package com.example.perpustakaan

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class PageBuku : AppCompatActivity() {
    private lateinit var buttonBack   : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_buku)

        val buttonAddBuku : Button = findViewById(R.id.buttonAdd)
        val buttonViewBuku : Button = findViewById(R.id.btnviewbuku)
        val buttonAddKategori : Button = findViewById(R.id.btnaddkategori)
        val buttonViewKategori : Button = findViewById(R.id.btnviewkategori)
        buttonBack = findViewById(R.id.buttonBack)

        buttonAddBuku.setOnClickListener {
            val intent = Intent(this@PageBuku, SendDataBuku::class.java)
            startActivity(intent)
        }

        buttonViewBuku.setOnClickListener {
            val intent = Intent(this@PageBuku, ViewDataBuku::class.java)
            startActivity(intent)
        }

        buttonAddKategori.setOnClickListener {
            val intent = Intent(this@PageBuku, SendDataKategori::class.java)
            startActivity(intent)
        }

        buttonViewKategori.setOnClickListener {
            val intent = Intent(this@PageBuku, ViewDataKategori::class.java)
            startActivity(intent)
        }

        buttonBack.setOnClickListener {
            val intent = Intent(this@PageBuku, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

