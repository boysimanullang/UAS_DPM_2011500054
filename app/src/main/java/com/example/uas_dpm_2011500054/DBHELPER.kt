package com.example.uas_dpm_2011500054

import android.content.*
import android.database.Cursor
import android.database.sqlite.*

class DBHELPER (context: Context): SQLiteOpenHelper(context, "campuss", null, 1) {
    var nidn = ""
    var namaDosen = ""
    var jabatan = ""
    var golongan = ""
    var pendidikan = ""
    var keahlian = ""
    var programStudi = ""

    private val tabel = "lecturer"
    private var sql = ""

    override fun onCreate(db: SQLiteDatabase?) {
        sql = """create table $tabel(
            NIDN char(10) primary key,
            nama_dosen varchar(50) not null,
            Jabatan varchar(15) not null,
            golongan_pangkat varchar(30) not null,
            Pendidikan char(2) not null,
            Keahlian varchar(30) not null,
            Program_studi varchar(50) not null
            )
        """.trimIndent()
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        sql = "drop table if exists $tabel"
        db?.execSQL(sql)
    }

    fun tampil(): Cursor {
        val db = writableDatabase
        val reader = db.rawQuery("select * from $tabel", null)
        return reader
    }

    fun simpan(): Boolean{
        val db = writableDatabase
        val cv = ContentValues()
        with(cv) {
            put("NIDN", nidn)
            put("nama_dosen", namaDosen)
            put("Jabatan", jabatan)
            put("golongan_pangkat", golongan)
            put("Pendidikan", pendidikan)
            put("Keahlian", keahlian)
            put("Program_studi", programStudi)
        }

        val cmd = db.insert(tabel, null, cv)
        db.close()
        return cmd != -1L
    }

    fun hapus(kode: String):Boolean {
        val db = writableDatabase
        val cmd = db.delete(tabel, "nidn = ?", arrayOf(kode))
        return  cmd != -1
    }

    fun ubah (kode: String): Boolean {
        val db = writableDatabase
        val cv =  ContentValues()
        with(cv){
            put("nama_dosen", namaDosen)
            put("Jabatan", jabatan)
            put("golongan_pangkat", golongan)
            put("Pendidikan", pendidikan)
            put("Keahlian", keahlian)
            put("Program_studi", programStudi)
        }
        val cmd = db.update(tabel, cv, "nidn = ?", arrayOf(kode))
        db.close()
        return cmd != -1
    }
}