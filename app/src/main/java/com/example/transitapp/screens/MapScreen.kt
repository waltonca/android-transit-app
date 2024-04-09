package com.example.transitapp.screens


import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.viewinterop.AndroidView
import com.example.transitapp.MainViewModel
import com.example.transitapp.R
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions


//private lateinit var mapView: MapView
@Composable
fun MapScreen(mainViewModel: MainViewModel) {
    //Text("Map Screen")
    val GTFS by mainViewModel.GTFSStateFlow.collectAsState()
    val GTFSList = GTFS?.entityList

    // AndroidView
    AndroidView(
        // update
        update = { mapView ->
            Log.i("testing2", "GTFSList: ${GTFSList.toString()}")
            // display sample code first
            val viewAnnotationManager = mapView.viewAnnotationManager
            viewAnnotationManager.addViewAnnotation(
                R.layout.layout,
                viewAnnotationOptions {
                    geometry(Point.fromLngLat(-63.60,44.65))
                }
            )


            // update GTFS data in the Map
        },
        factory = { context ->
        // Create a map programmatically and set the initial camera
        val mapView = MapView(context)
        mapView.mapboxMap.setCamera(
            CameraOptions.Builder()
                .center(Point.fromLngLat(-63.594, 44.654))
                .pitch(0.0)
                .zoom(13.0)
                .bearing(0.0)
                .build()
        )
        // Add code to display buses, my IDE is different, move this part into update

        // Add the map view to the activity (you can also add it to other views as a child)
        mapView.apply { }

    })
}




