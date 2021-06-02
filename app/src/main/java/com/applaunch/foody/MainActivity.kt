package com.applaunch.foody

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//https://api.spoonacular.com/recipes/complexSearch?type=drink&diet=vegan&addRecipeInformation=true&number=1&fillIngredients=true&apiKey=367f74632264476797464bd574e056f2
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}