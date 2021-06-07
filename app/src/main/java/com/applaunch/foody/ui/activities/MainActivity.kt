package com.applaunch.foody.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.applaunch.foody.R
import kotlinx.android.synthetic.main.activity_main.*

//https://api.spoonacular.com/recipes/complexSearch?type=drink&diet=vegan&
// addRecipeInformation=true&number=1&fillIngredients=true&
// apiKey=367f74632264476797464bd574e056f2
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         navController = findNavController(R.id.navHostFragment)
        val appBuildConfig = AppBarConfiguration(setOf(
            R.id.recipesFragment,
            R.id.favRecipesFragment,
            R.id.jokesFragment
        ))

        bottomNavigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController,appBuildConfig)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}