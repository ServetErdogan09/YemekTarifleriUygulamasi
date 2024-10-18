package com.example.yemektarifuygulamasi.prensetation.detailrecipe

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.util.Resource
import com.example.yemektarifuygulamasi.domain.use_case.get_detail_recipe.GetDetailRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(private val getDetailRecipeUseCase: GetDetailRecipeUseCase) : ViewModel() {

    private val _state = mutableStateOf(DetailState())
    val state : State<DetailState> = _state


    private val job : Job ?= null

         fun getDteailScreen(id : Int){


            getDetailRecipeUseCase.executeGetDetailRecipe(id).onEach {
                job?.cancel()
                when(it){

                    is Resource.Loading ->{
                        _state.value = DetailState(isLoading = true)

                    }

                    is Resource.Error -> {

                        _state.value = DetailState(error = it.message?:"")

                    }
                    is Resource.Success -> {

                        _state.value = DetailState(recipes = it.data)


                    }
                }

            }.launchIn(viewModelScope)

        }
}