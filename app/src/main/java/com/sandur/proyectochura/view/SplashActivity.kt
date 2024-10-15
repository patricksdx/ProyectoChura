package com.sandur.proyectochura.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sandur.proyectochura.R
import com.sandur.proyectochura.ui.theme.ProyectoChuraTheme

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            /*
            var startAnimation by remember { mutableStateOf(false) }
            val offsety by animateDpAsState(
                targetValue = if(startAnimation) 0.dp else 1500.dp,
                animationSpec = tween(durationMillis = 1000)
            )
            val alpha by animateFloatAsState(
                targetValue = if(startAnimation) 1f else 0f,
                animationSpec = tween(durationMillis = 3000)
            )
            val scale by animateFloatAsState(
                targetValue = if(startAnimation) 1.5f else 3f,
                animationSpec = tween(durationMillis = 2000)
            )
            */
            ProyectoChuraTheme {
                /*
                LaunchedEffect(key1 = true) {
                    startAnimation = true
                    delay(4000)
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                }
                */
                AdoPetScreen()
            }
        }
    }
}
@Composable
fun AdoPetScreen() {
    val context = LocalContext.current

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                PetGrid()
                Text(
                    text = "Look for Pets around you",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 60.dp, vertical = 10.dp)
                )
                Text(
                    text = "Search for your new best friend using the AdoPet app!",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 5.dp, start = 50.dp, end = 50.dp)
                )
                Button(
                    modifier = Modifier.padding(top = 20.dp),
                    onClick = {
                        context.startActivity(Intent(context, MainActivity::class.java))
                    }
                ) {
                    Text(text = "Start Now")
                }
            }
        }
    }
}

@Composable
fun PetGrid(modifier: Modifier = Modifier) {
    val petImages = listOf(
        R.drawable.pet1, R.drawable.pet2, R.drawable.pet3, R.drawable.pet4,
        R.drawable.pet5, R.drawable.pet6, R.drawable.pet7, R.drawable.pet8,
        R.drawable.pet9, R.drawable.pet10, R.drawable.pet11, R.drawable.pet12
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.padding(16.dp)
            .padding(horizontal = 40.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(petImages.size) { index ->
            Image(
                painter = painterResource(id = petImages[index]),
                contentDescription = "Pet",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(90.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .fillMaxSize()
            )
        }
    }
}