package com.example.yemektarifuygulamasi.data.remote.localdtb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val cookingMinutes: Int?,
    val healthScore: Int,
    val image: String,
    val vegan: Boolean,
    val instructions: String,
    val title: String,
    val isFavorite: Boolean
)
