package com.example.uas_dpm_2011500054

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class EntriDataDosen : AppCompatActivity () {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_entri_data)

        val modeEdit = intent.hasExtra("NIDN") && intent.hasExtra("Nama") &&
                intent.hasExtra("Jabatan") && intent.hasExtra("Golongan_Pangkat")
                && intent.hasExtra("Pendidikan") && intent.hasExtra("Keahlian")
                && intent.hasExtra("Program_Studi")
        title = if (modeEdit) "Edit Data Dosen" else "Entri Data Dosen"

        val etNIDN = findViewById<EditText>(R.id.etNIDN)
        val etNamaDosen = findViewById<EditText>(R.id.etNamaDosen)
        val spnJabatan = findViewById<Spinner>(R.id.spnJabatan)
        val spnGolongan = findViewById<Spinner>(R.id.spnGolongan)
        val s2 = findViewById<RadioButton>(R.id.s2)
        val s3 = findViewById<RadioButton>(R.id.s3)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        val etKeahlian = findViewById<EditText>(R.id.etKeahlian)
        val etProgramstudi = findViewById<EditText>(R.id.etProgamstudi)
        val jbt = arrayOf(
            "Tenaga Pengajar", "Asisten Ahli", "Lektor",
            "Lektor Kepala", "Guru Besar"
        )
        val gol = arrayOf(
            "III/a - Penata Muda", "III/b Penata Muda Tingkat 1",
            "III/c - Penata", "III/d - Penata Tingkat 1",
            "IV/a - Pembina", "IV/b - Pembina - Tingkat 1",
            "IV/c - Pembina Utama Muda", "IV/d - Pembina Utama Madya",
            "IV/e - Pembina Utama"
        )

        val adpJabatan = ArrayAdapter(
            this@EntriDataDosen,
            android.R.layout.simple_spinner_dropdown_item, jbt
        )
        val adpGolongan = ArrayAdapter(
            this@EntriDataDosen,
            android.R.layout.simple_spinner_dropdown_item, gol
        )
        spnJabatan.adapter = adpJabatan
        spnGolongan.adapter = adpGolongan

        if (modeEdit) {
            val nidn = intent.getStringExtra("NIDN")
            val namaDosen = intent.getStringExtra("Nama_Dosen")
            val jabatan = intent.getStringExtra("Jabatan")
            val golongan = intent.getStringExtra("Golongan_Pangkat")
            val pendidikan = intent.getStringExtra("Pendidikan")
            val bidangKeahlian = intent.getStringExtra("Keahlian")
            val programStudi = intent.getStringExtra("Program_Studi")

            etNIDN.setText(nidn)
            etNamaDosen.setText(namaDosen)
            spnJabatan.setSelection(jbt.indexOf(jabatan))
            spnGolongan.setSelection(gol.indexOf(golongan))
            etKeahlian.setText(bidangKeahlian)
            etProgramstudi.setText(programStudi)
            if (pendidikan == "S2") s2.isChecked = true else s3.isChecked = true
        }
        etNIDN.isEnabled = !modeEdit

        btnSimpan.setOnClickListener {
            if ("${etNIDN.text}".isNotEmpty() && "${etNamaDosen.text}".isNotEmpty() &&
                "${spnJabatan.selectedItem}".isNotEmpty() && "${spnGolongan.selectedItem}".isNotEmpty() &&
                "${etKeahlian.text}".isNotEmpty() && "${etProgramstudi.text}".isNotEmpty() &&
                (s2.isChecked || s3.isChecked)
            ) {
                val db = DBHELPER(this@EntriDataDosen)
                db.nidn = "${etNIDN.text}"
                db.namaDosen = "${etNamaDosen.text}"
                db.jabatan = spnJabatan.selectedItem as String
                db.golongan = spnGolongan.selectedItem as String
                db.pendidikan = if (s2.isChecked) "S2" else "S3"
                db.keahlian = "${etKeahlian.text}"
                db.programStudi = "${etProgramstudi.text}"
                if (if (!modeEdit) db.simpan() else db.ubah("${etNIDN.text}")) {
                    Toast.makeText(
                        this@EntriDataDosen,
                        "Data Dosen berhasil disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else
                    Toast.makeText(
                        this@EntriDataDosen,
                        "Data Dosen gagal disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
            } else
                Toast.makeText(
                    this@EntriDataDosen,
                    "Data Dosen belum lengkap",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }
}