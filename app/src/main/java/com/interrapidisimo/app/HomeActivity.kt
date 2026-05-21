package com.interrapidisimo.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.interrapidisimo.app.database.DatabaseHelper

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val dbHelper = DatabaseHelper(this)
        val usuario = dbHelper.obtenerUsuario()

        findViewById<TextView>(R.id.tvUsuario).text = "Usuario: ${usuario?.usuario ?: "N/A"}"
        findViewById<TextView>(R.id.tvIdentificacion).text = "Identificación: ${usuario?.identificacion ?: "N/A"}"
        findViewById<TextView>(R.id.tvNombre).text = "Nombre: ${usuario?.nombre ?: "N/A"}"

        findViewById<Button>(R.id.btnTablas).setOnClickListener {
            startActivity(Intent(this, TablasActivity::class.java))
        }

        findViewById<Button>(R.id.btnLocalidades).setOnClickListener {
            startActivity(Intent(this, LocalidadesActivity::class.java))
        }
    }
}