package com.timeflying.data

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.timeflying.utils.Constants

data class LocationData(
    var latitude: Double = Constants.DEFAULT_LATITUDE,
    var longitude: Double = Constants.DEFAULT_LONGITUDE
)

class LocationManagerWrapper(private val context: Context) : LocationListener {
    private val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private var _locationData = LocationData()
    val locationData: LocationData get() = _locationData
    private var hasValidLocation = false

    fun requestLocationUpdates(onPermissionDenied: () -> Unit = {}) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            onPermissionDenied()
            return
        }

        if (!hasValidLocation) {
            try {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    Constants.LOCATION_UPDATE_INTERVAL,
                    Constants.LOCATION_UPDATE_DISTANCE,
                    this
                )
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
    }

    private fun stopLocationUpdates() {
        try {
            locationManager.removeUpdates(this)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    override fun onLocationChanged(location: Location) {
        _locationData = LocationData(
            latitude = location.latitude,
            longitude = location.longitude
        )
        if (!hasValidLocation) {
            hasValidLocation = true
            stopLocationUpdates()
        }
    }

    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) {}
}