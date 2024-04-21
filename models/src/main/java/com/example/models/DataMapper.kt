package com.example.models

interface DataMapper<D> {

    fun entityToDto(): D
}