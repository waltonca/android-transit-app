package com.example.transitapp.screens


import android.util.Log
import android.widget.TextView
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
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions


//private lateinit var mapView: MapView
@Composable
fun MapScreen(mainViewModel: MainViewModel) {
    //Text("Map Screen")
    val GTFS by mainViewModel.GTFSStateFlow.collectAsState()
    val GTFSList = GTFS?.entityList

    val userLocation by mainViewModel.userLocationStateFlow.collectAsState()
    Log.i("Location", "${userLocation}")

    // AndroidView
    AndroidView(
        // update
        update = { mapView ->
            if (userLocation != null) {
                mapView.mapboxMap.flyTo(
                    CameraOptions.Builder()
                        .center(userLocation) // Center the camera at the user's location
                        .pitch(0.0)
                        .zoom(14.0)
                        .bearing(0.0)
                        .build(),
                    MapAnimationOptions.mapAnimationOptions {
                        duration(2000) // Adjust duration as needed
                    }
                )
            }

            Log.i("testing2", "GTFSList: ${GTFSList.toString()}")

            val viewAnnotationManager = mapView.viewAnnotationManager

            // display real-time bus locationsRequired: AnnotatedLayerFeature
            GTFSList?.forEach { entity ->
                if (entity.hasVehicle()) {
                    val vehicle = entity.vehicle
                    val point = Point.fromLngLat(vehicle.position.longitude.toDouble(),
                        vehicle.position.latitude.toDouble()
                    )

                    val viewAnnotation = viewAnnotationManager.addViewAnnotation(
                        R.layout.layout,
                        viewAnnotationOptions {
                            geometry(point)
                        }
                    )

                    // Get layout TextView obj，
                    val annotationTxtView  = viewAnnotation?.findViewById<TextView>(R.id.annotation)
                    Log.i("MapScreen", "TextView: $annotationTxtView ")
                    // Set TextView route_id
                    annotationTxtView?.text = vehicle.trip.routeId

                }
            }

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




