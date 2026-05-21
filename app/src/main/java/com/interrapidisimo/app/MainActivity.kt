package com.interrapidisimo.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.interrapidisimo.app.database.DatabaseHelper
import com.interrapidisimo.app.models.LoginRequest
import com.interrapidisimo.app.network.RetrofitClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)

        lifecycleScope.launch {
            verificarVersion()
        }

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            lifecycleScope.launch {
                hacerLogin()
            }
        }
    }

    private suspend fun verificarVersion() {
        try {
            val response = RetrofitClient.parametrosService.getVersion()
            if (response.isSuccessful) {
                val body = response.body()
                val versionServidor = when {
                    body?.isJsonObject == true -> {
                        val obj = body.asJsonObject
                        obj.get("Valor")?.asString
                            ?: obj.get("Version")?.asString
                            ?: obj.get("version")?.asString
                            ?: ""
                    }
                    body?.isJsonPrimitive == true -> body.asString
                    else -> ""
                }
                if (versionServidor.isNotEmpty()) {
                    val versionLocal = BuildConfig.VERSION_NAME
                    when {
                        versionLocal < versionServidor ->
                            mostrarAlerta("Versión desactualizada",
                                "Tu versión ($versionLocal) es inferior a la actual ($versionServidor). Actualiza la aplicación.")
                        versionLocal > versionServidor ->
                            mostrarAlerta("Versión superior",
                                "Tu versión ($versionLocal) es superior a la del servidor ($versionServidor).")
                    }
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "No se pudo verificar la versión: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private suspend fun hacerLogin() {

        try {
            val loginRequest = LoginRequest()
            val headers = mapOf(
                "Usuario" to "pam.meredy21",
                "Identificacion" to "987204545",
                "Accept" to "text/json",
                "IdUsuario" to "pam.meredy21",
                "IdCentroServicio" to "1295",
                "NombreCentroServicio" to "PTO/BOGOTA/CUND/COL/OF PRINCIPAL - CRA 30 # 7-45",
                "IdAplicativoOrigen" to "9",
                "Content-Type" to "application/json"
            )

            val response = RetrofitClient.seguridadService.login(headers, loginRequest)

            if (response.code() != 200) {
                mostrarAlerta("Error de autenticación",
                    "No se pudo iniciar sesión. Código de error: ${response.code()}")
                return
            }

            val body = response.body()
            if (body != null) {
                val usuario = body.get("Usuario")?.asString ?: ""
                val identificacion = body.get("Identificacion")?.asString ?: ""
                val nombre = body.get("Nombre")?.asString ?: ""
                dbHelper.guardarUsuario(usuario, identificacion, nombre)
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                mostrarAlerta("Error", "El servidor no retornó datos del usuario.")
            }

        } catch (e: Exception) {
            mostrarAlerta("Error de conexión", "No se pudo conectar: ${e.message}")
        }
    }

    private fun mostrarAlerta(titulo: String, mensaje: String) {
        AlertDialog.Builder(this)
            .setTitle(titulo)
            .setMessage(mensaje)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}