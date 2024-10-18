package com.example.yemektarifuygulamasi.domain.model

data class RandomFoodRecipes(
    val id: Int,
    val cookingMinutes: Int?,
    val healthScore: Int,
    val image: String,
    val vegan: Boolean,
    val instructions: String,
    val title: String
)
