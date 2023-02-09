package com.example.weather_app_clone.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weather_app_clone.presentation.theme.WeatherappcloneTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.flow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherappcloneTheme {
                val mainViewModel = hiltViewModel<MainViewModel>()
                Scaffold(topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = "Weather-App-Clone",
                                color = dynamicLightColorScheme(this).onPrimary
                            )
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = dynamicLightColorScheme(
                                this
                            ).primary
                        )
                    )
                }, content = {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MyComposable(mainViewModel)
                    }
                })

            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyComposable(mainViewModel: MainViewModel) {
    val flowResponse by mainViewModel.flow.collectAsState()
    var text by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Enter city name", modifier = Modifier
                .align(Alignment.Start)
        )
        TextField(modifier = Modifier.fillMaxWidth(), value = text, onValueChange = {
            text = it
        })
        Spacer(modifier = Modifier.height(30.dp))
        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            mainViewModel.getTemperatureByCityName(text)
        }) {
            Text(text = "Get Temperature", fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text("Temperature in ${flowResponse.name} right now is: ${flowResponse.main.temp}")
    }
}


//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    WeatherappcloneTheme {
//        MyComposable()
//    }
//}