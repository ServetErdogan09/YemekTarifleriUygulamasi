package com.example.yemektarifuygulamasi.data.remote.localdtb

import javax.inject.Inject

class LocalRepository @Inject constructor(private val recipesDao : RecipesDao) {


    fun getAllRecipe():kotlinx.coroutines.flow.Flow<List<RecipeEntity>>{
        return recipesDao.getAllRecipes()
    }

    suspend fun insertRecipe(recipeEntity: RecipeEntity){
        try {
            return recipesDao.insertRecipe(recipeEntity)
        }catch (e:Exception){
            println("Error adding recipe: ${e.message}")
        }

    }

    suspend fun getDeleteRecipe(recipeEntity: RecipeEntity){
       recipesDao.deleteRecipe(recipeEntity)
    }

}