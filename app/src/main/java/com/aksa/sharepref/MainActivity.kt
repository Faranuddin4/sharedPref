package com.aksa.sharepref

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.aksa.sharepref.ui.theme.SharePrefTheme

class MainActivity : ComponentActivity() {
    private var token by sharedPreferences("Token")
    private var encryptedSharedPrefDelegates by encryptedSharedPreferences("Token")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        token = MyString(name = "uddin")
        encryptedSharedPrefDelegates = MyString(name = "Faran uddin")
        Log.e("hi", token.toString())
        Log.e("hi", encryptedSharedPrefDelegates.toString())
        setContent {
            SharePrefTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SharePrefTheme {
        Greeting("Android")
    }
}
