package com.example.yemektarifuygulamasi.presentation.favorites

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.SwipeToDismiss
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.yemektarifuygulamasi.R
import com.example.yemektarifuygulamasi.data.remote.localdtb.LocalViewModel
import com.example.yemektarifuygulamasi.data.remote.localdtb.RecipeEntity
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun FavoriteRecipesScreen(
    viewModel: LocalViewModel,
    navController: NavController
) {
    val favoriteRecipes by viewModel.recipesFlow.collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()


    BackHandler {

    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Favori Tarifler", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (favoriteRecipes.isEmpty()) {
                item {
                    FavoriteEmptyList()
                }
            } else {
                items(favoriteRecipes, key = { it.id }) { recipe ->
                    var showConfirmationDialog by remember { mutableStateOf(false) }
                    val dismissState = rememberDismissState()


                    if (dismissState.isDismissed(DismissDirection.StartToEnd)) {
                        showConfirmationDialog = true
                    }

                    SwipeToDismiss(
                        state = dismissState,
                        background = {
                            val color = if (dismissState.dismissDirection == DismissDirection.StartToEnd) {
                                Color.Red
                            } else {
                                Color.Gray
                            }
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(16.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Icon(painter = painterResource(R.drawable.delete), contentDescription = "silme")
                            }
                        },
                        directions = setOf(DismissDirection.StartToEnd),
                        dismissContent = {
                            FavoriteRecipeItem(recipe, navController = navController , id = recipe.id)
                        }

                    )

                    if(dismissState.isDismissed(DismissDirection.StartToEnd)){
                        viewModel.deleteRecipe(recipe)
                    }

                }
            }
        }
    }
}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun FavoriteRecipeItem(recipe: RecipeEntity,navController: NavController , id : Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp).clickable {
                navController.navigate(route = "DetailScreen/${id}")
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Tarifin resmini göster
            val painter: Painter = rememberImagePainter(recipe.image)
            Image(
                painter = painter,
                contentDescription = recipe.title,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp)
            )
            Column {
                Text(text = recipe.title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Sağlık Skoru: ${recipe.healthScore}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Pişirme Süresi: ${recipe.cookingMinutes ?: 0} dakika", style = MaterialTheme.typography.bodySmall)

            }
        }
    }
}

@Composable
fun FavoriteEmptyList(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.image),
            contentDescription = "Boş resim",
            modifier = Modifier.fillMaxSize()
        )
    }
}