package com.hussainthecoder.newsapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.hussainthecoder.newsapp.API_KEY
import com.hussainthecoder.newsapp.RetrofitClient
import com.hussainthecoder.newsapp.models.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun NewsScreen() {
    var articles by remember { mutableStateOf<List<Article>>(emptyList()) }

    LaunchedEffect(true) {
        val response = withContext(Dispatchers.IO) {
            RetrofitClient.client.apiCalling(
                country = "us",
                category = "business",
                apiKey = API_KEY
            )
        }

        if (response.isSuccessful) {
            articles = response.body()?.articles ?: emptyList()
        }
    }

    Surface(modifier = Modifier.fillMaxSize().padding(top = 4.dp)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(articles) { article ->
                NewsItem(article)
            }
        }
    }
}

@Composable
fun NewsItem(article: Article) {
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxWidth()) {
        val imagePainter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(context)
                .data(article.urlToImage)
                .crossfade(true)
                .build()
        )

        Image(
            painter = imagePainter,
            contentDescription = article.title ?: "News Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = article.title ?: "No Title",
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = article.description ?: "No Description",
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}
