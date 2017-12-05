package com.github.gnoely.model

object CuisineConverter {

    fun fromInput(input: String) : Cuisine {
        val find = Cuisine.values().find({ it ->
            it.input == input
        })
        return find?: Cuisine.NONE
    }
}
