package com.example.uas_dpm_2011500054

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: Adapter
    private lateinit var dataDosen: ArrayList<DataDosen>
    private lateinit var lvDataDosen: ListView
    private lateinit var linTidakAda: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_tampil_data)

        title = "UAS DPM 2011500054"

        val btnTambah = findViewById<Button>(R.id.btnTambah)
        lvDataDosen = findViewById(R.id.lvDataDosen)
        linTidakAda = findViewById(R.id.linTidakAda)

        dataDosen = ArrayList()
        adapter = Adapter(this@MainActivity, dataDosen)

        lvDataDosen.adapter = adapter

        refresh()
        btnTambah.setOnClickListener{
            val i= Intent(this@MainActivity, EntriDataDosen::class.java)
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) refresh()
    }

    private fun refresh() {
        val db = DBHELPER(this@MainActivity)
        val data = db.tampil()
        repeat(dataDosen.size) {dataDosen.removeFirst()}
        if (data.count > 0){
            while (data.moveToNext()){
                val data = DataDosen(
                    data.getString(0),
                    data.getString(1),
                    data.getString(2),
                    data.getString(3),
                    data.getString(4),
                    data.getString(5),
                    data.getString(6)
                )
                adapter.add(data)
                adapter.notifyDataSetChanged()
            }
            lvDataDosen.visibility = View.VISIBLE
            linTidakAda.visibility = View.GONE
        } else {
            lvDataDosen.visibility = View.GONE
            linTidakAda.visibility = View.VISIBLE
        }
    }
}