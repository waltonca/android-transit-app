package com.example.transitapp.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.transitapp.MainViewModel

@Composable
fun RouteScreen(mainViewModel: MainViewModel) {
    //Text("Route Screen")
    // display a list of the buses by Route ID from GTFS.
    val GTFS by mainViewModel.GTFSStateFlow.collectAsState()
    val GTFSList = GTFS?.entityList

    Log.i("RouteScreen", "GTFSList: ${GTFSList.toString()}")


    LazyColumn {
        items(GTFSList ?: emptyList()) { entity ->
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    "Route ID: ${entity.vehicle.trip.routeId}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}