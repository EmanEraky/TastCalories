package com.eman.taskcalories.data.model

import com.google.gson.annotations.SerializedName
import java.io.*

data class ApiCalories (
    @SerializedName("calories")
    var calories: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("image")
    val image: String = "",
    @SerializedName("headline")
    val headline: String = "",
    @SerializedName("proteins")
    val proteins: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("id")
    val id: String = "",
    @SerializedName("fats")
    var fats: String = ""): Serializable
