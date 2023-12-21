package com.isil.ep_2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.lifecycleScope
import com.isil.ep_2.utils.Total
import com.isil.ep_2.utils.UserStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.json.JSONArray

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaunchedEffect(key1 = true){
                delay(3000)
                val userStore = UserStore(this@SplashActivity)
                lifecycleScope.launch {
                    var dato = userStore.getDatosUsuario.first()
                    if(dato == ""){
                        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                    }
                    else{
                        Total.usuarioActivo = JSONArray(dato).getJSONObject(0)
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    }
                }
                finish()
            }
            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ){
                Image(painter = painterResource(id = R.drawable.principal),
                    contentDescription = null )
            }
        }
    }
}

