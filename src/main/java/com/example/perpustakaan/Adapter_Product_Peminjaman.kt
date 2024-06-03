package com.example.perpustakaan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class Adapter_Product_Peminjaman(viewDataPeminjaman: ViewDataPeminjaman, arraylist_data_peminjaman: ArrayList<Product>) :BaseAdapter() {

    private var arraylist_data_peminjaman: ArrayList<Product> = arraylist_data_peminjaman
    private var inflater: LayoutInflater = viewDataPeminjaman.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return arraylist_data_peminjaman.size
    }

    override fun getItem(position: Int): Any {
        return arraylist_data_peminjaman[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var vi: View? = convertView
        if (convertView == null)
            vi = inflater.inflate(R.layout.list_product_peminjaman, null)

        val textViewidpeminjaman  : TextView = vi!!.findViewById(R.id.textViewIdPeminjaman)
        val textViewidmember : TextView = vi.findViewById(R.id.textViewIdMember)
        val textViewidbuku: TextView = vi.findViewById(R.id.textViewIdBuku)
        val textViewtglpinjam: TextView = vi.findViewById(R.id.textViewTglPinjam)
        val textViewtglkembali: TextView = vi.findViewById(R.id.textViewTglKembali)

        val product = arraylist_data_peminjaman[position]
        textViewidpeminjaman.text  = product.id_peminjaman
        textViewidmember.text = product.id_member
        textViewidbuku.text = product.id_buku
        textViewtglpinjam.text = product.tanggal_peminjaman
        textViewtglkembali.text = product.tanggal_pengembalian

        return vi
    }
}