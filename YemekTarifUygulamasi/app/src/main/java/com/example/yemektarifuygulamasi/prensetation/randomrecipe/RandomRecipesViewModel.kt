package com.example.yemektarifuygulamasi.prensetation.randomrecipe

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.util.Resource
import com.example.yemektarifuygulamasi.domain.use_case.get_random_recipes.GetRandomRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomRecipesViewModel @Inject constructor(private val getRandomRecipesUseCase: GetRandomRecipesUseCase) : ViewModel() {

    private val _state = mutableStateOf<RecipeState>(RecipeState())
    val state : State<RecipeState> = _state


    private var job : Job?= null // coroutine yaşam döngüsünü kontrol eder

    init {
        getRandomRecipes()
    }


    private fun getRandomRecipes(){

        job?.cancel() // daha önceden başlatılan bir film araması varsa iptal et

        viewModelScope.launch {
            getRandomRecipesUseCase.executeRandomFoodRecipes().onEach {
                when(it){
                    is Resource.Loading ->{

                        _state.value = RecipeState(isLoading = true)

                    }

                    is Resource.Error -> {

                        _state.value = RecipeState(error = it.message ?:"")
                    }

                    is Resource.Success -> {
                        _state.value = RecipeState(recipes = it.data ?: emptyList())
                    }
                }

            }.launchIn(viewModelScope) // Akışı başlat
        }
    }
}