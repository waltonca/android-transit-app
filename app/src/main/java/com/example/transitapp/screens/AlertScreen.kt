package com.example.transitapp.screens

import android.util.Log
import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.transitapp.MainViewModel
import com.example.transitapp.R
import com.google.transit.realtime.GtfsRealtime
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AlertScreen(mainViewModel: MainViewModel) {
    //Text("Alert Screen")
    val alerts by mainViewModel.AlertsStateFlow.collectAsState()
    val alertsList = alerts?.entityList

    Log.i("AlertScreen", "alertsList: ${alertsList.toString()}")
    if (alertsList != null) {
        LazyColumn {
            items(alertsList) { entity ->
                val alert = entity.alert
                val routeId = alert.informedEntityList.getOrNull(0)?.routeId ?: ""
                //Log.i("AlertScreen", "routeId: $routeId")

                //Text("Route ID: $routeId")
                //Spacer(modifier = Modifier.height(8.dp))

                DisplayAlerts(alert)
            }
        }
//        alertsList?.forEach { entity ->
//            if (entity.hasAlert()) {
//                val alert = entity.alert
//                val routeId = alert.informedEntityList.get(0).routeId
//                Log.i("AlertScreen", "routeId: ${routeId.toString()}")
//                Text("Route ID: ${routeId}")
//            }
//        }
    } else {
        Text(text = "No alerts available")
    }
}

@Composable
fun DisplayAlerts(alert: GtfsRealtime.Alert) {
    // ALl the alert data.
    val routeId = alert.informedEntityList.getOrNull(0)?.routeId ?: ""
    val timeStart = alert.activePeriodList.getOrNull(0)?.start ?:""
    val timeEnd = alert.activePeriodList.getOrNull(0)?.end ?:""
    val effect = alert.effect ?:""
    val description = alert.headerText.translationList.getOrNull(0)?.text ?:""

    // Log.i("AlertScreen", "time: ${timeStart.toString()}")
    Log.i("AlertScreen", "time: ${timeEnd.toString()}")
    // Format the time
    val strTimeStart = formatDate(timeStart.toString())
    val strTimeEnd = formatDate(timeEnd.toString())

    // Display
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(15.dp)
    )
    {
        Column {
            Row {
                Text("Route: ${routeId}")
                Spacer(modifier = Modifier.width(20.dp))
                Text("Effect: ${effect}")
            }
            Row {
                Text("Start: ${strTimeStart}")
            }
            Row {
                Text("Description: ${description}")
            }
        }
        Divider(modifier = Modifier.padding(vertical = 8.dp))
    }
}

private fun formatDate(timestamp: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CANADA)
    val date = Date(timestamp.toLong() * 1000)
    return dateFormat.format(date)
}