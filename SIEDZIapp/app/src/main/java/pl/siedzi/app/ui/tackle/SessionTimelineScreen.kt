package pl.siedzi.app.ui.tackle

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.Phishing
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pl.siedzi.app.ui.theme.Amber
import pl.siedzi.app.ui.theme.EmeraldGreen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionTimelineScreen(
    fisheryName: String,
    entries: List<TimelineEntryUi>,
    onBranieClick: () -> Unit,
    onChangeSetupClick: () -> Unit,
    onBack: () -> Unit
) {
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Oś Czasu · $fisheryName",
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
                .fillMaxHeight()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                entries.forEach { ui ->
                    TimelineItem(
                        time = timeFormat.format(Date(ui.entry.timestamp)),
                        label = when (ui.entry.type) {
                            "cast" -> "Zarzucenie"
                            "catch" -> "Połów"
                            else -> ui.entry.type
                        },
                        isCatch = ui.entry.type == "catch",
                        setupTags = ui.setupTags,
                        catchSpecies = ui.catchSpecies,
                        catchWeight = ui.catchWeight
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp, 16.dp),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp)
            ) {
                TimelineCtaButton(
                    text = "Branie!",
                    icon = Icons.Outlined.PhotoCamera,
                    color = Amber,
                    modifier = Modifier.weight(1f),
                    onClick = onBranieClick
                )
                TimelineCtaButton(
                    text = "Zmień zestaw",
                    icon = Icons.Outlined.Phishing,
                    color = EmeraldGreen,
                    modifier = Modifier.weight(1f),
                    onClick = onChangeSetupClick
                )
            }
        }
    }
}

@Composable
private fun TimelineItem(
    time: String,
    label: String,
    isCatch: Boolean,
    setupTags: List<String>,
    catchSpecies: String?,
    catchWeight: String?
) {
    val dotColor = if (isCatch) Amber else EmeraldGreen
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.size(5.dp))
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(
                        dotColor.copy(alpha = 0.18f),
                        CircleShape
                    )
                    .border(2.dp, dotColor.copy(alpha = 0.55f), CircleShape)
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "$time · $label",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.5f)
            )
            Spacer(modifier = Modifier.height(5.dp))
            if (isCatch && (catchSpecies != null || catchWeight != null)) {
                CatchCard(
                    species = catchSpecies ?: "",
                    weight = catchWeight ?: ""
                )
            } else {
                CastCard(tags = setupTags)
            }
        }
    }
}

@Composable
private fun CastCard(tags: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.03f), RoundedCornerShape(14.dp))
            .border(1.dp, Color.White.copy(alpha = 0.07f), RoundedCornerShape(14.dp))
            .padding(10.dp, 12.dp)
    ) {
        if (tags.isNotEmpty()) {
            Text(
                text = "Zestaw",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.55f)
            )
            Spacer(modifier = Modifier.height(7.dp))
            FlowLayout(tags = tags)
        }
    }
}

@Composable
private fun CatchCard(species: String, weight: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Amber.copy(alpha = 0.05f), RoundedCornerShape(14.dp))
            .border(1.dp, Amber.copy(alpha = 0.2f), RoundedCornerShape(14.dp))
            .padding(12.dp),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .background(Amber.copy(alpha = 0.1f), RoundedCornerShape(10.dp))
                .border(1.dp, Amber.copy(alpha = 0.25f), RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Outlined.PhotoCamera,
                contentDescription = null,
                tint = Amber
            )
        }
        Column {
            Text(
                text = species.ifBlank { "Połów" },
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
            if (weight.isNotBlank()) {
                Text(
                    text = weight,
                    style = MaterialTheme.typography.titleMedium,
                    color = Amber
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FlowLayout(tags: List<String>) {
    androidx.compose.foundation.layout.FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(6.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(6.dp)
    ) {
        tags.forEach { tag ->
            Text(
                text = tag,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.75f),
                modifier = Modifier
                    .background(EmeraldGreen.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                    .border(1.dp, EmeraldGreen.copy(alpha = 0.18f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 9.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
private fun TimelineCtaButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier,
    onClick: () -> Unit
) {
    androidx.compose.material3.Button(
        onClick = onClick,
        modifier = modifier,
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
