package com.eman.taskcalories.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eman.taskcalories.R
import com.eman.taskcalories.data.model.ApiCalories
import com.eman.taskcalories.databinding.RowRecipeBinding

class ApiCaloriesAdapter(private var calories: ArrayList<ApiCalories>) : RecyclerView.Adapter<ApiCaloriesAdapter.DataViewHolder>(), Filterable {
    var caloriesList= ArrayList<ApiCalories>()

    init {
        caloriesList = calories
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding=   DataBindingUtil.inflate<RowRecipeBinding>(
            LayoutInflater.from(parent.context),
            R.layout.row_recipe,
            parent,
            false
        )
        return DataViewHolder(binding)
    }


    override fun getItemCount(): Int = caloriesList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.binding.calories = caloriesList.get(position)
        Glide.with(holder.binding.root).load(caloriesList.get(position).image).into(holder.binding.imgCalor)
    }

    class DataViewHolder(val binding: RowRecipeBinding) : RecyclerView.ViewHolder(binding.root)

    fun addData(list: List<ApiCalories>) {
        calories.addAll(list)
    }

    override
    fun getFilter(): Filter? {
        return object : Filter() {

            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    caloriesList = calories
                } else {
                    val filteredList = ArrayList<ApiCalories>()
                    for (row in calories) {

                        if (row.name.toLowerCase().contains(charString.toLowerCase())
                        ) {
                            filteredList.add(row)
                        }
                    }
                    caloriesList = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = caloriesList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                caloriesList = results!!.values as ArrayList<ApiCalories>
                notifyDataSetChanged()
            }

        }
    }


}