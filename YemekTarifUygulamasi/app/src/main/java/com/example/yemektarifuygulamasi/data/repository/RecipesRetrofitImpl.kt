package com.example.yemektarifuygulamasi.data.repository

import com.example.yemektarifuygulamasi.data.remote.dto.DetailFoodRecipesDto
import com.example.yemektarifuygulamasi.data.remote.dto.RandomFoodRecipesDto
import com.example.yemektarifuygulamasi.data.remote.dto.SearchFoodRecipesDto
import com.example.yemektarifuygulamasi.data.remote.service.RecipesAPI
import com.example.yemektarifuygulamasi.domain.repository.RecipesRepository
import com.example.yemektarifuygulamasi.util.Constants.API_KEY
import javax.inject.Inject

class RecipesRetrofitImpl @Inject constructor(private val api : RecipesAPI) : RecipesRepository{

    override suspend fun getRandomFoodRecipes(): RandomFoodRecipesDto {

         return api.getRandomRecipes(API_KEY,20)
    }

    override suspend fun getDetailFoodRecipe(id : Int): DetailFoodRecipesDto {

        return api.getInformationRecipes(id, API_KEY)
    }

    override suspend fun getSearchFoodRecipes(searchFood : String): SearchFoodRecipesDto {

        return api.getSearchRecipes(query = searchFood , number = 20 , apiKey = API_KEY)

    }


}