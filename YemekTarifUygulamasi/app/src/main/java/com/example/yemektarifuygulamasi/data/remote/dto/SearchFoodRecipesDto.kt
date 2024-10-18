package com.example.yemektarifuygulamasi.data.remote.dto

data class SearchFoodRecipesDto(
    val number: Int,
    val offset: Int,
    val results: List<Result>,
    val totalResults: Int
)