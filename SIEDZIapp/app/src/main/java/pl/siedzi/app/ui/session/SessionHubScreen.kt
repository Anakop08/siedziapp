package pl.siedzi.app.ui.session

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.siedzi.app.ui.components.TopAppBarTitle
import pl.siedzi.app.ui.theme.CyanTeal

/**
 * Hub sesji – główny ekran aktywnej sesji nad wodą.
 * Sekcje: Formularz zasadzki, Oś czasu, Zmiana zestawu.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionHubScreen(
    sessionId: String,
    fisheryName: String,
    endSessionStats: Pair<Int, Double>,
    onNavigateToTackleForm: () -> Unit,
    onNavigateToTimeline: () -> Unit,
    onNavigateToChangeSetup: () -> Unit,
    onEndSession: () -> Unit,
    onBack: () -> Unit
) {
    var showEndDialog by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { TopAppBarTitle("Sesja: $fisheryName") },
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
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onNavigateToTackleForm),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Formularz zasadzki",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Wybierz zestaw i zarzuć wędkę",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onNavigateToTimeline),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Oś czasu sesji",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Historia rzutów i połowów",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onNavigateToChangeSetup),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Zmień zestaw",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Edytuj zestaw na podstawie poprzedniego rzutu",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedButton(
                onClick = { showEndDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Stop, contentDescription = null)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Zakończ sesję")
            }
        }
    }

    if (showEndDialog) {
        EndSessionDialog(
            fisheryName = fisheryName,
            catchCount = endSessionStats.first,
            weightKg = endSessionStats.second,
            onConfirm = {
                showEndDialog = false
                onEndSession()
            },
            onDismiss = { showEndDialog = false }
        )
    }
}

@Composable
private fun EndSessionDialog(
    fisheryName: String,
    catchCount: Int,
    weightKg: Double,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Zakończyć sesję?") },
        text = {
            Column {
                Text("Sesja na łowisku $fisheryName zostanie zakończona.")
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = catchCount.toString(),
                            style = MaterialTheme.typography.titleLarge,
                            color = CyanTeal
                        )
                        Text("połowów", style = MaterialTheme.typography.labelSmall)
                    }
                    Spacer(modifier = Modifier.padding(horizontal = 24.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "%.1f".format(weightKg),
                            style = MaterialTheme.typography.titleLarge,
                            color = CyanTeal
                        )
                        Text("kg", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Tak, zakończ", color = CyanTeal)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Nie")
            }
        }
    )
}
