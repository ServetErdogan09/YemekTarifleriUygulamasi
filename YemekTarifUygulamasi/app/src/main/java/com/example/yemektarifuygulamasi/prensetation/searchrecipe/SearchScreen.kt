package com.example.yemektarifuygulamasi.prensetation.searchrecipe

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchRecipesViewModel,
    navController: NavController
) {
    var searchQuery by remember { mutableStateOf("") }
    val state = viewModel.state.value //// ViewModel'den durumu alıyoruz

    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search TextField
        TextField(
            value = searchQuery,
            onValueChange = { newValue ->
                searchQuery = newValue
                scope.launch {
                    delay(1000)
                    viewModel.getSearchRecipes(searchQuery) // Çok sık istek göndermeyi önlemek için debounce eklenebilir.
                }
            },
            label = { Text("Search Recipes") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Loading State
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center)
            {
               CircularProgressIndicator(
                   color = Color.Gray
               )

            }
        }

        // Error State
        if (state.error.isNotEmpty()) {
            Text(text = "Error: ${state.error}", color = Color.Red)
        }


        // Success State
        state.result.let { recipes ->
            Column {
              recipes.forEach { recipe ->
                    SearchCardRow(modifier = Modifier, imageUrl = recipe.image ?: "", title = recipe.title ?: "Unknown Title" , id = recipe.id, navController = navController)
               }
            }
        }
    }
}