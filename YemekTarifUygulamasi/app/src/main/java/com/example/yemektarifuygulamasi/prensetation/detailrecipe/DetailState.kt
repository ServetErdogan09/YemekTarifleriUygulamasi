package com.example.yemektarifuygulamasi.prensetation.detailrecipe

import com.example.yemektarifuygulamasi.domain.model.DetailFoodRecipe

data class DetailState(
    val isLoading : Boolean = false,
    val recipes : DetailFoodRecipe?= null,
    val error : String = ""

)

