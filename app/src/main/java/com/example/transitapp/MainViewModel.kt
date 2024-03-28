package com.example.transitapp

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.transitapp.models.GTFS
import com.google.transit.realtime.GtfsRealtime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.net.URL

class MainViewModel : ViewModel() {

    private val _GTFSStateFlow = MutableStateFlow<GTFS?>(null)

    val GTFSStateFlow : StateFlow<GTFS?> = _GTFSStateFlow.asStateFlow()
    fun loadBusPositions() {
        Thread {
            val url = URL("https://gtfs.halifax.ca/realtime/Vehicle/VehiclePositions.pb")
            val feed = GtfsRealtime.FeedMessage.parseFrom(url.openStream())

            // rest of code goes here
            // Here you can examine the 'feed' object to see the GTFS data
            // Console each bus route name(route 7A) and location(long and lati)
            Log.i("testing", "feed: ${feed.toString()}")
            feed.entityList.forEach{ entity ->
                if(entity.hasVehicle()){
                    val vehicle = entity.vehicle
                    println("Bus route name: ${vehicle.trip.routeId}")
                    println("Bus location: ${vehicle.position.longitude},${vehicle.position.latitude}")
                }
            }

        }.start()

    }
}
