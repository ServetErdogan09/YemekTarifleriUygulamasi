package com.example.yemektarifuygulamasi.prensetation.searchrecipe


import android.icu.text.CaseMap.Title
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.yemektarifuygulamasi.data.remote.dto.SearchFoodRecipesDto
import com.example.yemektarifuygulamasi.domain.model.RandomFoodRecipes
import dagger.hilt.android.AndroidEntryPoint

@Composable
fun SearchCardRow(
    modifier: Modifier = Modifier,
    title: String,
    imageUrl: String,
    id : Int,
    navController: NavController
) {


                Card(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(8.dp).clickable {
                            navController.navigate("DetailScreen/$id")
                        },
                    elevation = CardDefaults.cardElevation(8.dp),
                    shape = RectangleShape // Kartın şekli
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start
                    ) {
                        // Resim bileşeni
                        NetworkImage(imageUrl = imageUrl )

                        Spacer(modifier = Modifier.height(8.dp))
                        // Tarifin başlığı
                        Text(
                            text = title,
                            fontSize = 20.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }


            }


@OptIn(ExperimentalCoilApi::class)
@Composable
private fun NetworkImage(imageUrl: String) {
    val painter = rememberImagePainter(imageUrl)
    val painterState = painter.state

    Box(
        modifier = Modifier.size(300.dp),
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
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RectangleShape)
                    )
                }
            }
        }
    }
}