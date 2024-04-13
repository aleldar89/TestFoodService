package com.example.models_api

interface DataMapper<D> {

    fun entityToDto(): D
}