package com.interrapidisimo.app.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "interrapidisimo.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_USUARIOS = "usuarios"
        private const val COL_USUARIO = "usuario"
        private const val COL_IDENTIFICACION = "identificacion"
        private const val COL_NOMBRE = "nombre"

        private const val TABLE_TABLAS = "tablas_esquema"
        private const val COL_NOMBRE_TABLA = "nombre_tabla"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE $TABLE_USUARIOS (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_USUARIO TEXT,
                $COL_IDENTIFICACION TEXT,
                $COL_NOMBRE TEXT
            )
        """.trimIndent())

        db.execSQL("""
            CREATE TABLE $TABLE_TABLAS (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NOMBRE_TABLA TEXT
            )
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIOS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TABLAS")
        onCreate(db)
    }

    fun guardarUsuario(usuario: String, identificacion: String, nombre: String) {
        val db = writableDatabase
        try {
            db.delete(TABLE_USUARIOS, null, null)
            val values = ContentValues().apply {
                put(COL_USUARIO, usuario)
                put(COL_IDENTIFICACION, identificacion)
                put(COL_NOMBRE, nombre)
            }
            db.insert(TABLE_USUARIOS, null, values)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
    }

    fun obtenerUsuario(): UsuarioLocal? {
        val db = readableDatabase
        return try {
            val cursor = db.query(TABLE_USUARIOS, null, null, null, null, null, null)
            if (cursor.moveToFirst()) {
                val usuario = UsuarioLocal(
                    usuario = cursor.getString(cursor.getColumnIndexOrThrow(COL_USUARIO)),
                    identificacion = cursor.getString(cursor.getColumnIndexOrThrow(COL_IDENTIFICACION)),
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRE))
                )
                cursor.close()
                usuario
            } else {
                cursor.close()
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            db.close()
        }
    }

    fun guardarTablas(tablas: List<String>) {
        val db = writableDatabase
        try {
            db.delete(TABLE_TABLAS, null, null)
            for (tabla in tablas) {
                val values = ContentValues().apply {
                    put(COL_NOMBRE_TABLA, tabla)
                }
                db.insert(TABLE_TABLAS, null, values)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
    }

    fun obtenerTablas(): List<String> {
        val db = readableDatabase
        val tablas = mutableListOf<String>()
        return try {
            val cursor = db.query(TABLE_TABLAS, null, null, null, null, null, null)
            while (cursor.moveToNext()) {
                tablas.add(cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRE_TABLA)))
            }
            cursor.close()
            tablas
        } catch (e: Exception) {
            e.printStackTrace()
            tablas
        } finally {
            db.close()
        }
    }
}

data class UsuarioLocal(
    val usuario: String,
    val identificacion: String,
    val nombre: String
)