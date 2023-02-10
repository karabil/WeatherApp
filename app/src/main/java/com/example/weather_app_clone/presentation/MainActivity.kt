package com.example.weather_app_clone.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import com.example.weather_app_clone.data.remote.ApiImpl
import com.example.weather_app_clone.data.repository.MyRepositoryImpl
import com.example.weather_app_clone.presentation.theme.WeatherappcloneTheme
import dagger.hilt.android.AndroidEntryPoint

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
                        }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = dynamicLightColorScheme(
                                this
                            ).primary
                        )
                    )
                }, //topBar
                    content = {
                        // A surface container using the 'background' color from the theme
                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            ContentComp(mainViewModel)
                        }
                    } //content
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentComp(mainViewModel: MainViewModel) {
    val flowResponse by mainViewModel.responseFlow.collectAsState()
    val flowText by mainViewModel.textFlow.collectAsState()
    val flowProgress by mainViewModel.progressFlow.collectAsState()
    val flowError by mainViewModel.errorFlow.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Enter city name",
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 10.dp)

        )
        TextField(
            textStyle = TextStyle.Default.copy(fontSize = 20.sp),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth(),
            value = flowText,
            onValueChange = {
                mainViewModel.updateTextFlow(it)
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
        Spacer(
            modifier = Modifier.height(40.dp)
        )
        Button(shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth(), onClick = {
            if (flowText.isBlank()) {
                Toast.makeText(context, "Please enter a city", Toast.LENGTH_SHORT).show()
            } else {
                mainViewModel.getTemperatureByCityName(flowText)
            }
        }) {
            Text(
                text = "Get Temperature", fontSize = 23.sp
            )
        }
        Spacer(
            modifier = Modifier.height(30.dp)
        )

        if (flowResponse.main != null) {
            Text(
                fontSize = 20.sp,
                text = "Current temperature in ${flowResponse.name}:"
            )
            Text(
                fontSize = 20.sp,
                text = "${flowResponse.main?.temp}°C",
                fontWeight = FontWeight.Bold
            )
        }
        if (flowProgress) {
            CircularProgressIndicator()
        }
        if (flowError.isNotEmpty()) {
            Toast.makeText(context, flowError, Toast.LENGTH_SHORT).show()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherappcloneTheme {
        val api = ApiImpl()
        val repository = dagger.Lazy { MyRepositoryImpl(api) }
        val savedStateHandle = SavedStateHandle()
        val viewModel = MainViewModel(repository, savedStateHandle)
        ContentComp(viewModel)
    }
}