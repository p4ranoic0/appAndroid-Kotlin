package com.isil.ep_2.caja

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isil.ep_2.caja.ui.theme.Ep2Theme
import com.isil.ep_2.datos.DatosCaja
import com.isil.ep_2.ui.theme.neutral6

@OptIn(ExperimentalMaterial3Api::class)
class CajaInsertActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Ep2Theme {
                var descripcion by remember { mutableStateOf("") }
                var monto by remember { mutableStateOf("") }
                var tipoMovimiento by remember { mutableStateOf(-1) }
                Column(
                    modifier = Modifier.padding(all = 24.dp)
                ) {
                    TopAppBar(
                        title = {
                            Text(
                                text ="NUEVO MOVIMIENTO",
                                style = MaterialTheme.typography.titleLarge
                            )
                        },
                        navigationIcon = {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(35.dp)
                                    .clickable {
                                        onBackPressed()
                                    }
                            )
                        },
                    )

                    OutlinedTextField(value = descripcion,
                        label = { Text(text = "Descripcion",color = neutral6) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        onValueChange = {
                            descripcion = it
                        })
                    Spacer(modifier = Modifier.size(12.dp))
                    OutlinedTextField(value = monto,
                        label = { Text(text = "Monto", color = neutral6) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        onValueChange = {
                            monto = it
                        }
                    )
                    Spacer(modifier = Modifier.size(12.dp))
                    Switch(
                        checked = tipoMovimiento == 1,
                        onCheckedChange = { tipoMovimiento = if (it) 1 else -1 },
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    Text(text = "Tipo de Movimiento: ${if (tipoMovimiento == 1) "Ingreso" else "Gasto"}",style = MaterialTheme.typography.labelSmall)
                    Spacer(modifier = Modifier.size(12.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(top = 10.dp),
                        onClick = {
                            guardarDatos(descripcion, monto, tipoMovimiento)
                        },
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Text(text = "Guardar")
                    }
                }
            }
        }
    }

    private fun guardarDatos(descripcion: String, monto: String, tipo: Int) {
        val datosCaja = DatosCaja(this)
        val auto = datosCaja.registrarMovimiento(datosCaja, descripcion, monto.toFloat(), tipo)
        Toast.makeText(this, "id: " + auto, Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, CajaActivity::class.java))

    }
}
