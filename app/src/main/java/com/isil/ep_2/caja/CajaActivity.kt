package com.isil.ep_2.caja

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.isil.ep_2.datos.DatosCaja
import com.isil.ep_2.ui.theme.Ep2Theme
import com.isil.ep_2.ui.theme.neutral1
import com.isil.ep_2.ui.theme.neutral3
import com.isil.ep_2.ui.theme.neutral6
import com.isil.ep_2.ui.theme.primary
import com.isil.ep_2.ui.theme.warning

class CajaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arraylist = ArrayList<HashMap<String, String>>()
        leerDatos()

    }

    private fun leerDatos() {
        val arrayList = ArrayList<HashMap<String, String>>()
        val datosCaja = DatosCaja(this)
        val cursor: Cursor = datosCaja.movimientos(datosCaja)

        // Obtener el saldo total, total de ingresos y total de gastos
        val saldoTotalCursor: Cursor = datosCaja.saldoTotal(datosCaja)
        val totalIngresosCursor: Cursor = datosCaja.totalIngresos(datosCaja)
        val totalGastosCursor: Cursor = datosCaja.totalGastos(datosCaja)

        // Obtener los valores
        var saldoTotal: String? = null
        var totalIngresos: String? = null
        var totalGastos: String? = null

        if (cursor.count==0) {
            datosCaja.registrarMovimiento(datosCaja, "Trabajos", 1025.0f, 1)
            datosCaja.registrarMovimiento(datosCaja, "Internet", 150.0f, -1)
        }
        if (saldoTotalCursor.moveToFirst()) {
            saldoTotal =
                saldoTotalCursor.getString(saldoTotalCursor.getColumnIndexOrThrow("saldoTotal"))
        }

        if (totalIngresosCursor.moveToFirst()) {
            totalIngresos =
                totalIngresosCursor.getString(totalIngresosCursor.getColumnIndexOrThrow("totalIngresos"))
        }

        if (totalGastosCursor.moveToFirst()) {
            totalGastos =
                totalGastosCursor.getString(totalGastosCursor.getColumnIndexOrThrow("totalGastos"))
        }

        if (cursor.moveToFirst()) {
            do {
                val map = HashMap<String, String>()
                map["idmovimiento"] = cursor.getString(cursor.getColumnIndexOrThrow("idmovimiento"))
                map["fecha"] = cursor.getString(cursor.getColumnIndexOrThrow("fecha"))
                map["descripcion"] = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
                map["monto"] = cursor.getString(cursor.getColumnIndexOrThrow("monto"))
                map["tipo"] = cursor.getString(cursor.getColumnIndexOrThrow("tipo"))
                arrayList.add(map)
            } while (cursor.moveToNext())
            dibujar(arrayList, saldoTotal, totalIngresos, totalGastos)
            //println("listaaaaaaaaaaaaaaaaaaaaaaaa "+arrayList.toString())
        }


    }

    private fun dibujar(
        arrayList: ArrayList<HashMap<String, String>>,
        saldoTotal: String?,
        totalIngresos: String?,
        totalGastos: String?
    ) {
        val datosCaja = DatosCaja(this)
        val cursor: Cursor = datosCaja.saldoTotal(datosCaja)
        setContent {
            Ep2Theme {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.padding(all = 15.dp)) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = primary),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .padding(all = 15.dp)
                                .shadow(elevation = 8.dp)

                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            ) {


                                // Mostrar el total de ingresos
                                Text(
                                    text = "Total de Ingresos: S/ $totalIngresos",
                                    style = MaterialTheme.typography.labelLarge
                                )

                                // Mostrar el total de gastos
                                Text(
                                    text = "Total de Gastos: S/ $totalGastos",
                                    style = MaterialTheme.typography.labelLarge
                                )
                                Text(
                                    text = "Total: S/ $saldoTotal",
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        }
                        LazyColumn(content = {
                            items(items = arrayList, itemContent = {
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = neutral3),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .padding(all = 15.dp)
                                        .shadow(elevation = 8.dp)

                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(16.dp)
                                    ) {
                                        Text(
                                            text = "ID: " + it["idmovimiento"].toString() + " Descripcion: ",
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                        Text(
                                            text = it["descripcion"].toString(),
                                            style = MaterialTheme.typography.titleMedium
                                        )

                                        if (it["tipo"].toString() == "1")
                                            Text(
                                                text = "Monto: S/ " + String.format(
                                                    "%.2f", it["monto"]!!.toFloat()
                                                ),
                                                color = primary
                                            )
                                        else
                                            Text(
                                                text = "Monto: S/ " + String.format(
                                                    "%.2f", it["monto"]!!.toFloat()
                                                ),
                                                color = warning
                                            )

                                        Text(
                                            text = "Fecha: " + it["fecha"].toString(),
                                            style = MaterialTheme.typography.labelSmall
                                        )

                                        if (it["tipo"].toString() == "1")
                                            Text("Ingreso", style = MaterialTheme.typography.labelMedium, color = neutral6)
                                        else
                                            Text("Gasto",style=MaterialTheme.typography.labelMedium, color = neutral6)
                                    }

                                }
                            })
                        })
                    }
                    FloatingActionButton(
                        onClick = {
                            startActivity(
                                Intent(
                                    Intent(
                                        this@CajaActivity, CajaInsertActivity::class.java
                                    )
                                )
                            )
                        },
                        modifier = Modifier
                            .padding(all = 20.dp)
                            .align(alignment = Alignment.BottomEnd)
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = null)
                    }
                }
            }
        }
    }
}


