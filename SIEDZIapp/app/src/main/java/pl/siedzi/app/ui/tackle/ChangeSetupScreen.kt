package pl.siedzi.app.ui.tackle

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material.icons.outlined.History
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pl.siedzi.app.ui.components.TopAppBarTitle
import pl.siedzi.app.ui.theme.EmeraldGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeSetupScreen(
    previousTime: String,
    previousSetupTags: List<String>,
    state: TackleFormState,
    onRodChange: (String) -> Unit,
    onReelChange: (String) -> Unit,
    onLineChange: (String) -> Unit,
    onBaitChange: (String) -> Unit,
    onHookChange: (String) -> Unit,
    onGroundbaitChange: (String) -> Unit,
    onHasBoatChange: (Boolean) -> Unit,
    onHasEchosounderChange: (Boolean) -> Unit,
    onCastAgainClick: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { TopAppBarTitle("Zmiana zestawu · $previousTime") },
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
            PreviousSetupStrip(
                previousTime = previousTime,
                tags = previousSetupTags
            )

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
                ctaText = "Zarzuć ponownie",
                onCtaClick = onCastAgainClick
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PreviousSetupStrip(
    previousTime: String,
    tags: List<String>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF60A5FA).copy(alpha = 0.06f))
            .border(1.dp, Color(0xFF60A5FA).copy(alpha = 0.15f))
            .padding(9.dp, 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            Icons.Outlined.History,
            contentDescription = null,
            tint = Color(0xFF60A5FA).copy(alpha = 0.8f)
        )
        Column {
            Text(
                text = "Poprzedni zestaw · $previousTime",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF60A5FA).copy(alpha = 0.7f)
            )
            if (tags.isNotEmpty()) {
                androidx.compose.foundation.layout.FlowRow(
                    modifier = Modifier.padding(top = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    tags.forEach { tag ->
                        Text(
                            text = tag,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White.copy(alpha = 0.65f),
                            modifier = Modifier
                                .background(Color(0xFF60A5FA).copy(alpha = 0.08f), RoundedCornerShape(7.dp))
                                .border(1.dp, Color(0xFF60A5FA).copy(alpha = 0.15f), RoundedCornerShape(7.dp))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
            }
        }
    }
}
