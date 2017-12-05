package com.github.gnoely.service

import com.github.gnoely.model.Cuisine
import com.github.gnoely.recipe.YummlyClient
import com.kaloer.yummly.models.Recipe
import java.util.*


object RecipeService {

    fun getFirstRecipeForQuery(query: String) : Recipe {
        return YummlyClient.searchForRecipeIncluding(query)
    }

    fun getFirstIncludingIngredients(ingredients: List<String>): Optional<Recipe> {
        return YummlyClient.searchForRecipeIncluding(ingredients.joinToString("+"), ingredients)
    }

    fun getFirstRecipeWithCuisine(query: String, ingredients: List<String>, cuisine: Cuisine): Optional<Recipe> {
        return YummlyClient.searchForRecipeWithCuisine(query, ingredients, cuisine)
    }

}
