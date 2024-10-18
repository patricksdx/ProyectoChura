package com.sandur.proyectochura.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.sandur.proyectochura.model.Categorias

@Composable
fun DrawCategorias(item: Categorias, navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .height(275.dp)
            .background(MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(25.dp))
            .clickable {
                // Navegar a la pantalla de productos
                navController.navigate("products/${item.idcategoria}/${item.nombrecategoria}")
            }
    ) {
        // Imagen o mensaje de error si no hay imagen
        Box(modifier = Modifier.fillMaxWidth()) {
            if (item.imagencategoria == "https://api-proyectochura-express.onrender.com/") {
                Text(
                    text = "No se encontró imagen",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 60.dp)
                )
            } else {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                        .background(MaterialTheme.colorScheme.background)
                        .height(150.dp)
                        .width(150.dp),
                    contentScale = ContentScale.Crop,
                    model = item.imagencategoria,
                    contentDescription = "Imagen de ${item.nombrecategoria}",
                )
            }
        }

        // Box para mostrar el nombre y la descripción de la categoría
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 170.dp, start = 10.dp) // Ajusta la posición
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.nombrecategoria,
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = item.descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Total: ${item.total}",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}
