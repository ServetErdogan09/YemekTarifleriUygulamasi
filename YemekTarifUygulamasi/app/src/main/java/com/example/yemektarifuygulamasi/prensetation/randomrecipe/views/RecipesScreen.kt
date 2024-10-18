package com.example.yemektarifuygulamasi.prensetation.randomrecipe.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.yemektarifuygulamasi.data.remote.localdtb.LocalViewModel
import com.example.yemektarifuygulamasi.data.remote.localdtb.RecipeEntity
import com.example.yemektarifuygulamasi.extensions.toRecipesEntity
import com.example.yemektarifuygulamasi.prensetation.randomrecipe.RandomRecipesViewModel

@Composable
fun RecipesScreen(
    modifier: Modifier = Modifier,
    viewModel : RandomRecipesViewModel,
    navController: NavController,
    localViewModel: LocalViewModel
) {
    val state = viewModel.state.value


    // Yükleme göstergesi
    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp),
                color = Color.Black
            )
        }
    }

             // Hata mesajı
            if (state.error.isNotEmpty()){
                Text(
                    text = viewModel.state.value.error,
                    color = Color.Red,
                    modifier = Modifier
                        .padding(16.dp)
                )

            }

            LazyColumn {
                items(viewModel.state.value.recipes){ recipes->
                    RecipesCardRow(modifier,recipes,navController, viewModel =localViewModel , entity = recipes.toRecipesEntity() )
                }

            }

        }


