package com.example.cryptomatthew

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cryptomatthew.ui.home.HomeScreen
import com.example.cryptomatthew.ui.home.HomeViewModel
import com.example.cryptomatthew.ui.theme.CryptoMatthewTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val owner = LocalViewModelStoreOwner.current

            owner?.let {
                val viewModel: HomeViewModel = viewModel(
                    it,
                    "UserViewModel",
                    HomeViewModelFactory(LocalContext.current.applicationContext as Application)
                )
                CryptoMatthewTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                        HomeScreen(viewModel)
                    }
                }
            }

        }
    }
}

class HomeViewModelFactory(val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(application) as T
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    CryptoMatthewTheme {
//        HomeScreen(currenciesData)
//    }
//}