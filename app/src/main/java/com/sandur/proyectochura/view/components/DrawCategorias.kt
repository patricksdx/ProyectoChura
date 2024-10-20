package com.sandur.proyectochura.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.sandur.proyectochura.model.Categorias

@Composable
fun DrawCategorias(item: Categorias, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(vertical = 10.dp, horizontal = 5.dp)
            .background(MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(10.dp))
            .clickable {
                navController.navigate("products/${item.idcategoria}/${item.nombrecategoria}")
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
        ) {
            if (item.imagencategoria == "https://api-proyectochura-express.onrender.com/") {
                Text(
                    text = "No se encontró imagen",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 60.dp)
                )
            } else {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    model = item.imagencategoria,
                    contentDescription = "Imagen de ${item.nombrecategoria}",
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Black.copy(alpha = 0.4f))
                )
            }

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.nombrecategoria,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start,
                    color = Color.White
                )
                Text(
                    text = "Total: ${item.total}",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Start,
                    color = Color.White
                )
            }
        }
    }
}
