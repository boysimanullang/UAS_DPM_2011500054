package com.example.uas_dpm_2011500054

import android.app.Activity
import android.app.AlertDialog
import android.content.*
import android.view.*
import android.widget.*

class Adapter(
    private val getContext: Context,
    private val customListItem: ArrayList<DataDosen>
) : ArrayAdapter<DataDosen>(getContext, 0, customListItem){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listLayout = convertView
        val holder: ViewHolder
        if (listLayout == null) {
            val inflateList = (getContext as Activity).layoutInflater
            listLayout = inflateList.inflate(R.layout.layout_data_dosen, parent, false)
            holder = ViewHolder()
            with(holder){
                tvNamaDosen = listLayout.findViewById(R.id.tvNamaDosen)
                tvNIDN = listLayout.findViewById(R.id.tvNIDN)
                tvProgramStudi = listLayout.findViewById(R.id.tvProgamstudi)
                btnEdit = listLayout.findViewById(R.id.btnEdit)
                btnHapus = listLayout.findViewById(R.id.btnHapus)
            }
            listLayout.tag = holder
        } else
            holder = listLayout.tag as ViewHolder
        val listItem = customListItem[position]
        holder.tvNamaDosen!!.setText(listItem.namaDosen)
        holder.tvNIDN!!.setText(listItem.nidn)
        holder.tvProgramStudi!!.setText(listItem.programstudi)

        holder.btnEdit!!.setOnClickListener{
            val i = Intent(context, EntriDataDosen::class.java)
            i.putExtra("NIDN", listItem.nidn)
            i.putExtra("Nama", listItem.namaDosen)
            i.putExtra("Jabatan", listItem.jabatan)
            i.putExtra("Golongan_Pangkat", listItem.golongan)
            i.putExtra("Pendidikan", listItem.pendidikan)
            i.putExtra("Keahlian", listItem.keahlian)
            i.putExtra("Program_Studi", listItem.programstudi)
            context.startActivity(i)
        }

        holder.btnHapus!!.setOnClickListener{
            val db = DBHELPER(context)
            val alb = AlertDialog.Builder(context)
            val nidn = holder.tvNIDN!!.text
            val nama = holder.tvNamaDosen!!.text
            val programstudi = holder.tvProgramStudi!!.text
            val msg = "Apakah Anda yakin akan menghapus data ini?"
            with(alb) {
                setTitle("Konfirmasi Penghapusan")
                setCancelable(false)
                setMessage("$msg" + "\n$nama" + "\n[$nidn-$programstudi]")
                setPositiveButton("Ya") { _, _ ->
                    if (db.hapus("$nidn"))
                        Toast.makeText(
                            context,
                            "Data Dosen berhasil dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                    else
                        Toast.makeText(
                            context,
                            "Data Dosen gagal dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                }
                setNegativeButton("Tidak", null)
                create().show()
            }
        }

        return listLayout!!
    }

    class ViewHolder {
        internal var tvNamaDosen: TextView? = null
        internal var tvNIDN: TextView? = null
        internal var tvProgramStudi: TextView? = null
        internal var btnEdit: ImageButton? = null
        internal var btnHapus: ImageButton? = null
    }
}