package pl.siedzi.app.ui.catchflow

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material.icons.outlined.Label
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pl.siedzi.app.data.entity.FishSpecies
import pl.siedzi.app.ui.theme.Amber
import pl.siedzi.app.ui.theme.EmeraldGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatchCardScreen(
    state: CatchCardState,
    speciesList: List<FishSpecies>,
    onSpeciesSelect: (FishSpecies) -> Unit,
    onWeightChange: (String) -> Unit,
    onLengthChange: (String) -> Unit,
    onNicknameChange: (String) -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Karta Połowu",
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
        var showSpeciesPicker by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            SectionLabel("Dane połowu")
            Box {
                CardFormField(
                    value = state.speciesName,
                    placeholder = "Gatunek",
                    filled = state.speciesName.isNotBlank(),
                    onClick = { showSpeciesPicker = true }
                )
                androidx.compose.material3.DropdownMenu(
                    expanded = showSpeciesPicker,
                    onDismissRequest = { showSpeciesPicker = false }
                ) {
                    speciesList.take(50).forEach { species ->
                        androidx.compose.material3.DropdownMenuItem(
                            text = { Text(species.name) },
                            onClick = {
                                onSpeciesSelect(species)
                                showSpeciesPicker = false
                            }
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CardFormField(
                    value = state.weightText,
                    placeholder = "Waga (kg)",
                    filled = state.weightText.isNotBlank(),
                    onClick = {},
                    onValueChange = onWeightChange,
                    modifier = Modifier.weight(1f)
                )
                CardFormField(
                    value = state.lengthText,
                    placeholder = "Długość (cm)",
                    filled = state.lengthText.isNotBlank(),
                    onClick = {},
                    onValueChange = onLengthChange,
                    modifier = Modifier.weight(1f)
                )
            }
            NicknameField(
                value = state.nickname,
                onValueChange = onNicknameChange
            )

            if (state.tackleSetupTags.isNotEmpty()) {
                SectionLabel("Zestaw")
                TackleTagsRow(tags = state.tackleSetupTags)
            }

            SectionLabel("Pobrane automatycznie")
            AutoMetaStrip(
                gpsText = state.gpsText,
                timeText = state.timeText
            )

            Spacer(modifier = Modifier.height(24.dp))

            androidx.compose.material3.Button(
                onClick = onSave,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp, 16.dp),
                shape = RoundedCornerShape(18.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = EmeraldGreen
                )
            ) {
                Text("Zapisz połów", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    "Wróci na Oś Czasu Sesji",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = Color.White.copy(alpha = 0.55f),
        modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 9.dp)
    )
}

@Composable
private fun CardFormField(
    value: String,
    placeholder: String,
    filled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onValueChange: ((String) -> Unit)? = null
) {
    val borderColor = if (filled) Amber.copy(alpha = 0.35f) else Color.White.copy(alpha = 0.1f)
    val bgColor = if (filled) Amber.copy(alpha = 0.05f) else Color.White.copy(alpha = 0.03f)

    if (onValueChange != null) {
        androidx.compose.material3.OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color.White.copy(alpha = 0.65f)) },
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .clip(RoundedCornerShape(14.dp))
                .border(1.dp, borderColor, RoundedCornerShape(14.dp))
                .background(bgColor),
            colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedContainerColor = bgColor,
                unfocusedContainerColor = bgColor,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            singleLine = true
        )
    } else {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(bgColor)
                .border(1.dp, borderColor, RoundedCornerShape(14.dp))
                .clickable(onClick = onClick)
                .padding(12.dp, 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (value.isNotBlank()) value else placeholder,
                color = if (value.isNotBlank()) Color.White.copy(alpha = 0.9f)
                else Color.White.copy(alpha = 0.65f),
                style = MaterialTheme.typography.bodyMedium
            )
            Icon(Icons.Outlined.ExpandMore, contentDescription = null, tint = Color.White.copy(alpha = 0.5f))
        }
    }
}

@Composable
private fun NicknameField(value: String, onValueChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White.copy(alpha = 0.03f))
            .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(14.dp))
            .padding(12.dp, 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(Icons.Outlined.Label, contentDescription = null, tint = Color.White.copy(alpha = 0.5f))
        androidx.compose.material3.OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    "np. Wielka Berta, Karpik Majowy",
                    color = Color.White.copy(alpha = 0.65f)
                )
            },
            modifier = Modifier.weight(1f),
            colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )
    }
}

@Composable
private fun TackleTagsRow(tags: List<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        tags.take(3).forEach { tag ->
            Text(
                text = tag,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.75f),
                modifier = Modifier
                    .background(EmeraldGreen.copy(alpha = 0.12f), RoundedCornerShape(20.dp))
                    .border(1.dp, EmeraldGreen.copy(alpha = 0.2f), RoundedCornerShape(20.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
private fun AutoMetaStrip(gpsText: String, timeText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(EmeraldGreen.copy(alpha = 0.05f))
            .border(1.dp, EmeraldGreen.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
            .padding(10.dp, 14.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (gpsText.isNotBlank()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.LocationOn, contentDescription = null, tint = EmeraldGreen, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.size(5.dp))
                Text(gpsText, style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.65f))
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Outlined.Schedule, contentDescription = null, tint = EmeraldGreen, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.size(5.dp))
            Text(timeText, style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.65f))
        }
    }
}
