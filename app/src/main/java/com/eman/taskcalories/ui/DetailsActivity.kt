package com.eman.taskcalories.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.eman.taskcalories.R
import com.eman.taskcalories.data.model.ApiCalories
import com.eman.taskcalories.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)

        val myRecipe = intent.getSerializableExtra("myRecipe") as ApiCalories
        if(myRecipe.fats.equals("")){
            myRecipe.fats="0"
        }
        if(myRecipe.calories.equals("")){
            myRecipe.calories="0"
        }
        binding.calories = myRecipe
        Glide.with(this).load(myRecipe.image).into(binding.imgCalor)


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}