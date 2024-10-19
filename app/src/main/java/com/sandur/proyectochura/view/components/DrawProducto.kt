package com.sandur.proyectochura.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.sandur.proyectochura.R
import com.sandur.proyectochura.model.Producto

@Composable
fun DrawProducto(producto: Producto, navController: NavHostController) {
    Box(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .background(MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(12.dp))
            .clickable {
                // Lógica para manejar el clic
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
        ) {
            if(producto.imagenproducto == "https://api-proyectochura-express.onrender.com/undefined"){
                Image(
                    painter = painterResource(R.drawable.pet1),
                    contentDescription = producto.nombreproducto,
                    modifier = Modifier
                        .height(170.dp)
                        .fillMaxWidth(0.4f)
                        .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                AsyncImage(
                    model = producto.imagenproducto,
                    contentDescription = producto.nombreproducto,
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = producto.nombreproducto,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    fontSize = 15.sp
                )
                Column {
                    if (!producto.preciodescuento.isNullOrEmpty()) {
                        Text(
                            text = "Antes: ${producto.precio}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                fontWeight = FontWeight.Light,
                                fontSize = 12.sp
                            ),
                        )
                        Text(
                            text = "Descuento: ${producto.preciodescuento}",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        )
                    } else {
                        Text(
                            text = "Precio: ${producto.precio}",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }

                Text(
                    text = "Disponibles: ${producto.cantidad}",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp
                )

                Box(
                    modifier = Modifier
                        .clickable(onClick = { /* Handle click event */ })
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .shadow(4.dp, RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(vertical = 5.dp, horizontal = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Agregar al carrito",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}