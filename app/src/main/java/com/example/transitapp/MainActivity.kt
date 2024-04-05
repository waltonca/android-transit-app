package com.example.transitapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.transitapp.screens.AlertScreen
import com.example.transitapp.screens.MapScreen
import com.example.transitapp.screens.RouteScreen
import com.example.transitapp.ui.theme.TransitAppTheme

class MainActivity : ComponentActivity() {

    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewModel
        mainViewModel = MainViewModel()

        // Do something
        mainViewModel.loadBusPositions()

        setContent {
            TransitAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TransitApp()
                }
            }
        }
    }


    @Composable
    fun TransitApp(){

        //test
        //Text(text = "hello")

        // Step 3: Add scaffolding to display a bottom app bar

        // The NavHostController manages the screen navigation
        val navController: NavHostController = rememberNavController()

        // Get GTFS from ViewModel, and the UI will re-compose when ViewModel changes or GTFS data is loaded
        //val GTFS by mainViewModel.GTFSStateFlow.collectAsState()

        //var textFieldLocation by remember { mutableStateOf("") }

        Scaffold(
            bottomBar = {
                BottomAppBar(
                    //modifier = Modifier.fillMaxSize(),
                    actions = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ){
                            IconButton(onClick = { navController.navigate("map") }) {
                                Icon(
                                    painterResource(id = R.drawable.baseline_map_24),
                                    contentDescription = "Map"
                                )
                            }
                            IconButton(onClick = { navController.navigate("route") }) {
                                Icon(
                                    painterResource(id = R.drawable.baseline_add_road_24),
                                    contentDescription = "Route"
                                )
                            }
                            IconButton(onClick = { navController.navigate("alert") }) {
                                Icon(
                                    painterResource(id = R.drawable.baseline_add_alert_24),
                                    contentDescription = "Alert"
                                )
                            }
                        }
                    }
                )
            }
        ) { innerPadding ->
            // NavHost will display the correct screen
            NavHost(
                navController = navController,
                startDestination = "map",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = "map") {
                    MapScreen(mainViewModel)
                }
                composable(route = "route") {
                    RouteScreen(mainViewModel)
                }
                composable(route = "alert") {
                    AlertScreen(mainViewModel)
                }
            }
        }

    }
}

