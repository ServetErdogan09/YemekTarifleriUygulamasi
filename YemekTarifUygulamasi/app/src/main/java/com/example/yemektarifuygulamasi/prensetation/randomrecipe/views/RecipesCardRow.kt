package com.example.yemektarifuygulamasi.prensetation.randomrecipe.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.yemektarifuygulamasi.R
import com.example.yemektarifuygulamasi.data.remote.localdtb.LocalViewModel
import com.example.yemektarifuygulamasi.data.remote.localdtb.RecipeEntity
import com.example.yemektarifuygulamasi.domain.model.RandomFoodRecipes
import com.example.yemektarifuygulamasi.extensions.toRecipesEntity

@Composable
fun RecipesCardRow(
    modifier: Modifier = Modifier,
    randomFoodRecipes: RandomFoodRecipes,
    navController: NavController,
    viewModel: LocalViewModel,
    entity: RecipeEntity
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(viewModel.isFavorite(randomFoodRecipes.id)) }

    val favoriteIcon = if (isFavorite) {
        R.drawable.baseline_favorite_24
    } else {
        R.drawable.baseline_favorite_border_24
    }

    // LaunchedEffect to monitor favoriteRecipes changes
    LaunchedEffect(viewModel.favoriteRecipes) {
        isFavorite = viewModel.isFavorite(entity.id)
    }

    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        shape = CardDefaults.shape,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("DetailScreen/${randomFoodRecipes.id}")
                Log.e("logid", randomFoodRecipes.id.toString())
            },
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                NetworkImage(
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RectangleShape),
                    imageUrl = randomFoodRecipes.image
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = randomFoodRecipes.title,
                    fontSize = 22.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = if (isExpanded) randomFoodRecipes.instructions else "${randomFoodRecipes.instructions.take(100)}...",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = if (isExpanded) "Daha Az" else "Daha Fazla",
                    fontSize = 16.sp,
                    color = Color.Blue,
                    modifier = Modifier
                        .clickable {
                            isExpanded = !isExpanded
                        }
                        .padding(vertical = 10.dp)
                )
            }

            IconButton(
                onClick = {
                    Log.e("isFavorite" , "güncell $isFavorite" )
                    isFavorite = !isFavorite
                    Log.e("isFavorite" , "değiştirilen $isFavorite")
                    viewModel.toggleFavorite(randomFoodRecipes.toRecipesEntity(isFavorite = isFavorite))

                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(favoriteIcon),
                    contentDescription = "Favori",
                    tint = if (isFavorite) Color.Red else Color.Gray
                )
            }
        }
    }
}
@OptIn(ExperimentalCoilApi::class)
@Composable
private fun NetworkImage(modifier: Modifier = Modifier, imageUrl: String) {
    val painter = rememberImagePainter(imageUrl)
    val painterState = painter.state

    Box(
        modifier = Modifier.size(200.dp),
        contentAlignment = Alignment.Center
    ) {
        if (imageUrl.isEmpty()) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "",
                modifier = Modifier.clip(RectangleShape)
            )
        } else {
            when (painterState) {
                is coil.compose.ImagePainter.State.Loading -> {
                    CircularProgressIndicator(color = Color.Gray)
                }
                is coil.compose.ImagePainter.State.Error -> {
                    Text(text = "Resim yüklenemedi", color = Color.Red)
                }
                else -> {
                    Image(
                        painter = painter,
                        contentDescription = "Recipe Image",
                        modifier = modifier
                            .size(200.dp)
                            .clip(RectangleShape)
                    )
                }
            }
        }
    }
}