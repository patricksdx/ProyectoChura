package com.sandur.proyectochura.view.components

import android.widget.Space
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sandur.proyectochura.model.Pais

@Composable
fun DrawPais(pais: Pais) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = pais.codpais, style = MaterialTheme.typography.labelSmall)
        Text(text = pais.pais, style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.padding(vertical = 4.dp))

        Text(text = pais.capital, style = MaterialTheme.typography.bodySmall)
        Text(text = "Área: ${pais.area} km²", style = MaterialTheme.typography.bodySmall)
        Text(text = "Población: ${pais.poblacion}", style = MaterialTheme.typography.bodySmall)
        Text(text = "Continente: ${pais.continente}", style = MaterialTheme.typography.bodySmall)
    }
}