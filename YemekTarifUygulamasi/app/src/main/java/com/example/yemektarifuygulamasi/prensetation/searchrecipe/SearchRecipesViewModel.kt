package com.example.yemektarifuygulamasi.prensetation.searchrecipe

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.util.Resource
import com.example.yemektarifuygulamasi.domain.use_case.get_search_recipe.GetSearchRecipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchRecipesViewModel @Inject constructor(private val getSearchRecipe: GetSearchRecipe) : ViewModel() {

    private val _state = mutableStateOf<SearchState>(SearchState())
    val state : State<SearchState> = _state

    private var job : Job?= null // coroutine yaşam döngüsünü kontrol eder


     fun getSearchRecipes(search : String){

        job?.cancel()

        getSearchRecipe.executeGetSearchRecipes(search).onEach {

            when(it){
                is Resource.Error -> {
                   _state.value = SearchState(error = it.message ?:"")
                }
                is Resource.Loading -> {
                    _state.value = SearchState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = SearchState(result = it.data!!.results)
                }
            }


        }.launchIn(viewModelScope)




    }
}