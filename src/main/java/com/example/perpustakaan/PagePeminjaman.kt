package com.example.perpustakaan

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class PagePeminjaman : AppCompatActivity() {
    private lateinit var buttonBack   : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_peminjaman)

        val buttonAddPeminjaman : Button = findViewById(R.id.btnaddpeminjaman)
        val buttonViewPeminjaman : Button = findViewById(R.id.btnviewpeminjaman)
        buttonBack = findViewById(R.id.buttonBack)

        buttonAddPeminjaman.setOnClickListener {
            val intent = Intent(this@PagePeminjaman, SendDataPeminjaman::class.java)
            startActivity(intent)
        }

        buttonViewPeminjaman.setOnClickListener {
            val intent = Intent(this@PagePeminjaman, ViewDataPeminjaman::class.java)
            startActivity(intent)
        }

        buttonBack.setOnClickListener {
            val intent = Intent(this@PagePeminjaman, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}