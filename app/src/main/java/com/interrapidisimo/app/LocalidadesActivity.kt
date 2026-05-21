package com.interrapidisimo.app

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.interrapidisimo.app.network.RetrofitClient
import kotlinx.coroutines.launch


class LocalidadesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_localidades)

        val listView = findViewById<ListView>(R.id.lvLocalidades)

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.parametrosService.getLocalidades()

                if (response.isSuccessful) {
                    val localidades = response.body()
                    if (!localidades.isNullOrEmpty()) {
                        val lista = localidades.map {
                            "${it.AbreviacionCiudad} - ${it.NombreCompleto}"
                        }
                        val adapter = ArrayAdapter(
                            this@LocalidadesActivity,
                            android.R.layout.simple_list_item_1,
                            lista
                        )
                        listView.adapter = adapter
                    } else {
                        Toast.makeText(this@LocalidadesActivity,
                            "No se encontraron localidades", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@LocalidadesActivity,
                        "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                Toast.makeText(this@LocalidadesActivity,
                    "Error de conexión: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}