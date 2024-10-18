package com.example.yemektarifuygulamasi.domain.repository

import com.example.yemektarifuygulamasi.data.remote.dto.DetailFoodRecipesDto
import com.example.yemektarifuygulamasi.data.remote.dto.RandomFoodRecipesDto
import com.example.yemektarifuygulamasi.data.remote.dto.SearchFoodRecipesDto

interface RecipesRepository {

     suspend fun getRandomFoodRecipes():RandomFoodRecipesDto

     suspend fun getDetailFoodRecipe(id : Int): DetailFoodRecipesDto

     suspend fun getSearchFoodRecipes(searchFood : String):SearchFoodRecipesDto

}