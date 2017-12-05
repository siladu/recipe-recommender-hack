package com.github.gnoely.model

enum class Cuisine(val input: String, val searchTerm: String?) {

    AMERICAN("American", "cuisine^cuisine-american"),
    KID_FRIENDLY("Kid Friendly", "cuisine^cuisine-kid-friendly"),
    ITALIAN("Italian", "cuisine^cuisine-italian"),
    ASIAN("Asian", "cuisine^cuisine-asian"),
    MEXICAN("Mexican", "cuisine^cuisine-mexican"),
    SOUTHERN_SOUL("Southern & Soul Food", "cuisine^cuisine-southern"),
    FRENCH("French", "cuisine^cuisine-french"),
    SOUTHWESTERN("Southwestern", "cuisine^cuisine-southwestern"),
    BARBECUE("Barbecue", "cuisine^cuisine-barbecue-bbq"),
    INDIAN("Indian", "cuisine^cuisine-indian"),
    CHINESE("Chinese", "cuisine^cuisine-chinese"),
    CAJUN_CREOLE("Cajun & Creole", "cuisine^cuisine-cajun"),
    ENGLISH("English", "cuisine^cuisine-english"),
    MEDITERRANEAN("Mediterranean", "cuisine^cuisine-mediterranean"),
    GREEK("Greek", "cuisine^cuisine-greek"),
    SPANISH("Spanish", "cuisine^cuisine-spanish"),
    GERMAN("German", "cuisine^cuisine-german"),
    THAI("Thai", "cuisine^cuisine-thai"),
    MOROCCAN("Moroccan", "cuisine^cuisine-moroccan"),
    IRISH("Irish", "cuisine^cuisine-irish"),
    JAPANESE("Japanese", "cuisine^cuisine-japanese"),
    CUBAN("Cuban", "cuisine^cuisine-cuban"),
    HAWAIIAN("Hawaiian", "cuisine^cuisine-hawaiian"),
    SWEDISH("Swedish", "cuisine^cuisine-swedish"),
    HUNGARIAN("Hungarian", "cuisine^cuisine-hungarian"),
    PORTUGUESE("Portuguese", "cuisine^cuisine-portuguese"),
    NONE("None", null);

}

fun main(args: Array<String>) {
}
