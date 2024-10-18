package com.example.yemektarifuygulamasi.extensions

import com.example.yemektarifuygulamasi.data.remote.dto.DetailFoodRecipesDto
import com.example.yemektarifuygulamasi.data.remote.dto.RandomFoodRecipesDto
import com.example.yemektarifuygulamasi.data.remote.localdtb.RecipeEntity
import com.example.yemektarifuygulamasi.domain.model.DetailFoodRecipe
import com.example.yemektarifuygulamasi.domain.model.RandomFoodRecipes

fun RandomFoodRecipesDto.toRandomFoodRecipes() :List<RandomFoodRecipes> {
    return recipes.map {
         RandomFoodRecipes(id = it.id , cookingMinutes = it.cookingMinutes?:0 , image = it.image?:"" , healthScore = it.healthScore , vegan = it.vegan , instructions = it.instructions, title = it.title)
    }

}


fun DetailFoodRecipesDto.toDetailFoodRecipes():DetailFoodRecipe{
    return DetailFoodRecipe(id, image, instructions, title, vegan, cookingMinutes?:0, healthScore, readyInMinutes)
}


fun RandomFoodRecipes.toRecipesEntity(isFavorite : Boolean = false) : RecipeEntity{
    return RecipeEntity(id, cookingMinutes, healthScore, image, vegan, instructions, title , isFavorite = isFavorite)
}



fun DetailFoodRecipe.toRecipesEntity(isFavorite : Boolean = false) : RecipeEntity{
    return RecipeEntity(id, cookingMinutes, healthScore = healthScore.toInt(), image, vegan, instructions, title , isFavorite = isFavorite)
}