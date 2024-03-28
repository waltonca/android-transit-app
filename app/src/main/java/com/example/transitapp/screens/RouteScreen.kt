package com.example.transitapp.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.transitapp.MainViewModel

@Composable
fun RouteScreen(mainViewModel: MainViewModel) {

    // Get GTFS from ViewModel, and the UI will re-compose when ViewModel changes or GTFS data is loaded
    // val GTFS by mainViewModel.GTFSStateFlow.collectAsState()


    Text("Route Screen")
}