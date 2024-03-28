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
            Log.i("testing", feed.toString())

        }.start()

    }
}
