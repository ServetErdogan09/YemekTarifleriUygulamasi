package com.example.yemektarifuygulamasi.data.remote.localdtb

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalViewModel @Inject constructor(private val localRepository: LocalRepository) : ViewModel() {

    val recipesFlow : kotlinx.coroutines.flow.Flow<List<RecipeEntity>> = localRepository.getAllRecipe() // butun verileri alıyoruz

    private val _favoriteRecipes = mutableStateListOf<RecipeEntity>()
    val favoriteRecipes: List<RecipeEntity> get() = _favoriteRecipes

    fun isFavorite(recipeId: Int): Boolean {
        Log.e("id","$recipeId")
        return _favoriteRecipes.any {
            it.id == recipeId
        }


    }


    fun toggleFavorite(recipeEntity: RecipeEntity) {
        val isCurrentlyFavorite = _favoriteRecipes.any { it.id == recipeEntity.id }
        Log.e("isCurrentlyFavorite", "$isCurrentlyFavorite")

        viewModelScope.launch {
            try {
                if (isCurrentlyFavorite) {
                    // Veritabanından silme işlemi başarılı olursa listeden sil
                    localRepository.getDeleteRecipe(recipeEntity)
                    _favoriteRecipes.remove(recipeEntity)
                    Log.e("viewmodel", "Listed ve veritabanından silindi: ${recipeEntity.id} - ${recipeEntity.isFavorite} - ${recipeEntity.title}")
                } else {
                    // Veritabanına ekleme işlemi başarılı olursa listeye ekle
                    localRepository.insertRecipe(recipeEntity)
                    _favoriteRecipes.add(recipeEntity)
                    Log.e("viewmodel", "Listeye ve veritabanına eklendi: ${recipeEntity.id} - ${recipeEntity.isFavorite} - ${recipeEntity.title}")
                }
            } catch (e: Exception) {
                Log.e("Error", "Favori durumu güncellenemedi: ${e.message}")
            }
        }
    }

    private fun addRecipes(recipeEntity: RecipeEntity){

        viewModelScope.launch {
            try {
                localRepository.insertRecipe(recipeEntity)
                Log.e("viewmodel"," ekle ${recipeEntity.id} -  ${recipeEntity.isFavorite}  - ${recipeEntity.title}")
            }catch (e:Exception){
                println("Error adding recipe: ${e.message}")
            }

        }
    }


    fun deleteRecipe(recipeEntity: RecipeEntity){
        viewModelScope.launch {
            try {
                localRepository.getDeleteRecipe(recipeEntity)
                _favoriteRecipes.remove(recipeEntity) // listeden sil
                Log.e("viewmodel"," sil ${recipeEntity.id} -  ${recipeEntity.isFavorite} - ${recipeEntity.title}")
            }catch (e:Exception){
                println("Error delete recipe: ${e.message}")
            }
        }
    }


}