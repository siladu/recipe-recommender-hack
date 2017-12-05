package com.github.gnoely

import com.github.gnoely.model.Product
import com.github.gnoely.service.OrderService
import com.github.gnoely.service.ProductService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.TimeUnit
import kotlin.streams.toList


@SpringBootApplication
class Application {

//    @Bean
    fun dumpDataForRecommendations(service: OrderService, productService: ProductService): CommandLineRunner {
        val fileName = "/Users/leon.pelech/recom_dump"
        val fileExt = ".csv"

        return CommandLineRunner {
            (0..10).forEach { i ->
                val start = System.nanoTime()
                println("STARTED DUMP $i!")

                val fileNameFull = "$fileName-$i$fileExt"
                File(fileNameFull).printWriter().use { out ->
                    out.println("userId,productId,freq")
                    service.dumpUserProductStats(i*1000L, 1000)
                        .forEach { out.println("${it.userId},${it.productId},${it.count}") }
                }

                val end = System.nanoTime()
                println("FINISHED DUMP $i!: ${TimeUnit.NANOSECONDS.toMillis(end - start)}ms")
            }

        }
    }

    @Bean
    fun commandLineRunner(service: OrderService, productService: ProductService): CommandLineRunner {
        return CommandLineRunner {
            println("STARTED!")
            val start = System.nanoTime()
//            service.findAllProductsForTwitterHandle("leonpelech").forEach { println(it) }
//            productService.findAllInFoodDepartments().forEach { println(it) }
//            service.dumpUserProductStats().forEach { println(it) }
//            printExactMatch(productService)
//            printFullIngredientContainedInProduct(productService)
            val end = System.nanoTime()
            println("FINISHED!: ${TimeUnit.NANOSECONDS.toMillis(end - start)}ms")


        }
    }
}

fun printFullIngredientContainedInProduct(productService: ProductService) {
    val rawIngredients: List<String> = Files.readAllLines(Paths.get("./src/main/resources/yumly-ingredients.txt"))
    val ingredients: List<String> = rawIngredients.stream().map({it.toLowerCase()}).toList()

    var maxMatches = 0
    productService.findAllInFoodDepartments()
        .stream()
        .map({it.copy(name=it.name.toLowerCase())})
//        .limit(1)
        .forEach {
            val completeWordsInProduct = it.name.split(' ')
            val matches = ingredients.filter { igt ->
                it.name.contains(igt) && completeWordsInProduct.contains(igt)
            }
            if (matches.size > maxMatches) maxMatches = matches.size
            val match = matches.size > 0
//            val words = it.name.split(',')
//            val match = ingredients.any { words.contains(it) }
            if (match) println("${it.name} contains $matches") else println("NO MATCHES for ${it.name}")
            //                if (lines.contains(it.name)) println("${it.id},${it.name}")

        }

    val idsToUpdateWithMatches: List<Pair<String, List<String>>> = productService.findAllInFoodDepartments()
        .stream()
//        .limit(10)
        .map({ original -> original.copy(name = original.name.toLowerCase()) })
        .filter({
            val completeWordsInProduct = it.name.split(' ')
            val matches: List<String> = ingredients.filter { igt ->
                it.name.contains(igt) && completeWordsInProduct.contains(igt)
            }
            matches.size > 0
        })
        .map({
            val completeWordsInProduct = it.name.split(' ')
            val matches: List<String> = ingredients.filter { igt ->
                it.name.contains(igt) && completeWordsInProduct.contains(igt)
            }
            Pair(it.id, matches)
        })
        .toList()

    fun updateProduct(product: Product, matches: List<String>): Product {
        return product.copy(rawMatches=matches.joinToString(","), isIgtMatch=true)
    }
    val productsToUpdate = productService.productRepo.findAll(idsToUpdateWithMatches.map{it.first})
    val updatedProducts = productsToUpdate.map { prod ->
        val nullablePair: Pair<String, List<String>>? = idsToUpdateWithMatches.find { it.first.equals(prod.id) }
        if (nullablePair != null) {
            updateProduct(prod, nullablePair.second)
        } else {
            prod
        }
    }
    updatedProducts.forEach({println(it)})
    println("ABOUT TO SAVE ${updatedProducts.size}...")
    productService.productRepo.save(updatedProducts)
    println("SAVED.")
}

fun printExactMatch(productService: ProductService) {
    val rawIngredients: List<String> = Files.readAllLines(Paths.get("./src/main/resources/yumly-ingredients.txt"))
    val ingredients: List<String> = rawIngredients.stream().map({ it.toLowerCase() }).toList()

//    productService.findAllInFoodDepartments()
//        .stream()
//        .map({ original -> original.copy(name = original.name.toLowerCase()) })
//        .filter({ ingredients.contains(it.name) })
//        .forEach { println("${it.id},${it.name}") }

    val idsToUpdate = productService.findAllInFoodDepartments()
        .stream()
        .map({ original -> original.copy(name = original.name.toLowerCase()) })
        .filter({ ingredients.contains(it.name) })
        .map({it.id})
        .toList()

    val productsToUpdate = productService.productRepo.findAll(idsToUpdate)
    val updatedProducts = productsToUpdate.map { it.copy(isExactIgtMatch = true) }
//    println(productsToUpdate.count())
//    println(updatedProducts.count { it.isExactIgtMatch })
    productService.productRepo.save(updatedProducts)
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
