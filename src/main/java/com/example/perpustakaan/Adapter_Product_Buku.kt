package com.example.perpustakaan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import com.squareup.picasso.Picasso

class Adapter_Product_Buku(private val context: Context, private val arraylist_data_buku: ArrayList<Product>) : BaseAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val statusList = arrayOf("Ready", "Tidak Ready")

    // Inisialisasi Spinner sekali di konstruktor
    private val spinnerStatusBuku: Spinner = Spinner(context)

    init {
        setSpinner(spinnerStatusBuku)
    }

    private fun setSpinner(spinner: Spinner) {
        val adapter =
            ArrayAdapter(spinner.context, android.R.layout.simple_spinner_item, statusList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        //spinner.isEnabled = false // If you want to make the Spinner read-only
    }


    private fun getStatusIndex(status: String): Int {
        return statusList.indexOf(status)
    }

    override fun getCount(): Int {
        return arraylist_data_buku.size
    }

    override fun getItem(position: Int): Any {
        return arraylist_data_buku[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var vi: View? = convertView
        if (convertView == null) {
            vi = inflater.inflate(R.layout.list_product_buku, null)
        }

        val image: ImageView = vi!!.findViewById(R.id.clickToUploadImg)
        val textViewIdBuku: TextView = vi.findViewById(R.id.textViewIdBuku)
        val textViewJudul: TextView = vi.findViewById(R.id.textViewJudul)
        val textViewpengarang: TextView = vi.findViewById(R.id.textViewPengarang)
        val textViewpenerbit: TextView = vi.findViewById(R.id.textViewPenerbit)
        val textViewIdKategori: TextView = vi.findViewById(R.id.textViewIdKategori)
        val textViewtahunTerbit: TextView = vi.findViewById(R.id.textViewTahunTerbit)
        val textViewjumlahhalaman: TextView = vi.findViewById(R.id.textViewJumlahHalaman)

        val spinnerStatusBuku: Spinner = vi.findViewById(R.id.spinnerStatusBuku) as Spinner

        // Set nilai Spinner sesuai dengan data yang bersangkutan
        val product = arraylist_data_buku[position]
        Picasso.get().load(product.image).into(image)
        textViewIdBuku.text = product.id_buku
        textViewJudul.text = product.judul
        textViewpengarang.text = product.pengarang
        textViewpenerbit.text = product.penerbit
        textViewIdKategori.text = product.id_kategori
        textViewtahunTerbit.text = product.tahun_terbit
        textViewjumlahhalaman.text = product.jumlah_halaman
        spinnerStatusBuku.setSelection(getStatusIndex(product.status_buku))

        // Menonaktifkan focus dan clickable pada Spinner
        spinnerStatusBuku.isFocusable = false
        spinnerStatusBuku.isClickable = false

        return vi
    }
}