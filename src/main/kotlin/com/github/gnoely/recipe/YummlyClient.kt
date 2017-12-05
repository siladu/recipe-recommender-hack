package com.github.gnoely.recipe

import com.github.gnoely.model.Cuisine
import com.kaloer.yummly.Yummly
import com.kaloer.yummly.models.Recipe
import com.kaloer.yummly.models.SearchResult
import java.util.*

object YummlyClient {


    val yummly = Yummly("<your app id>", "<your app key>")


    fun searchForRecipeIncluding(query: String) : Recipe {
        val searchResult: SearchResult = yummly.search(query)
        return searchResult.matches[0]
    }

    fun searchForRecipeIncluding(query: String, ingredients: List<String>) : Optional<Recipe> {
        val searchResult: SearchResult = yummly.search(query, ingredients)
        return getRecipes(searchResult, ingredients)
    }

    fun searchForRecipeWithCuisine(query: String, ingredients: List<String>, cuisine: Cuisine): Optional<Recipe> {
        if (Cuisine.NONE.equals(cuisine)) {
            return searchForRecipeIncluding(query, ingredients)
        } else {
            val searchResult: SearchResult = yummly.searchWithCuisine(query, ingredients, cuisine.searchTerm)
            return getRecipes(searchResult, ingredients)
        }
    }

    private fun getRecipes(searchResult: SearchResult, ingredients: List<String>): Optional<Recipe> {
        val recipes = searchResult.matches
        if (recipes.isEmpty()) {
            return Optional.empty()
        }
        val singularisedIngredients = ingredients.map { i -> i.toLowerCase().trimEnd('s')}
        var partialRecipe : Recipe? = recipes.find { r -> singularisedIngredients.any({ r.name.toLowerCase().contains(it.toLowerCase()) }) }
//        val partialRecipe = matchedRecipes.get(0)

//        val partialRecipe = recipes[Math.floor(Math.random()*recipes.size).toInt()]
        if (partialRecipe == null) {
            partialRecipe = recipes.get(0)
        }
        return Optional.of(yummly.getRecipe(partialRecipe?.id))
    }
}
