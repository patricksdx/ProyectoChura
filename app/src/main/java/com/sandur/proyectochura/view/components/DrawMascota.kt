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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import coil.compose.AsyncImage
import com.sandur.proyectochura.model.Mascota
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.Heart
import compose.icons.lineawesomeicons.MarsSolid
import compose.icons.lineawesomeicons.VenusSolid

@Composable
fun DrawMascota(item: Mascota) {

    //val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .height(275.dp)
            .background(MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(25.dp))
            .clickable {
                // Lógica para manejar el clic
            }
    ) {
        // Imagen o mensaje de error
        Box(modifier = Modifier.fillMaxWidth()) {
            if (item.foto == "https://api-proyectochura-express.onrender.com/") {
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
                    model = item.foto,
                    contentDescription = "Imagen de ${item.nombre}",
                )
            }
        }

        // Box para mostrar el nombre y la especie de la mascota
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 170.dp, start = 10.dp) // Ajusta la posición
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row (
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.nombre,
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        if(item.sexo == 1){
                            Icon(
                                imageVector = LineAwesomeIcons.MarsSolid,
                                modifier = Modifier.size(18.dp),
                                contentDescription = "Sex",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        } else {
                            Icon(
                                imageVector = LineAwesomeIcons.VenusSolid,
                                modifier = Modifier.size(18.dp),
                                contentDescription = "Sex",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                    Row {
                        Icon(
                            imageVector = LineAwesomeIcons.Heart,
                            modifier = Modifier.size(20.dp),
                            contentDescription = "Heart",
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = item.especiemascota + " | " + item.razamascota,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = item.ubicacion,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}