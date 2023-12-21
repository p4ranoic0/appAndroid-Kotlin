package com.isil.ep_2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.isil.ep_2.ui.theme.Ep2Theme
import com.isil.ep_2.ui.theme.neutral3
import com.isil.ep_2.ui.theme.neutral4
import com.isil.ep_2.ui.theme.neutral5
import com.isil.ep_2.ui.theme.primary
import com.isil.ep_2.utils.Total
import org.json.JSONArray

@OptIn(ExperimentalMaterial3Api::class)
class DetalleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras
        val idpedido = bundle!!.getString("idpedido")
        getProductos(idpedido)
    }

    private fun getProductos(idpedido: String?) {
        val queue = Volley.newRequestQueue(this)
        val url = Total.rutaServicio + "pedidosdetalle.php?idpedido=" + idpedido
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("DATOS", response)
                mostrarLista(response)
            },
            {
                Log.d("ERROR----------------->>>>", it.message.toString())
            })
        queue.add(stringRequest)
    }

    private fun mostrarLista(response: String?) {
        val jsonArray = JSONArray(response)
        val arrayList = ArrayList<HashMap<String, String>>()
        for (i in 0 until jsonArray.length()) {
            val idproducto = jsonArray.getJSONObject(i).getString("idproducto")
            val nombre = jsonArray.getJSONObject(i).getString("nombre")
            val precio = jsonArray.getJSONObject(i).getString("precio")
            val cantidad = jsonArray.getJSONObject(i).getString("cantidad")
            val imagenchica = jsonArray.getJSONObject(i).getString("imagenchica")
            val detalle = jsonArray.getJSONObject(i).getString("detalle")

            val map = HashMap<String, String>()
            map["idproducto"] = idproducto
            map["nombre"] = nombre
            map["precio"] = precio
            map["cantidad"] = cantidad
            map["imagenchica"] = imagenchica
            map["detalle"] = detalle
            arrayList.add(map)
        }
        dibujar(arrayList)
    }

    private fun dibujar(arrayList: ArrayList<HashMap<String, String>>) {
        setContent {
            Ep2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        TopAppBar(
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
                            title = { Text(text = " DETALLE") },
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background)
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            LazyRow(
                                content = {
                                    items(items = arrayList, itemContent = { producto ->
                                        Box(
                                            modifier = Modifier
                                                .fillParentMaxSize()
                                        ) {
                                            AsyncImage(
                                                model = Total.rutaServicio + producto["imagenchica"],
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .fillMaxSize(),
                                                contentScale = ContentScale.Crop
                                            )
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(all = dimensionResource(id = R.dimen.espacio_chiquito)),
                                                verticalArrangement = Arrangement.Bottom,
                                                horizontalAlignment = Alignment.End
                                            ) {
                                                Text(
                                                    text = producto["nombre"].toString() + " \n" + producto["detalle"].toString(),
                                                    style = MaterialTheme.typography.titleMedium,
                                                    color = neutral3,
                                                    modifier = Modifier
                                                        .background(primary)
                                                        .padding(
                                                            horizontal = 10.dp,
                                                            vertical = 5.dp
                                                        )
                                                )
                                                Text(
                                                    text = "S/" + producto["precio"].toString(),
                                                    color = neutral4,
                                                    modifier = Modifier
                                                        .background(neutral5)
                                                        .padding(
                                                            horizontal = 10.dp,
                                                            vertical = 5.dp
                                                        )
                                                )
                                            }
                                        }
                                    })
                                }
                            )
                        }
                    }
                }
            }
        }
    }

}


