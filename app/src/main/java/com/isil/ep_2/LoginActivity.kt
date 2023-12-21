package com.isil.ep_2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.isil.ep_2.ui.theme.Ep2Theme
import com.isil.ep_2.ui.theme.neutral6
import com.isil.ep_2.ui.theme.warning
import com.isil.ep_2.utils.Total
import com.isil.ep_2.utils.UserStore
import kotlinx.coroutines.launch
import org.json.JSONArray

@OptIn(ExperimentalMaterial3Api::class)
class LoginActivity : ComponentActivity() {
    var estadoChecked: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var usuario by remember { mutableStateOf("") }
            var clave by remember { mutableStateOf("") }
            var guardarSesion by remember { mutableStateOf(false) }
            var usuarioValido by remember { mutableStateOf(true) }
            Ep2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()

                    ) {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Sign In",
                                    style = MaterialTheme.typography.titleLarge,
                                    textAlign = TextAlign.Center
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
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            verticalArrangement = Arrangement.Bottom,
                            ) {
                            OutlinedTextField(value = usuario,
                                label = { Text(text = "Nombre de usuario", color = neutral6) },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth(),
                                onValueChange = {
                                    usuario = it
                                    usuarioValido = it.isNotEmpty()
                                })
                            Spacer(modifier = Modifier.size(12.dp))
                            OutlinedTextField(value = clave,
                                label = { Text(text = "Contraseña", color = neutral6) },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth(),
                                visualTransformation = PasswordVisualTransformation(),
                                onValueChange = {
                                    clave = it
                                })
                            Spacer(modifier = Modifier.size(12.dp))
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                onClick = {
                                    usuarioValido = usuario != ""
                                    leerServicioIniciarSesion(usuario, clave)
                                },
                                shape = RoundedCornerShape(12.dp),
                            ) {
                                Text(text = "INICIAR SESIÓN")
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End
                            ) {
                                Text(text = "Desea guardar la sesión")
                                Checkbox(checked = guardarSesion, onCheckedChange = {
                                    guardarSesion = it
                                    estadoChecked = it
                                })
                            }
                            Spacer(modifier = Modifier.size(12.dp))
                            if (!usuarioValido) {
                                Text(text = "Debe ingresar el nombre de usuario", color = warning)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun leerServicioIniciarSesion(usuario: String, clave: String) {
        val queue = Volley.newRequestQueue(this)
        val url = Total.rutaServicio + "iniciarsesion.php"

        val stringRequest = object : StringRequest(Request.Method.POST, url, { response ->
            Log.d("DATOS", response)
            verificarInicioSesion(response)
        }, {
            Log.d("ERRROR", it.message.toString())
        }) {
            override fun getParams(): MutableMap<String, String>? {
                val map = HashMap<String, String>()
                map.put("usuario", usuario)
                map.put("clave", clave)
                return map
            }
        }

        queue.add(stringRequest)
    }

    private fun verificarInicioSesion(response: String?) {

        when (response) {
            "-1" -> Toast.makeText(
                this, "El usuario no existe", Toast.LENGTH_SHORT
            ).show()

            "-2" -> Toast.makeText(
                this, "La contraseña es incorrecta", Toast.LENGTH_SHORT
            ).show()

            else -> {
                Toast.makeText(
                    this, "Bienvenido", Toast.LENGTH_SHORT
                ).show()
                Total.usuarioActivo = JSONArray(response).getJSONObject(0)
                startActivity(Intent(this, MainActivity::class.java))
                verificarGuardarSesion(response)
            }
        }
    }

    private fun verificarGuardarSesion(response: String?) {
        if (estadoChecked) {
            val userStore = UserStore(this)
            lifecycleScope.launch {
                userStore.setDatosUsuario(response.toString())
            }
        }
    }
}
