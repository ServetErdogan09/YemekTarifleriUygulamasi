package com.example.yemektarifuygulamasi.domain.model

data class DetailFoodRecipe(
    val id: Int,
    val image: String,
    val instructions: String,
    val title: String,
    val vegan: Boolean,
    val cookingMinutes: Int,
    val healthScore: Double,
    val readyInMinutes: Int,
)
