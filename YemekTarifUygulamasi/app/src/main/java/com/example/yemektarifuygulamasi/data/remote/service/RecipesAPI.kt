package com.example.yemektarifuygulamasi.data.remote.service

import com.example.yemektarifuygulamasi.data.remote.dto.DetailFoodRecipesDto
import com.example.yemektarifuygulamasi.data.remote.dto.RandomFoodRecipesDto
import com.example.yemektarifuygulamasi.data.remote.dto.SearchFoodRecipesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipesAPI {

    @GET("recipes/complexSearch")
   suspend fun getSearchRecipes(
        @Query("query") query: String,
        @Query("maxFat") maxFat: Int = 25,
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String
   ) :SearchFoodRecipesDto

    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("apiKey") apiKey: String,
        @Query("number") number: Int
    ):RandomFoodRecipesDto


    @GET("recipes/{id}/information")
    suspend fun getInformationRecipes(
        @Path("id") id : Int,
        @Query("apiKey") apiKey : String
    ):DetailFoodRecipesDto

}