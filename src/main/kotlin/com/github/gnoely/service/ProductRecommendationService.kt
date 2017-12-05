package com.github.gnoely.service

import com.github.gnoely.model.Product
import com.github.gnoely.repository.ProductRepository
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender
import org.apache.mahout.cf.taste.similarity.UserSimilarity
import org.springframework.stereotype.Service
import java.io.File

object Recommendation {
    val userMap = mutableMapOf<String, Long>()
}

@Service
class ProductRecommendationService(val productRepo: ProductRepository) {

    private val recommender: UserBasedRecommender

    init {
        val userProductHistoryUrl = this.javaClass.getResource("/rec_training.csv")
        val userProductHistoryFile = userProductHistoryUrl?.let { File(it.toURI()) }

        val model = FileDataModel(userProductHistoryFile)

        val similarity: UserSimilarity = PearsonCorrelationSimilarity(model)

        val neighborhood = ThresholdUserNeighborhood(1.0, similarity, model)

        recommender = GenericUserBasedRecommender(model, neighborhood, similarity)
    }


    fun recommendProducts(handle: String, num: Int): Set<Product> {

        println(Recommendation.userMap)
        val entry = if (Recommendation.userMap.containsKey(handle)) {
            Recommendation.userMap.getValue(handle)
        } else {
            null
        }

        var choosenId = 1L
        if (entry == null) {
            println("PUT")
            choosenId = chooseId()
            println("CHOOSEN ID $choosenId")
            Recommendation.userMap.put(handle, choosenId)
        } else {
            choosenId = entry
        }
        println("USER: $handle -> $choosenId")
        val productIds = recommender.recommend(choosenId, num).map { it.itemID.toString() }
        return productRepo.findAll(productIds).toHashSet()
    }

    fun chooseId(): Long {
        val itr = recommender.dataModel.userIDs
        while (itr.hasNext()) {
            val id = itr.next()
            println(id)
            if (!Recommendation.userMap.containsValue(id)) {
                return id
            }
        }
        return 1L
    }
}
