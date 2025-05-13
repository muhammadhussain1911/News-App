package com.hussainthecoder.newsapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.hussainthecoder.newsapp.ui.screens.NewsScreen
import com.hussainthecoder.newsapp.ui.theme.NewsAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                NewsScreen()
                lifecycleScope.launch(Dispatchers.IO) {

                    Log.d("API", RetrofitClient.client.apiCalling(
                        // country = "us",
                        category = "business",
                        apiKey = API_KEY
                    ).toString())

                }
            }
        }
    }
}
