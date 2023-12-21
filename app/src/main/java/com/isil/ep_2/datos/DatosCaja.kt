package com.isil.ep_2.datos

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
// Definir constantes
const val TABLE_MOVIMIENTOS = "movimientos"
const val COLUMN_ID_MOVIMIENTO = "idmovimiento"
const val COLUMN_FECHA = "fecha"
const val COLUMN_DESCRIPCION = "descripcion"
const val COLUMN_MONTO = "monto"
const val COLUMN_TIPO = "tipo"

class DatosCaja(context: Context) : SQLiteOpenHelper(context, "micaja.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE $TABLE_MOVIMIENTOS(" +
                    "$COLUMN_ID_MOVIMIENTO INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMN_FECHA DATETIME DEFAULT CURRENT_TIMESTAMP," +
                    "$COLUMN_DESCRIPCION TEXT," +
                    "$COLUMN_MONTO FLOAT," +
                    "$COLUMN_TIPO INTEGER)"
        )
    }

    fun registrarMovimiento(
        datosCaja: DatosCaja,
        descripcion: String,
        monto: Float,
        tipo: Int
    ): Long {
        val db = datosCaja.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_DESCRIPCION, descripcion)
            put(COLUMN_MONTO, monto)
            put(COLUMN_TIPO, tipo)
        }
        return db.insert(TABLE_MOVIMIENTOS, null, contentValues)
    }

    // Obtener todos los movimientos financieros ordenados por ID de forma descendente
    fun movimientos(datosCaja: DatosCaja): Cursor {
        val db = datosCaja.readableDatabase
        val sql = "SELECT * FROM $TABLE_MOVIMIENTOS ORDER BY $COLUMN_ID_MOVIMIENTO DESC"
        return db.rawQuery(sql, null)
    }

    // Obtener el saldo total de todos los movimientos financieros
    fun saldoTotal(datosCaja: DatosCaja): Cursor {
        val db = datosCaja.readableDatabase
        val sql = "SELECT SUM($COLUMN_MONTO * $COLUMN_TIPO) AS saldoTotal FROM $TABLE_MOVIMIENTOS"
        return db.rawQuery(sql, null)
    }

    // Obtener el total de gastos
    fun totalGastos(datosCaja: DatosCaja): Cursor {
        val db = datosCaja.readableDatabase
        val sql = "SELECT SUM($COLUMN_MONTO * $COLUMN_TIPO) AS totalGastos FROM $TABLE_MOVIMIENTOS WHERE $COLUMN_TIPO=-1"
        return db.rawQuery(sql, null)
    }

    // Obtener el total de ingresos
    fun totalIngresos(datosCaja: DatosCaja): Cursor {
        val db = datosCaja.readableDatabase
        val sql = "SELECT SUM($COLUMN_MONTO * $COLUMN_TIPO) AS totalIngresos FROM $TABLE_MOVIMIENTOS WHERE $COLUMN_TIPO=1"
        return db.rawQuery(sql, null)
    }

    // ...

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Manejar actualizaciones de la base de datos aquí si es necesario
    }

}