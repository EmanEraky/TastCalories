package com.eman.taskcalories.data.model

import com.google.gson.annotations.SerializedName

data class ApiCalories(
    @SerializedName("calories")
    var calories: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("image")
    val image: String = "",
    @SerializedName("headline")
    val headline: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("id")
    val id: String = "",
    @SerializedName("fats")
    var fats: String = ""
)
