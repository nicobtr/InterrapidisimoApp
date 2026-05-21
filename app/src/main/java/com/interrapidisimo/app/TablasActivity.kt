package com.interrapidisimo.app

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.interrapidisimo.app.database.DatabaseHelper
import com.interrapidisimo.app.network.RetrofitClient
import kotlinx.coroutines.launch


class TablasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tablas)

        val dbHelper = DatabaseHelper(this)
        val listView = findViewById<ListView>(R.id.lvTablas)

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.parametrosService.getEsquema()

                if (response.isSuccessful) {
                    val body = response.body()
                    val nombresTablas = mutableListOf<String>()

                    when {
                        body?.isJsonArray == true -> {
                            for (elemento in body.asJsonArray) {
                                try {
                                    val obj = elemento.asJsonObject
                                    val nombre = obj.get("NombreTabla")?.asString
                                        ?: obj.get("Nombre")?.asString
                                        ?: obj.get("nombre")?.asString
                                        ?: obj.toString()
                                    nombresTablas.add(nombre)
                                } catch (e: Exception) {
                                    nombresTablas.add(elemento.toString())
                                }
                            }
                        }
                        body?.isJsonObject == true -> {
                            val obj = body.asJsonObject
                            for (key in obj.keySet()) {
                                if (obj.get(key).isJsonArray) {
                                    for (elemento in obj.getAsJsonArray(key)) {
                                        nombresTablas.add(elemento.toString())
                                    }
                                    break
                                }
                            }
                        }
                    }

                    if (nombresTablas.isNotEmpty()) {
                        dbHelper.guardarTablas(nombresTablas)
                    }

                } else {
                    Toast.makeText(this@TablasActivity,
                        "Error al obtener tablas: ${response.code()}", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                Toast.makeText(this@TablasActivity,
                    "Error de conexión: ${e.message}", Toast.LENGTH_SHORT).show()
            }

            val tablas = dbHelper.obtenerTablas()
            val adapter = ArrayAdapter(
                this@TablasActivity,
                android.R.layout.simple_list_item_1,
                tablas
            )
            listView.adapter = adapter
        }
    }
}