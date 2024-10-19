package com.sandur.proyectochura.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sandur.proyectochura.model.Mascota
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.MarsSolid
import compose.icons.lineawesomeicons.VenusSolid

@Composable
fun DrawMascota(item: Mascota) {
    Box(
        modifier = Modifier
            .height(270.dp)
            .padding(vertical = 10.dp, horizontal = 5.dp)
            .background(MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(10.dp))
            .clickable {
                // Lógica para manejar el clic
            }
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            if (item.foto == "https://api-proyectochura-express.onrender.com/") {
                Text(
                    text = "No se encontró imagen",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 60.dp)
                )
            } else {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                        .height(150.dp)
                        .width(150.dp)
                        .background(MaterialTheme.colorScheme.background),
                    contentScale = ContentScale.Crop,
                    model = item.foto,
                    contentDescription = "Imagen de ${item.nombre}",
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 155.dp, start = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.nombre,
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f) // Ocupa el espacio restante
                )
                Icon(
                    imageVector = if (item.sexo == 1) LineAwesomeIcons.MarsSolid else LineAwesomeIcons.VenusSolid,
                    modifier = Modifier.size(18.dp),
                    contentDescription = "Sex",
                    tint = if (item.sexo == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.width(5.dp))
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = item.especiemascota,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    Text(
                        text = item.razamascota,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                }
                Text(
                    text = item.ubicacion,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

        }
    }
}
