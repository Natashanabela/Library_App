package com.example.perpustakaan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class Adapter_Product_Member(viewDataMember: ViewDataMember, arraylist_data_member: ArrayList<Product>) :BaseAdapter() {

    private var arraylist_data_member: ArrayList<Product> = arraylist_data_member
    private var inflater: LayoutInflater = viewDataMember.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return arraylist_data_member.size
    }

    override fun getItem(position: Int): Any {
        return arraylist_data_member[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var vi: View? = convertView
        if (convertView == null)
            vi = inflater.inflate(R.layout.list_product_member, null)
        val textViewIdMember    : TextView = vi!!.findViewById(R.id.textViewIdMember)
        val textViewnamamember  : TextView = vi.findViewById(R.id.textViewNamaMember)
        val textViewjeniskelamin : TextView = vi.findViewById(R.id.textViewJenisKelamin)
        val textViewnotelp: TextView = vi.findViewById(R.id.textViewNomorTelp)

        val product = arraylist_data_member[position]
        textViewIdMember.text   = product.id_member
        textViewnamamember.text  = product.nama_member
        textViewjeniskelamin.text = product.jenis_kelamin
        textViewnotelp.text = product.nomor_telepon

        return vi
    }
}