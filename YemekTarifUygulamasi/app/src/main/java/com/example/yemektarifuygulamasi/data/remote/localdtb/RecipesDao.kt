package com.example.yemektarifuygulamasi.data.remote.localdtb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity)

    @Query("SELECT * FROM recipe")
    fun getAllRecipes(): Flow<List<RecipeEntity>>


    @Delete
    suspend fun deleteRecipe(recipe: RecipeEntity)
}