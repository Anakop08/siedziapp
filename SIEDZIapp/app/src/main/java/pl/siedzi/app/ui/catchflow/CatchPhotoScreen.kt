package pl.siedzi.app.ui.catchflow

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pl.siedzi.app.ui.theme.Amber
import pl.siedzi.app.ui.theme.EmeraldGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatchPhotoScreen(
    photoUris: List<String>,
    canAddMore: Boolean,
    onPhotosSelected: (List<String>) -> Unit,
    onRemovePhoto: (Int) -> Unit,
    onNavigateToCatchCard: () -> Unit,
    onBack: () -> Unit
) {
    val pickMediaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(5)
    ) { uris ->
        val toAdd = uris.take((5 - photoUris.size).coerceAtLeast(0)).map { it.toString() }
        if (toAdd.isNotEmpty()) onPhotosSelected(toAdd)
    }

    fun launchPicker() {
        pickMediaLauncher.launch(
            androidx.activity.result.PickVisualMediaRequest(
                ActivityResultContracts.PickVisualMedia.ImageOnly
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Branie! · Zdjęcia",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
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
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Amber.copy(alpha = 0.1f))
                    .border(1.dp, Amber.copy(alpha = 0.2f))
                    .padding(9.dp, 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(Amber, RoundedCornerShape(4.dp))
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "Zdjęcia do karty połowu",
                        style = MaterialTheme.typography.labelMedium,
                        color = Amber
                    )
                }
                Text(
                    text = "${photoUris.size} / 5",
                    style = MaterialTheme.typography.labelLarge,
                    color = Amber
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(5) { index ->
                    if (index < photoUris.size) {
                        PhotoThumb(
                            uri = photoUris[index],
                            onClick = { },
                            onRemove = { onRemovePhoto(index) }
                        )
                    } else {
                        AddPhotoSlot(onClick = { launchPicker() })
                    }
                }
            }

            Text(
                text = "Wskazówka: zrób zdjęcie z ryby na wodzie i po wyjęciu — aparat GPS zapisuje lokalizację automatycznie.",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.65f),
                modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp, 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PhotoCtaButton(
                    text = "Kolejne zdjęcie",
                    icon = Icons.Outlined.PhotoCamera,
                    color = Amber,
                    modifier = Modifier.weight(1f),
                    onClick = { launchPicker() },
                    enabled = canAddMore
                )
                PhotoCtaButton(
                    text = "Karta połowu",
                    icon = Icons.Outlined.Check,
                    color = EmeraldGreen,
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateToCatchCard
                )
            }
        }
    }
}

@Composable
private fun PhotoThumb(
    uri: String,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(4f / 3f)
            .clip(RoundedCornerShape(14.dp))
            .background(Amber.copy(alpha = 0.08f))
            .border(1.dp, Amber.copy(alpha = 0.2f), RoundedCornerShape(14.dp))
    ) {
        AsyncImage(
            model = Uri.parse(uri),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun AddPhotoSlot(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(4f / 3f)
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White.copy(alpha = 0.02f))
            .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(14.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                Icons.Outlined.Add,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.55f),
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.size(5.dp))
            Text(
                text = "Dodaj zdjęcie",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.65f)
            )
        }
    }
}

@Composable
private fun PhotoCtaButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    androidx.compose.material3.Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(18.dp),
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = color
        )
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.size(8.dp))
        Text(text, style = MaterialTheme.typography.titleSmall)
    }
}
