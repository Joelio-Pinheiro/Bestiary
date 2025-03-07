package com.example.bestiariodd.network

import com.example.bestiariodd.model.Monster
import com.example.bestiariodd.model.MonsterDetails
import com.example.bestiariodd.model.MonsterResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("monsters")
    suspend fun getMonsters(): MonsterResponse


    @GET("monsters/{index}")
    suspend fun getMonster(@Path("index") index: String): Monster

    @GET("monsters/{index}")
    suspend fun getMonsterDetails(@Path("index") index: String): MonsterDetails
}