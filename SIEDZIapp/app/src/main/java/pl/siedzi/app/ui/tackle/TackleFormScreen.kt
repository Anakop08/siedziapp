package pl.siedzi.app.ui.tackle

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pl.siedzi.app.ui.components.TopAppBarTitle
import pl.siedzi.app.ui.theme.EmeraldGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TackleFormScreen(
    fisheryName: String,
    state: TackleFormState,
    onRodChange: (String) -> Unit,
    onReelChange: (String) -> Unit,
    onLineChange: (String) -> Unit,
    onBaitChange: (String) -> Unit,
    onHookChange: (String) -> Unit,
    onGroundbaitChange: (String) -> Unit,
    onHasBoatChange: (Boolean) -> Unit,
    onHasEchosounderChange: (Boolean) -> Unit,
    onCastClick: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { TopAppBarTitle("Zasadzka · $fisheryName") },
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
                .verticalScroll(rememberScrollState())
        ) {
            TackleFormContent(
                state = state,
                onRodChange = onRodChange,
                onReelChange = onReelChange,
                onLineChange = onLineChange,
                onBaitChange = onBaitChange,
                onHookChange = onHookChange,
                onGroundbaitChange = onGroundbaitChange,
                onHasBoatChange = onHasBoatChange,
                onHasEchosounderChange = onHasEchosounderChange,
                ctaText = "Zarzuć wędkę",
                onCtaClick = onCastClick
            )
        }
    }
}

@Composable
internal fun TackleFormContent(
    state: TackleFormState,
    onRodChange: (String) -> Unit,
    onReelChange: (String) -> Unit,
    onLineChange: (String) -> Unit,
    onBaitChange: (String) -> Unit,
    onHookChange: (String) -> Unit,
    onGroundbaitChange: (String) -> Unit,
    onHasBoatChange: (Boolean) -> Unit,
    onHasEchosounderChange: (Boolean) -> Unit,
    ctaText: String,
    onCtaClick: () -> Unit
) {
    FormSection(title = "Wędka · Przynęta · Zanęta") {
                FormField(
                    value = state.rodDisplay,
                    placeholder = "Wędka",
                    filled = state.rodDisplay.isNotBlank(),
                    onValueChange = onRodChange
                )
                FormField(
                    value = state.baitDisplay,
                    placeholder = "Przynęta",
                    filled = state.baitDisplay.isNotBlank(),
                    onValueChange = onBaitChange
                )
                FormField(
                    value = state.groundbaitDisplay,
                    placeholder = "Zanęta",
                    filled = state.groundbaitDisplay.isNotBlank(),
                    onValueChange = onGroundbaitChange
                )
            }

            FormSection(title = "Kołowrotek · Żyłka · Osprzęt") {
                FormField(
                    value = state.reelDisplay,
                    placeholder = "Kołowrotek",
                    filled = state.reelDisplay.isNotBlank(),
                    onValueChange = onReelChange
                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FormField(
                        value = state.lineDisplay,
                        placeholder = "Żyłka",
                        filled = state.lineDisplay.isNotBlank(),
                        onValueChange = onLineChange,
                        modifier = Modifier.weight(1f)
                    )
                    FormField(
                        value = state.hookDisplay,
                        placeholder = "Haczyk",
                        filled = state.hookDisplay.isNotBlank(),
                        onValueChange = onHookChange,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            FormSection(title = "Dodatki") {
                ToggleRow(
                    label = "Łódka zanętowa",
                    checked = state.hasBoat,
                    onCheckedChange = onHasBoatChange
                )
                ToggleRow(
                    label = "Echosonda",
                    checked = state.hasEchosounder,
                    onCheckedChange = onHasEchosounderChange
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            CastCtaButton(text = ctaText, onClick = onCtaClick)
}

@Composable
private fun FormSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = Color.White.copy(alpha = 0.55f),
            modifier = Modifier.padding(bottom = 9.dp)
        )
        content()
    }
}

@Composable
private fun FormField(
    value: String,
    placeholder: String,
    filled: Boolean,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = when {
        filled -> EmeraldGreen.copy(alpha = 0.35f)
        else -> Color.White.copy(alpha = 0.1f)
    }
    val bgColor = when {
        filled -> EmeraldGreen.copy(alpha = 0.05f)
        else -> Color.White.copy(alpha = 0.03f)
    }

    androidx.compose.material3.OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = Color.White.copy(alpha = 0.65f)) },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
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
        singleLine = true,
        trailingIcon = {
            Icon(
                Icons.Outlined.ExpandMore,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.5f)
            )
        }
    )
}

@Composable
private fun ToggleRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(
                if (checked) EmeraldGreen.copy(alpha = 0.08f)
                else Color.White.copy(alpha = 0.03f)
            )
            .border(
                1.dp,
                if (checked) EmeraldGreen.copy(alpha = 0.35f) else Color.White.copy(alpha = 0.1f),
                RoundedCornerShape(14.dp)
            )
            .padding(12.dp, 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.75f)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = EmeraldGreen.copy(alpha = 0.45f)
            )
        )
    }
}

@Composable
private fun CastCtaButton(text: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp, 16.dp)
    ) {
        androidx.compose.material3.Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = EmeraldGreen
            )
        ) {
            Text(text, style = MaterialTheme.typography.titleMedium)
        }
    }
}
