package com.github.gnoely.service

import com.github.gnoely.messagegenerator.MessageGenerator
import com.github.gnoely.model.Cuisine
import com.github.gnoely.model.CuisineConverter
import com.github.gnoely.model.Reply
import com.github.gnoely.repository.TwitterUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ReplyBuildingService {

    @Autowired lateinit var recommendationService : ProductRecommendationService

    @Autowired lateinit var twitterUserRepo: TwitterUserRepository
    @Autowired lateinit var orderService: OrderService

    fun buildReply(twitterHandle: String, ingredients: List<String>) : Reply {

        var yummlyIngredients = if (ingredients.isEmpty()) findRecommendedIngredients(twitterHandle) else ingredients
        println("recommended ingredients: $yummlyIngredients")
        val recipe = RecipeService.getFirstIncludingIngredients(yummlyIngredients)
        return MessageGenerator.generateMessage(recipe, yummlyIngredients)
    }

    fun buildReplyWithCuisine(twitterHandle: String, ingredients: List<String>, cuisineString: String) : Reply {
        var yummlyIngredients = if (ingredients.isEmpty()) findRecommendedIngredients(twitterHandle) else ingredients
        var cuisine: Cuisine = CuisineConverter.fromInput(cuisineString)
        val recipe = RecipeService.getFirstRecipeWithCuisine("", yummlyIngredients, cuisine)
        return MessageGenerator.generateMessage(recipe, yummlyIngredients)
    }

    fun findRecommendedIngredients(twitterHandle: String): List<String> {
//        val userId = twitterUserRepo.findOne(twitterHandle)
        println("RECOMMENDING FOR $twitterHandle")
        val recommendedProducts = recommendationService.recommendProducts(twitterHandle, 5)
        return recommendedProducts.filter { it.isIgtMatch }.flatMap { it.rawMatches.split(",") }
    }

}
