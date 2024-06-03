package com.example.perpustakaan

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonMember : ImageButton = findViewById(R.id.btnpagemember)
        val buttonBuku : ImageButton = findViewById(R.id.btnpagebuku)
        val buttonPeminjaman : ImageButton = findViewById(R.id.btnpagepeminjaman)



        buttonMember.setOnClickListener {
            val intent = Intent(this@MainActivity, ViewDataMember::class.java)
            startActivity(intent)
        }

        buttonBuku.setOnClickListener {
            val intent = Intent(this@MainActivity, PageBuku::class.java)
            startActivity(intent)
        }

        buttonPeminjaman.setOnClickListener {
            val intent = Intent(this@MainActivity, PagePeminjaman::class.java)
            startActivity(intent)
        }
    }
}