package com.example.yemektarifuygulamasi.data.remote.localdtb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RecipeEntity::class] , version = 2)
abstract class RecipeDatabase :RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
}