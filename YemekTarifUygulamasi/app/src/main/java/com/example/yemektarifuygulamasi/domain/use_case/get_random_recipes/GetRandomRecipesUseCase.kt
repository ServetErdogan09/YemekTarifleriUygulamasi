package com.example.yemektarifuygulamasi.domain.use_case.get_random_recipes

import android.util.Log
import coil.network.HttpException
import com.example.movieapp.util.Resource
import com.example.yemektarifuygulamasi.domain.model.RandomFoodRecipes
import com.example.yemektarifuygulamasi.domain.repository.RecipesRepository
import com.example.yemektarifuygulamasi.extensions.toRandomFoodRecipes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetRandomRecipesUseCase @Inject constructor(val repository : RecipesRepository){

    fun executeRandomFoodRecipes() :Flow<Resource<List<RandomFoodRecipes>>> = flow{
        emit(Resource.Loading())
        try {
            val randomFoodRecipes = repository.getRandomFoodRecipes()
            emit(Resource.Success(randomFoodRecipes.toRandomFoodRecipes()))

        }catch (e:HttpException){
           emit(Resource.Error(message =e.localizedMessage))

        }catch (e : IOException){ // internet bağlantısı sorunu
            emit(Resource.Error(message = "no conection internet"))

        }


    }

}