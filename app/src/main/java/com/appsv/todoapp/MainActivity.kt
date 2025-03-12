package com.appsv.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.appsv.todoapp.core.presentation.ui.theme.ToDoAppTheme
import com.appsv.todoapp.todo.presentation.ToDoScreen
import com.appsv.todoapp.todo.presentation.ToDoViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                Color(0xFF03045e).toArgb()
            )
        )
        setContent {
            ToDoAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("Todo App" ,
                                    color = Color.White
                                )
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = colorResource(R.color.Dark_Blue)
                            )
                        )
                    }

                ) { innerPadding ->
                    Box(
                        modifier = Modifier.fillMaxSize().padding(innerPadding)
                    ){
                        // call for function
                        val viewModel : ToDoViewModel by viewModels()
                        val state by viewModel.state.collectAsState()
                        ToDoScreen(
                            state = state ,
                            events = viewModel::onEvent
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ToDoAppTheme {
        Greeting("Android")
    }
}