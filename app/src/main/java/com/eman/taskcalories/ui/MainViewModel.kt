package com.eman.taskcalories.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eman.taskcalories.data.api.ApiServices
import com.eman.taskcalories.data.model.ApiCalories
import com.eman.taskcalories.utils.Resource
import kotlinx.coroutines.launch

class MainViewModel(var api: ApiServices) : ViewModel() {
    private val calories = MutableLiveData<Resource<List<ApiCalories>>>()

    init {
        fetchCalories()
    }


    fun fetchCalories() {
        viewModelScope.launch {
            try {
                val result = api.getRecipes()
                calories.postValue(Resource.success(result))
            } catch (e: Exception) {
                calories.postValue(Resource.error(e.message!!,null))
            }
        }
    }
    fun getCalories():MutableLiveData<Resource<List<ApiCalories>>>{
        return calories
    }
}