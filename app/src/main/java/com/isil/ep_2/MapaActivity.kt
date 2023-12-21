package com.isil.ep_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

class MapaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras
        val latitud = bundle?.getString("latitud")
        val longitud = bundle?.getString("longitud")
        println(latitud) //esto devuelve 43.64608130492877
        println(longitud) //esto devuelve -79.39916429271426

        setContent {
            val ubicacionBuscada = LatLng(latitud!!.toDouble(), longitud!!.toDouble())
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(ubicacionBuscada, 10f)
            }
           GoogleMap(   modifier = Modifier.fillMaxSize(),
               cameraPositionState = cameraPositionState) {
               Marker(
                   state = MarkerState(position = ubicacionBuscada),
                   title = "Envio",
                   snippet = "Lugar de Envío"
               )
           }
        }
    }
}

