package pl.siedzi.app.ui.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.OverlayItem

/**
 * Komponent mapy OSM z pinem. Wyświetla mapę i pozwala wybrać lokalizację przez tap.
 */
@Composable
fun OsmMapView(
    modifier: Modifier = Modifier,
    lat: Double,
    lon: Double,
    onLocationSelected: (lat: Double, lon: Double) -> Unit
) {
    val context = LocalContext.current
    val defaultMarker = remember {
        ContextCompat.getDrawable(context, android.R.drawable.ic_dialog_map)
    }
    val mapView = remember {
        MapView(context).apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            mapView.onDetach()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { mapView },
        update = { view ->
            view.overlays.removeAll { it is ItemizedIconOverlay<*> || it is MapEventsOverlay }
            val point = GeoPoint(lat, lon)
            if (lat != 0.0 || lon != 0.0) {
                view.controller.animateTo(point)
                view.controller.setZoom(14.0)
                val item = OverlayItem("pin", null, GeoPoint(lat, lon))
                defaultMarker?.let { item.setMarker(it) }
                val overlay = ItemizedIconOverlay(
                    mutableListOf(item),
                    null,
                    context
                )
                view.overlays.add(overlay)
            } else {
                view.controller.setZoom(4.0)
                view.controller.setCenter(GeoPoint(52.0, 19.0)) // Polska
            }
            val eventsOverlay = MapEventsOverlay(object : MapEventsReceiver {
                override fun singleTapConfirmedHelper(p: GeoPoint): Boolean {
                    onLocationSelected(p.latitude, p.longitude)
                    return true
                }
                override fun longPressHelper(p: GeoPoint): Boolean = false
            })
            view.overlays.add(0, eventsOverlay)
        }
    )
}
