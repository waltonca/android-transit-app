package com.example.transitapp.screens


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView


private lateinit var mapView: MapView
@Composable
fun MapScreen() {
    //Text("Map Screen")

    // AndroidView
    AndroidView(factory = { context ->
        // Create a map programmatically and set the initial camera
        mapView = MapView(context)
        mapView.mapboxMap.setCamera(
            CameraOptions.Builder()
                .center(Point.fromLngLat(-63.594, 44.654))
                .pitch(0.0)
                .zoom(13.0)
                .bearing(0.0)
                .build()
        )
        // Add the map view to the activity (you can also add it to other views as a child)
        mapView.apply {  }

    })
}