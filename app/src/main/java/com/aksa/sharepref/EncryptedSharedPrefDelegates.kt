package com.aksa.sharepref

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class EncryptedSharedPrefDelegates(
    val context: Context,
    val name: String,
) : ReadWriteProperty<Any?, MyString?> {
    private val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    private val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
    private val securePreferences by lazy {
        EncryptedSharedPreferences.create(
            "secure_prefs",
            mainKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): MyString? {
        val loginRequest: String? =
            securePreferences.getString(name, "Null")
        if (loginRequest != null) {
            val gson = Gson()
            return gson.fromJson(loginRequest, MyString::class.java)
        }
        return null
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: MyString?) {
        with(securePreferences.edit()) {
            val gson: Gson = GsonBuilder().create()
            val jsonString: String = gson.toJson(value)
            putString(name, jsonString)
        }.apply()
    }
}

fun Context.encryptedSharedPreferences(name: String) = EncryptedSharedPrefDelegates(this, name)
