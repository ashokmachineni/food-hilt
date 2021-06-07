package com.applaunch.foody.data

import com.applaunch.foody.data.FoodRecipesAPI
import com.applaunch.foody.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor (
    private val foodRecipesAPI: FoodRecipesAPI
        ) {
    suspend fun getRecipes(queries:Map<String,String>):Response<FoodRecipe>{
        return foodRecipesAPI.getRecipes(queries)
    }
}