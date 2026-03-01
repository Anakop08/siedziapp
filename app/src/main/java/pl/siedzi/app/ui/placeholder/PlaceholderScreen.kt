package pl.siedzi.app.ui.placeholder

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceholderScreen(
    title: String,
    onBack: () -> Unit,
    onDisclaimerClick: (() -> Unit)? = null,
    onFisheriesClick: (() -> Unit)? = null
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Wstecz")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            if (title == "Ustawienia") {
                onDisclaimerClick?.let { onClick ->
                    TextButton(onClick = onClick) {
                        Text("Informacje prawne")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
                onFisheriesClick?.let { onClick ->
                    TextButton(onClick = onClick) {
                        Text("Zarządzanie łowiskami")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            Text(
                text = "W budowie",
                style = androidx.compose.material3.MaterialTheme.typography.titleLarge
            )
        }
    }
}
