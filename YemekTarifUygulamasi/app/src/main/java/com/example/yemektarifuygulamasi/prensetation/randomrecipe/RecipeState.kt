package com.example.yemektarifuygulamasi.prensetation.randomrecipe

import com.example.yemektarifuygulamasi.domain.model.RandomFoodRecipes

data class RecipeState(
    val isLoading : Boolean = false,
    val recipes : List<RandomFoodRecipes> = emptyList(),
    val error : String = ""

)
