package com.example.guidetovladivostok.mapper

interface Mapper <E, D>{
    fun getEntityToDto(entity: E): D
    fun getDtoToEntity(dto: D): E
}