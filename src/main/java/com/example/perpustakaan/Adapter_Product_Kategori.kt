package com.example.perpustakaan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class Adapter_Product_Kategori(viewDataKategori: ViewDataKategori, arraylist_data_kategori: ArrayList<Product>) :BaseAdapter() {

    private var arraylist_data_kategori: ArrayList<Product> = arraylist_data_kategori
    private var inflater: LayoutInflater = viewDataKategori.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return arraylist_data_kategori.size
    }

    override fun getItem(position: Int): Any {
        return arraylist_data_kategori[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var vi: View = convertView ?: inflater.inflate(R.layout.list_product_kategori, parent, false)

        val textViewIdKategori: TextView = vi.findViewById(R.id.textViewIdKategori)
        val textViewNamakategori: TextView = vi.findViewById(R.id.textViewNamaKategori)

        val product = arraylist_data_kategori[position]
        textViewIdKategori.text = product.id_kategori
        textViewNamakategori.text = product.nama_kategori

        return vi
    }

}