package com.bombadu.geofence2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadcastReceiver : BroadcastReceiver() {


    companion object {
        const val TAG = "GeofenceReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.

        //Toast.makeText(context, "Fence Detected", Toast.LENGTH_SHORT).show()
        //Log.i(TAG, "receiver")

        val notificationUtil = NotifcationUtil(context)

        val geofencingEvent = GeofencingEvent.fromIntent(intent)

        if (geofencingEvent.hasError()) {
            Log.d(TAG, "onREceived: Error receiving geofence event...")
            return
        }

        val geofenceList = geofencingEvent.triggeringGeofences as List<Geofence>
        for (i in geofenceList) {
            Log.i(TAG, "onReceive ${i.requestId}")
        }
   //     val location = geofencingEvent.triggeringLocation

        when(geofencingEvent.geofenceTransition) {
            Geofence.GEOFENCE_TRANSITION_ENTER -> {
                notificationUtil.sendHighPriorityNotification("Enter", "", MapsActivity::class.java)
                Toast.makeText(context, "Entering Fence", Toast.LENGTH_SHORT).show()
            }

            Geofence.GEOFENCE_TRANSITION_DWELL -> {
                notificationUtil.sendHighPriorityNotification("Dwell", "", MapsActivity::class.java)
                Toast.makeText(context, "Dwell Fence", Toast.LENGTH_SHORT).show()

            }

            Geofence.GEOFENCE_TRANSITION_EXIT -> {
                notificationUtil.sendHighPriorityNotification("Exit", "", MapsActivity::class.java)
                Toast.makeText(context, "Exiting Fence", Toast.LENGTH_SHORT).show()

            }
        }


    }
}