package com.example.yemektarifuygulamasi.domain.use_case.get_search_recipe

import coil.network.HttpException
import com.example.movieapp.util.Resource
import com.example.yemektarifuygulamasi.data.remote.dto.SearchFoodRecipesDto
import com.example.yemektarifuygulamasi.domain.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetSearchRecipe @Inject constructor(private val repo : RecipesRepository) {

    fun executeGetSearchRecipes(search: String) : Flow<Resource<SearchFoodRecipesDto>> = flow {
        emit(Resource.Loading())
        try {
            val randomFoodRecipes = repo.getSearchFoodRecipes(search)

            emit(Resource.Success(randomFoodRecipes))
        }catch (e: HttpException){
            emit(Resource.Error(message =e.localizedMessage))
        }catch (e : IOException){ // internet bağlantısı sorunu
            emit(Resource.Error(message = "no conection internet"))
        }

    }

}