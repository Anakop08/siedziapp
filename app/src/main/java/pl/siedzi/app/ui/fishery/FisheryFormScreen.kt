package pl.siedzi.app.ui.fishery

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.siedzi.app.data.entity.Fishery
import pl.siedzi.app.ui.map.OsmMapView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FisheryFormScreen(
    fishery: Fishery?,
    onSave: (name: String, address: String, lat: Double, lon: Double, stationNote: String) -> Unit,
    onBack: () -> Unit
) {
    var name by remember(fishery) { mutableStateOf(fishery?.name ?: "") }
    var address by remember(fishery) { mutableStateOf(fishery?.address ?: "") }
    var latText by remember(fishery) { mutableStateOf(fishery?.lat?.toString() ?: "0.0") }
    var lonText by remember(fishery) { mutableStateOf(fishery?.lon?.toString() ?: "0.0") }
    var stationNote by remember(fishery) { mutableStateOf(fishery?.stationNote ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (fishery != null) "Edytuj łowisko" else "Dodaj łowisko") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Wstecz")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nazwa") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors()
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Adres") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors()
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = latText,
                onValueChange = { latText = it },
                label = { Text("Szerokość geogr.") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors()
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = lonText,
                onValueChange = { lonText = it },
                label = { Text("Długość geogr.") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors()
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = stationNote,
                onValueChange = { stationNote = it },
                label = { Text("Nr stanowiska / Notatka") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Lokalizacja na mapie",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Dotknij mapy, aby ustawić współrzędne",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OsmMapView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(152.dp),
                lat = latText.toDoubleOrNull() ?: 0.0,
                lon = lonText.toDoubleOrNull() ?: 0.0,
                onLocationSelected = { newLat, newLon ->
                    latText = "%.6f".format(newLat)
                    lonText = "%.6f".format(newLon)
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    val lat = latText.toDoubleOrNull() ?: 0.0
                    val lon = lonText.toDoubleOrNull() ?: 0.0
                    if (name.isNotBlank()) {
                        onSave(name.trim(), address.trim(), lat, lon, stationNote.trim())
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank()
            ) {
                Text(if (fishery != null) "Zapisz zmiany" else "Dodaj łowisko")
            }
        }
    }
}
