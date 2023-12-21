package com.isil.ep_2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.isil.ep_2.ui.theme.Ep2Theme
import com.isil.ep_2.ui.theme.neutral1
import com.isil.ep_2.ui.theme.primary
import com.isil.ep_2.ui.theme.stars
import com.isil.ep_2.utils.Total
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class)
class HospitalActivity : ComponentActivity() {
    private var selectedMedicoId by mutableStateOf<String?>(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMedicos();

    }

    private fun getMedicos() {
        val queue = Volley.newRequestQueue(this)
        val url = Total.rutaHospital + "medicos.php";
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("DATOS", response)
                mostrarLista(response)
            },
            {
                Log.d("ERROR--------->>>>>", it.message.toString())
            })
        queue.add(stringRequest)
    }

    private fun mostrarLista(response: String?) {
        val jsonArray = JSONArray(response)
        val arrayList = ArrayList<HashMap<String, String>>()

        val jsonList = (0 until jsonArray.length()).map { jsonArray.getJSONObject(it) }

        jsonList.forEach { jsonObject ->
            val map = HashMap<String, String>()
            map["id"] = jsonObject.getString("id")
            map["nombre"] = jsonObject.getString("nombre")
            map["apellidos"] = jsonObject.getString("apellidos")
            map["especialidad"] = jsonObject.getString("especialidad")
            map["calificacion"] = jsonObject.getString("calificacion")
            map["edad"] = jsonObject.getString("edad")
            arrayList.add(map)
        }



        dibujar(arrayList)
    }


    private fun dibujar(arrayList: ArrayList<HashMap<String, String>>) {
        setContent {
            Ep2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = primary
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "MEDICOS",
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
                                            finish()
                                        }
                                )
                            },
                        )


                        LazyVerticalGrid(columns = GridCells.Fixed(1), content = {
                            items(arrayList.size, itemContent = { posicion ->
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = neutral1),
                                    modifier = Modifier
                                        .height(250.dp)
                                        .padding(all = 12.dp)
                                        .shadow(elevation = 8.dp)


                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(20.dp),
                                        verticalAlignment = Alignment.Top,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {

                                            Text(
                                                text = arrayList[posicion]["nombre"]!!.toString() + " " +
                                                        arrayList[posicion]["apellidos"]!!.toString(),
                                                textAlign = TextAlign.Left,
                                                color = primary,
                                                modifier = Modifier
                                                    .padding(top = 4.dp)
                                                    .fillMaxWidth(),
                                                style = MaterialTheme.typography.titleLarge
                                            )
                                            Text(
                                                text = "Especialidad: " + arrayList[posicion]["especialidad"]!!.toString(),
                                                color = primary
                                            )
                                            Text(
                                                text = "Edad: " + arrayList[posicion]["edad"]!!.toString(),
                                                color = primary
                                            )
                                            Row {
                                                val calificacion =
                                                    arrayList[posicion]["calificacion"]!!.toFloat()
                                                for (i in 0 until calificacion.toInt()) {
                                                    Icon(
                                                        imageVector = Icons.Default.Star,
                                                        contentDescription = null,
                                                        tint = stars,
                                                        modifier = Modifier.size(24.dp)
                                                    )
                                                }
                                            }


                                        }
                                    }


                                }

                            })
                        })//LazyColumn

                    }
                }

            }
        }
    }

}

