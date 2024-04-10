package com.example.transitapp

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.transit.realtime.GtfsRealtime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.net.URL

class MainViewModel : ViewModel() {

    private val _GTFSStateFlow = MutableStateFlow<GtfsRealtime.FeedMessage?>(null)
    val GTFSStateFlow: StateFlow<GtfsRealtime.FeedMessage?> = _GTFSStateFlow.asStateFlow()

    private val _AlertsStateFlow = MutableStateFlow<GtfsRealtime.FeedMessage?>(null)
    val AlertsStateFlow: StateFlow<GtfsRealtime.FeedMessage?> = _AlertsStateFlow.asStateFlow()

    fun loadBusPositions() {
        Thread {
            val url = URL("https://gtfs.halifax.ca/realtime/Vehicle/VehiclePositions.pb")
            val feed = GtfsRealtime.FeedMessage.parseFrom(url.openStream())

            // rest of code goes here
            // Here you can examine the 'feed' object to see the GTFS data
            // Console each bus route name(route 7A) and location(long and lati)
            /* Sprint2 is DONE
            Log.i("testing", "feed: ${feed.toString()}")
            feed.entityList.forEach{ entity ->
                if(entity.hasVehicle()){
                    val vehicle = entity.vehicle
                    println("Bus route name: ${vehicle.trip.routeId}")
                    println("Bus location: ${vehicle.position.longitude},${vehicle.position.latitude}")
                }
            }
            */

            // Set feed value to the feed value
             _GTFSStateFlow.value = feed

        }.start()

    }
    fun loadAlerts() {
        Thread {
            val url = URL("https://gtfs.halifax.ca/realtime/Alert/Alerts.pb")
            val alerts = GtfsRealtime.FeedMessage.parseFrom(url.openStream())
            //Log.i("alerts", "feed: ${alerts.toString()}")
            // Set feed value to the feed value
            _AlertsStateFlow.value = alerts
        }.start()
    }
}
