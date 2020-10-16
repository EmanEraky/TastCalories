package com.eman.taskcalories.utils

import android.content.Context
import android.content.SharedPreferences

object PreferenceHelper {

    val CALORIES_DATA = "Calories"

    fun customPreference(context: Context, name: String): SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    var SharedPreferences.calories
        get() = getString(CALORIES_DATA, "")
        set(value) {
            editMe {
                it.putString(CALORIES_DATA, value)
            }
        }

}
