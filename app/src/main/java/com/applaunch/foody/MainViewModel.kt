package com.applaunch.foody

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.applaunch.foody.data.Repository
import com.applaunch.foody.models.FoodRecipe
import com.applaunch.foody.util.NetworkResults
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application
):AndroidViewModel(application) {

    var recipesResponse:MutableLiveData<NetworkResults<FoodRecipe>> = MutableLiveData()

    fun getRecipes(queries:Map<String,String>) = viewModelScope.launch {
       getRecipesSafeCall(queries)
    }
    private suspend fun getRecipesSafeCall(queries: Map<String, String>){
        recipesResponse.value = NetworkResults.Loading()
        if (hasInternetConnection()){
            try {

                val response = repository.remote.getRecipes(queries)
                recipesResponse.value = handleFoodRecipesResponse(response)
            }catch (e:Exception){
                recipesResponse.value = NetworkResults.Error("Recipes Not Found.")
            }

        }else{
            recipesResponse.value = NetworkResults.Error("no Internet Connection")
        }

    }

    private fun handleFoodRecipesResponse(response: Response<FoodRecipe>): NetworkResults<FoodRecipe>? {

        when {
            response.message().toString().contains("timeout")->{
                return NetworkResults.Error("TimeOut")
            }
            response.code() == 402 -> {
                return  NetworkResults.Error("Api Key not working")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return  NetworkResults.Error("Recipes Not Found")
            }
            response.isSuccessful -> {
                val foodRecipe = response.body()
                return NetworkResults.Success(foodRecipe!!)
            }else -> {
                return NetworkResults.Error(response.message())
            }
        }
    }

    private fun hasInternetConnection():Boolean {
            val connectivityManager = getApplication<Application>().getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)?:return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}