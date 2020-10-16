package com.eman.taskcalories.data.api

import com.eman.taskcalories.data.model.ApiCalories
import retrofit2.http.GET

interface ApiServices {

    @GET("recipes.json")
    suspend fun getRecipes(): List<ApiCalories>

}