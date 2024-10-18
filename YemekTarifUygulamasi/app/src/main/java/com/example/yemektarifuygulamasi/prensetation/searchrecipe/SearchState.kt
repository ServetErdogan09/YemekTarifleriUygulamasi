package com.example.yemektarifuygulamasi.prensetation.searchrecipe

import com.example.yemektarifuygulamasi.data.remote.dto.Result


data class SearchState(
    val isLoading : Boolean = false,
    val result : List<Result> = emptyList(),
    val error : String = ""

)