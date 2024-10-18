package com.example.yemektarifuygulamasi.domain.use_case.get_detail_recipe

import android.util.Log
import coil.network.HttpException
import com.example.movieapp.util.Resource
import com.example.yemektarifuygulamasi.domain.model.DetailFoodRecipe
import com.example.yemektarifuygulamasi.domain.repository.RecipesRepository
import com.example.yemektarifuygulamasi.extensions.toDetailFoodRecipes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetDetailRecipeUseCase @Inject constructor(private val repository: RecipesRepository) {


   fun executeGetDetailRecipe(id : Int) : Flow<Resource<DetailFoodRecipe>> = flow{
       emit(Resource.Loading())
       try {
           val detailFoodRecipe = repository.getDetailFoodRecipe(id)
           emit(Resource.Success(detailFoodRecipe.toDetailFoodRecipes()))
       }catch (e: HttpException){
           emit(Resource.Error(message =e.localizedMessage))
       }catch (e : IOException){ // internet bağlantısı sorunu
           emit(Resource.Error(message = "no conection internet"))
       }

    }


}