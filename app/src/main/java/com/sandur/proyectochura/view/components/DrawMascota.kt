package com.sandur.proyectochura.view.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sandur.proyectochura.model.Mascota

@Composable
fun DrawMascota(item: Mascota) {
    val context = LocalContext.current

    Log.d("DrawMascota", "Mascota: $item")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .height(310.dp)
            .clickable {
                // Lógica para manejar el clic
                /*
                val intent = Intent(context, ProductDetailsActivity::class.java).apply {
                    putExtra("idproducto", item.idmascota)
                    putExtra("nombre", item.nombre)
                }
                context.startActivity(intent)
                */
            },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                // Comprobar si la foto no es nula o vacía
                if (item.foto.isNotEmpty() && item.foto != "null") {
                    AsyncImage(
                        model = item.foto,
                        contentDescription = "Imagen de ${item.nombre}",
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                } else {
                    Text(
                        text = "No se encontró imagen",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 60.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = item.nombre,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 15.sp,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = item.razamascota,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 15.sp,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = item.especiemascota,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 15.sp,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}
