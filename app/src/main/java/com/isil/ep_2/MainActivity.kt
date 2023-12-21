package com.isil.ep_2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.isil.ep_2.ui.theme.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import com.isil.ep_2.caja.CajaActivity
import com.isil.ep_2.ui.theme.Ep2Theme
import com.isil.ep_2.utils.UserStore
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var mostrarAlert by remember { mutableStateOf(false) }
            Ep2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = neutral3
                ) {

                    Box {
                        AsyncImage(
                            model = R.drawable.essalud,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(all = 30.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "EVALUACION PERMANENTE 4",
                            color = primary,
                            style = MaterialTheme.typography.titleLarge
                        )

                        // Row for the buttons
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(50.dp)
                                    .padding(start = 8.dp),
                                onClick = {
                                    startActivity(
                                        Intent(
                                            this@MainActivity,
                                            PedidoActivity::class.java
                                        )
                                    )
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(primary)
                            ) {
                                Text(
                                    text = "PEDIDOS",
                                    color = neutral1,
                                )
                            }

                            Button(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(50.dp)
                                    .padding(start = 8.dp), // Adjust padding as needed
                                onClick = {
                                    startActivity(
                                        Intent(
                                            this@MainActivity,
                                            HospitalActivity::class.java
                                        )
                                    )
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(primary)
                            ) {
                                Text(
                                    text = "SERVICIO",
                                    color = neutral1,
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Existing "Caja" button
                            Button(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(50.dp)
                                    .padding(start = 8.dp),
                                onClick = {
                                    startActivity(
                                        Intent(
                                            this@MainActivity,
                                            CajaActivity::class.java
                                        )
                                    )
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(primary)
                            ) {
                                Text(
                                    text = "CAJA",
                                    color = neutral1,
                                )
                            }

                            Button(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(50.dp)
                                    .padding(start = 8.dp), // Adjust padding as needed
                                onClick = {
                                    startActivity(
                                        Intent(
                                            this@MainActivity,
                                            EnvioActivity::class.java
                                        )
                                    )
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(primary)
                            ) {
                                Text(
                                    text = "MAPA",
                                    color = neutral1,
                                )
                            }
                        }
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .padding(all = 5.dp),
                            onClick = {
                                mostrarAlert = true
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(warning)
                        ) {
                            Text(
                                text = "CERRAR SESION",
                                color = neutral1,
                            )
                        }
                    }


                    if (mostrarAlert) {
                        AlertDialog(
                            icon = {
                                Icon(Icons.Filled.Warning, contentDescription = null)
                            },
                            title = {
                                Text(text = "Cerrar sesión")
                            },
                            text = {
                                Text(text = "¿Está seguro que desea cerrar la sesión?")
                            },
                            onDismissRequest = {},
                            confirmButton = {
                                Button(onClick = {
                                    mostrarAlert = false
                                    cerrarSesion()
                                }) {
                                    Text(text = "Sí")
                                }
                            },
                            dismissButton = {
                                Button(onClick = {
                                    mostrarAlert = false
                                }) {
                                    Text(text = "No")
                                }
                            },
                        )
                    }
                }
            }
        }

    }
    private fun cerrarSesion() {
        val userStore = UserStore(this)
        lifecycleScope.launch {
            userStore.setDatosUsuario("")
        }
        startActivity(Intent(this, SplashActivity::class.java))
    }
}

