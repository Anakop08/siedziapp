package pl.siedzi.app.service

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Udostępnia metadane do rejestracji połowu: GPS, czas, pogoda, solunar, zestaw.
 * GPS: FusedLocationProvider (last lub fresh fix).
 * Pogoda i solunar: TODO – Faza 2.3 (na razie null).
 */
class CatchMetadataService(
    private val context: Context,
    private val fusedClient: FusedLocationProviderClient
) {

    fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Pobiera metadane: GPS (jeśli uprawnienia), czas aktualny.
     * tackleSetupId przekazywane z zewnątrz (z TimelineEntry).
     * Pogoda i solunar na razie null (Faza 2.3).
     */
    suspend fun getMetadata(tackleSetupId: String): CatchMetadata {
        val timestamp = System.currentTimeMillis()
        val (lat, lon) = if (hasLocationPermission()) {
            fetchLocation()
        } else {
            null to null
        }

        return CatchMetadata(
            lat = lat,
            lon = lon,
            timestamp = timestamp,
            weatherSnapshot = null, // TODO: Faza 2.3
            solunarSnapshot = null,  // TODO: Faza 2.3
            tackleSetupId = tackleSetupId
        )
    }

    private suspend fun fetchLocation(): Pair<Double?, Double?> = suspendCancellableCoroutine { cont ->
        fusedClient.lastLocation
            .addOnSuccessListener { loc ->
                if (loc != null) {
                    cont.resume(loc.latitude to loc.longitude)
                } else {
                    fusedClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, CancellationTokenSource().token)
                        .addOnSuccessListener { fresh ->
                            if (fresh != null) {
                                cont.resume(fresh.latitude to fresh.longitude)
                            } else {
                                cont.resume(null to null)
                            }
                        }
                        .addOnFailureListener { cont.resume(null to null) }
                }
            }
            .addOnFailureListener { cont.resume(null to null) }
    }
}
