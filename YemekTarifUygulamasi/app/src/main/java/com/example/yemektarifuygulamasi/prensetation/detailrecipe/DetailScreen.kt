package com.example.yemektarifuygulamasi.prensetation.detailrecipe

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.yemektarifuygulamasi.R
import com.example.yemektarifuygulamasi.data.remote.localdtb.LocalViewModel
import com.example.yemektarifuygulamasi.extensions.toRecipesEntity

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Composable
fun DetailScreen(
    id: Int,
    viewModel: DetailScreenViewModel,
    localViewModel: LocalViewModel
) {

    var isFavorite by remember { mutableStateOf(false) }

    viewModel.state.value.recipes?.let { recipe ->
        isFavorite = localViewModel.isFavorite(recipe.id)
    }
    val detailState = viewModel.state.value.recipes




    LaunchedEffect(key1 = true) {
        viewModel.getDteailScreen(id)
        Log.e("logid", id.toString())
    }



    val state = viewModel.state.value.recipes

    Scaffold(
        topBar = {
            TopAppBar(
                title = { state?.title?.let { Text(text = it) } },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        LazyColumn {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        contentAlignment = Alignment.TopEnd // Favori ikonu sağ üst köşede
                    ) {
                        val painter: Painter = if (state?.image.isNullOrEmpty()) {
                            rememberImagePainter(R.drawable.ic_launcher_background)
                        } else {
                            rememberImagePainter(state?.image)
                        }
                        Image(
                            painter = painter,
                            contentDescription = state?.title ?: "Tarif Resmi",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        )


                        IconButton(
                            onClick = {
                                isFavorite = !isFavorite
                                localViewModel.toggleFavorite(detailState!!.toRecipesEntity(isFavorite = isFavorite))
                            },
                            modifier = Modifier.padding(16.dp) // Kenarlardan boşluk
                        ) {
                            Icon(
                                painter = painterResource(
                                    if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
                                ),
                                contentDescription = "Favori İkonu",
                                tint = Color.Red,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Tarif Bilgileri Kartı
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Tarif Bilgileri",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Hazırlama Süresi: ${state?.readyInMinutes ?: "Bilinmiyor"} dakika")
                            Text(text = "Pişirme Süresi: ${state?.cookingMinutes ?: "Bilinmiyor"} dakika")
                            Text(text = "Sağlık Skoru: ${state?.healthScore ?: "Bilinmiyor"}")
                            Text(text = "Vegan: ${if (state?.vegan == true) "Evet" else "Hayır"}")
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Talimatlar",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    if (state?.instructions.isNullOrEmpty()) {
                        Text(text = "Talimatlar mevcut değil.", style = MaterialTheme.typography.bodyLarge)
                    } else {
                        state?.instructions?.split("\n")?.forEachIndexed { index, instruction ->
                            Text(
                                text = "${index + 1}. $instruction",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Spacer(modifier = Modifier.weight(1f))

                    if (viewModel.state.value.error.isEmpty()) {
                        Log.e("hata", "veri çekilemedi1")
                    }

                    if (viewModel.state.value.isLoading) {
                        Log.e("hata", "veri çekilemedi2")
                    }
                }
            }
        }
    }
}