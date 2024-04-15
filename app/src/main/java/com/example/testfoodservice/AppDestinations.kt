package com.example.testfoodservice

interface AppDestination {
    val route: String
}

object Catalog : AppDestination {
    override val route = "catalog"
}

object Cart : AppDestination {
    override val route = "cart"
}