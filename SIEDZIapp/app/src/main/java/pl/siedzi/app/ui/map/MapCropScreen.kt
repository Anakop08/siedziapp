package pl.siedzi.app.ui.map

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import androidx.compose.foundation.Canvas as ComposeCanvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Crop
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import java.io.File
import java.io.FileOutputStream
import kotlin.math.roundToInt

/**
 * Ekran kadrowania wycinka mapy topograficznej (design-system §12.3).
 * Pełnoekranowa mapa z nakładką crop, przyciemnieniem poza kadrem i CTA „Zapisz wycinek topo".
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapCropScreen(
    lat: Double,
    lon: Double,
    onSave: (topoClipUri: String) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val mapViewRef = remember { mutableStateOf<MapView?>(null) }

    // Proporcje kadru zgodne z design (§12.3: 80% szer., ~41% wys., marginesy 10%/22%/10%/37%)
    val cropWidthPercent = 0.8f
    val cropHeightPercent = 0.41f
    val cropLeftPercent = 0.1f
    val cropTopPercent = 0.22f

    fun formatCoord(value: Double, isLat: Boolean): String {
        val deg = value.toInt()
        val min = ((value - deg) * 60).toInt()
        val sec = ((value - deg - min / 60.0) * 3600)
        val dir = if (isLat) if (value >= 0) "N" else "S" else if (value >= 0) "E" else "W"
        return "${kotlin.math.abs(deg)}°${min}'${"%.1f".format(kotlin.math.abs(sec))}\"$dir"
    }
    val coordText = "${formatCoord(lat, true)} · ${formatCoord(lon, false)}"

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val boxWidth = constraints.maxWidth.toFloat()
        val boxHeight = constraints.maxHeight.toFloat()

        // Mapa pełnoekranowa (pod spodem)
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                MapView(context).apply {
                    setTileSource(TileSourceFactory.MAPNIK)
                    setMultiTouchControls(true)
                    zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
                    if (lat != 0.0 || lon != 0.0) {
                        controller.animateTo(GeoPoint(lat, lon))
                        controller.setZoom(14.0)
                    } else {
                        controller.setCenter(GeoPoint(52.0, 19.0))
                        controller.setZoom(4.0)
                    }
                    mapViewRef.value = this
                }
            }
        )

        val cropLeft = (boxWidth * cropLeftPercent).roundToInt()
        val cropTop = (boxHeight * cropTopPercent).roundToInt()
        val cropW = (boxWidth * cropWidthPercent).roundToInt()
        val cropH = (boxHeight * cropHeightPercent).roundToInt()
        val cropRect = Rect(cropLeft, cropTop, cropLeft + cropW, cropTop + cropH)

        // Nakładka przyciemnienia poza kadrem (4 prostokąty)
        ComposeCanvas(modifier = Modifier.fillMaxSize()) {
            val dark = Color.Black.copy(alpha = 0.35f)
            // Górny pasek
            drawRect(dark, topLeft = Offset.Zero, size = Size(boxWidth, cropTop.toFloat()))
            drawRect(dark, topLeft = Offset(0f, (cropTop + cropH).toFloat()), size = Size(boxWidth, boxHeight - cropTop - cropH))
            drawRect(dark, topLeft = Offset(0f, cropTop.toFloat()), size = Size(cropLeft.toFloat(), cropH.toFloat()))
            drawRect(dark, topLeft = Offset((cropLeft + cropW).toFloat(), cropTop.toFloat()), size = Size(boxWidth - cropLeft - cropW, cropH.toFloat()))
        }

        // Ramka crop (dashed) – design §12.3
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = cropLeft.dp,
                    top = cropTop.dp,
                    end = (constraints.maxWidth - cropLeft - cropW).dp,
                    bottom = (constraints.maxHeight - cropTop - cropH).dp
                )
                .border(
                    width = 2.dp,
                    color = Color.White.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(8.dp)
                )
        )

        // Top bar
        TopAppBar(
            title = { Text("Kadruj widok TOPO") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Wstecz")
                }
            },
            modifier = Modifier.align(Alignment.TopCenter),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = Color.White
            )
        )

        // GPS baner (środek u góry, pod top barem)
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 72.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(Color(0xFF22C55E), CircleShape)
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = coordText,
                color = Color.White.copy(alpha = 0.9f),
                style = MaterialTheme.typography.bodySmall
            )
        }

        // CTA „Zapisz wycinek topo" na dole
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp)
        ) {
            FilledTonalButton(
                onClick = {
                    val mv = mapViewRef.value
                    val rect = cropRect
                    if (mv != null && rect.width() > 0 && rect.height() > 0) {
                        val bitmap = captureMapRegion(mv, rect)
                        bitmap?.let {
                            val dir = File(context.filesDir, "topo_clips").apply { mkdirs() }
                            val file = File(dir, "topo_${System.currentTimeMillis()}.png")
                            FileOutputStream(file).use { out -> it.compress(Bitmap.CompressFormat.PNG, 90, out) }
                            onSave(file.absolutePath)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(Icons.Default.Crop, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.padding(4.dp))
                Text("Zapisz wycinek topo")
            }
        }
    }
}

private fun captureMapRegion(mapView: MapView, rect: Rect): Bitmap? {
    return try {
        val w = rect.width().coerceAtLeast(1)
        val h = rect.height().coerceAtLeast(1)
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.translate(-rect.left.toFloat(), -rect.top.toFloat())
        mapView.draw(canvas)
        bitmap
    } catch (e: Exception) {
        null
    }
}
