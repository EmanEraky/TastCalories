package com.eman.taskcalories.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.eman.taskcalories.R
import com.eman.taskcalories.data.api.RetrofitBuilder
import com.eman.taskcalories.data.listeners.Listener_Recipe
import com.eman.taskcalories.data.model.ApiCalories
import com.eman.taskcalories.databinding.ActivityMainBinding
import com.eman.taskcalories.utils.NetworkConnection.isConnectedToInternet
import com.eman.taskcalories.utils.PreferenceHelper.calories
import com.eman.taskcalories.utils.PreferenceHelper.customPreference
import com.eman.taskcalories.utils.Status
import com.eman.taskcalories.utils.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.Comparator


class MainActivity : AppCompatActivity(), Listener_Recipe {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ApiCaloriesAdapter
    val CUSTOM_PREF_NAME = "Calories_data"
    val CALORIES = "Calories"
    val FAT = "Fat"

    private val dialog by lazy {
        BottomSheetDialog(this, R.style.BottomSheetDialog)
    }
    private val prefs by lazy {
        customPreference(this, CUSTOM_PREF_NAME)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupUI()

        if (isConnectedToInternet(this)) {
            setUpViewModel()
            setObserveCalories()
        } else {
            val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
            startActivity(intent)
        }

        binding.edtSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    adapter.getFilter()!!.filter(binding.edtSearch.text.toString())
                    return true
                }
                return false
            }
        })

        binding.filter.setOnClickListener {
            if (!dialog.isShowing) {
                DialogSheetSearch()
            }
        }
    }

    ///Dialog  Sort
    fun DialogSheetSearch() {
        dialog.setContentView(R.layout.row_sheet)
        val radio1 = dialog.findViewById<RadioButton>(R.id.btn_radio)
        val radio2 = dialog.findViewById<RadioButton>(R.id.btn_radio2)

        if (!prefs.calories.toString().equals("")) {
            if (getShared()) {
                radio1!!.isChecked = true
            } else {
                radio2!!.isChecked = true
            }
        }
        radio1!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                prefs.calories = CALORIES
                sortRecipeCalories(adapter.caloriesList)
                adapter.notifyDataSetChanged()
            }
        }
        radio2!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                prefs.calories = FAT
                sortRecipeFat(adapter.caloriesList)
                adapter.notifyDataSetChanged()
            }
        }
        dialog.show()
    }

    fun sortRecipeCalories(calories: List<ApiCalories>) {
        Collections.sort(calories, Comparator<ApiCalories?> { lhs, rhs ->
            if (lhs!!.calories.split(" ")[0].equals("")) {
                lhs.calories = "0"
            }
            if (rhs!!.calories.split(" ")[0].equals("")) {
                rhs.calories = "0"
            }
            if (lhs.calories.split(" ")[0].toInt() > rhs.calories.split(" ")[0].toInt()) {
                1
            } else {
                -1
            }
        })
    }

    fun sortRecipeFat(calories: List<ApiCalories>) {
        Collections.sort(calories, Comparator<ApiCalories?> { lhs, rhs ->
            if (lhs!!.fats.split(" ")[0].equals("")) {
                lhs.fats = "0"
            }
            if (rhs!!.fats.split(" ")[0].equals("")) {
                rhs.fats = "0"
            }
            if (lhs.fats.split(" ")[0].toInt() > rhs.fats.split(" ")[0].toInt()) {
                1
            } else {
                -1
            }
        })
    }

    private fun setupUI() {
        adapter = ApiCaloriesAdapter(arrayListOf(), this)
        recyclerView.adapter = adapter
    }

    private fun getShared(): Boolean {
        return prefs.calories.toString().equals(CALORIES)
    }

    fun setObserveCalories() {
        viewModel.getCalories().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { calories -> renderList(calories) }

                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }

            }
        })
    }

    private fun renderList(calories: List<ApiCalories>) {
        if (getShared()) {
            sortRecipeCalories(calories)
        } else {
            sortRecipeFat(calories)
        }
        adapter.addData(calories)
        adapter.notifyDataSetChanged()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(RetrofitBuilder.apiService)
        ).get(MainViewModel::class.java)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun openDetailRecipe(api: ApiCalories) {
        val intent_detail = Intent(this, DetailsActivity::class.java)
        intent_detail.putExtra("myRecipe", api)
        startActivity(intent_detail)
    }

}