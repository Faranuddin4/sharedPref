package com.aksa.sharepref

import android.content.Context
import androidx.activity.ComponentActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

data class MyString(val name: String = "")
class SharedPrefDelegates(
    val context: Context,
    val name: String,
) : ReadWriteProperty<Any?, MyString?> {
    private val sharedPreferences by lazy {
        context.getSharedPreferences("my_pref", ComponentActivity.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): MyString? {
        val loginRequest: String? =
            sharedPreferences.getString(name, "Null")
        if (loginRequest != null) {
            val gson = Gson()
            return gson.fromJson(loginRequest, MyString::class.java)
        }
        return null
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: MyString?) {
        val gson: Gson = GsonBuilder().create()
        val jsonString: String = gson.toJson(value)
        sharedPreferences.edit().putString(name, jsonString).apply()
    }
}

fun Context.sharedPreferences(name: String): SharedPrefDelegates = SharedPrefDelegates(this, name)
