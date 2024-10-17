package com.sandur.proyectochura.view.screens.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.sandur.proyectochura.R
import com.sandur.proyectochura.ui.theme.ProyectoChuraTheme
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.MapMarkedSolid
import compose.icons.lineawesomeicons.VideoSolid

@Composable
fun MainScreen(navController: NavHostController) {
    ProyectoChuraTheme {
        BodyContent(navController = navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BodyContent(navController: NavHostController){
    Scaffold (
        topBar = { TopAppBar(title = { Text(text = "") }) },
        bottomBar = { BottomAppBar {}}
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            PetLive()
            PetStories()
            PopularCategory()
        }
    }
}

@Composable
fun PetLive(modifier: Modifier = Modifier) {
    val petImages = listOf(
        R.drawable.pet1, R.drawable.pet2, R.drawable.pet3, R.drawable.pet4,
        R.drawable.pet5, R.drawable.pet6, R.drawable.pet7, R.drawable.pet8,
        R.drawable.pet9, R.drawable.pet10, R.drawable.pet11, R.drawable.pet12
    )

    Column(
        modifier = modifier.padding(horizontal = 20.dp)
    ) {
        Box {
            LazyRow(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
            ) {
                items(petImages.size) { index ->
                    Column() {
                        Box {
                            Image(
                                painter = painterResource(id = petImages[index]),
                                contentDescription = "Pet",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(120.dp)
                                    .height(120.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .padding(top = 85.dp)
                                    .wrapContentSize()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(MaterialTheme.colorScheme.primary)
                                    .width(100.dp)
                                    .height(25.dp)
                                    .clickable {
                                        // AGREGAR LOGICA PARA UNIRSE A LA TRANSMISIÓN EN VIVO
                                    }
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = LineAwesomeIcons.VideoSolid,
                                        contentDescription = "Join Live",
                                        modifier = Modifier.size(15.dp),
                                        tint = Color.White
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Join Live",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontSize = 10.sp,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Nombre",
                            style = MaterialTheme.typography.titleSmall,
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Edad",
                            style = MaterialTheme.typography.titleSmall,
                            fontSize = 12.5.sp
                        )
                        Row {
                            Icon(
                                imageVector = LineAwesomeIcons.MapMarkedSolid,
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.CenterVertically)
                                    .padding(1.dp),
                                contentDescription = "Shop",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                modifier = Modifier
                                    .width(100.dp)
                                    .padding(start = 10.dp),
                                text = "Ciudad Ciudad Ciudad Ciudad",
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PetStories(modifier: Modifier = Modifier) {

    val petImages = listOf(
        R.drawable.pet1, R.drawable.pet2, R.drawable.pet3, R.drawable.pet4,
        R.drawable.pet5, R.drawable.pet6, R.drawable.pet7, R.drawable.pet8,
        R.drawable.pet9, R.drawable.pet10, R.drawable.pet11, R.drawable.pet12
    )

    Column(
        modifier = modifier
            .padding(horizontal = 20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Top Stories",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 15.sp,
                modifier = Modifier.padding(end = 8.dp)
            )
            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .weight(1f)
                    .background(Color.Gray)
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(petImages.size) { index ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = petImages[index]),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Transparent, CircleShape)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "story.title",
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun PopularCategory(modifier: Modifier = Modifier) {
    val petImages = listOf(
        R.drawable.pet1, R.drawable.pet2, R.drawable.pet3, R.drawable.pet4,
        R.drawable.pet5, R.drawable.pet6, R.drawable.pet7, R.drawable.pet8,
        R.drawable.pet9, R.drawable.pet10, R.drawable.pet11, R.drawable.pet12
    )

    Column (
        modifier = modifier
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp)
    ) {
        Text(
            text = "Popular Categories",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 15.sp,
            textAlign = TextAlign.Start,
        )
        LazyRow(
            modifier = Modifier
                .padding(top = 15.dp, bottom = 30.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            items(petImages.size) { index ->
                Column (
                    modifier = Modifier
                        .clickable {
                            // AGREGAR LOGICA
                        }
                ) {
                    Image(
                        painter = painterResource(id = petImages[index]),
                        contentDescription = "Pet",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(120.dp)
                            .height(120.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .fillMaxSize()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        modifier = Modifier
                            .width(120.dp),
                        text = "Pet Toys",
                        style = MaterialTheme.typography.titleSmall,
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        modifier = Modifier
                            .width(120.dp),
                        text = "Toys Dogs and Cats",
                        style = MaterialTheme.typography.titleSmall,
                        fontSize = 12.sp
                    )
                    Text(
                        modifier = Modifier
                            .width(120.dp),
                        text = "Juguete Juguete Juguete Juguete",
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}
