package pl.siedzi.app.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class DashboardTile(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val gradientColors: List<Color>,
    val onClick: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToWspomnienia: () -> Unit,
    onNavigateToPlanowanie: () -> Unit,
    onNavigateToSesja: () -> Unit,
    onNavigateToGaleria: () -> Unit,
    onNavigateToUstawienia: () -> Unit,
    onNavigateToFishList: () -> Unit
) {
    val tiles = listOf(
        DashboardTile(
            id = "sesja",
            title = "Rozpocznij Sesję",
            icon = Icons.Default.PlayArrow,
            gradientColors = listOf(Color(0xFF052016), Color(0xFF073d25)),
            onClick = onNavigateToSesja
        ),
        DashboardTile(
            id = "planowanie",
            title = "Zaplanuj Wyprawę",
            icon = Icons.Default.Event,
            gradientColors = listOf(Color(0xFF1e0845), Color(0xFF2d1171)),
            onClick = onNavigateToPlanowanie
        ),
        DashboardTile(
            id = "wspomnienia",
            title = "Wspomnienia",
            icon = Icons.Default.Article,
            gradientColors = listOf(Color(0xFF1c0e3a), Color(0xFF2e1263)),
            onClick = onNavigateToWspomnienia
        ),
        DashboardTile(
            id = "galeria",
            title = "Galeria",
            icon = Icons.Default.Photo,
            gradientColors = listOf(Color(0xFF1c0e3a), Color(0xFF2e1263)),
            onClick = onNavigateToGaleria
        ),
        DashboardTile(
            id = "ustawienia",
            title = "Ustawienia",
            icon = Icons.Default.Settings,
            gradientColors = listOf(Color(0xFF16191f), Color(0xFF1f2430)),
            onClick = onNavigateToUstawienia
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "SIEDZI!",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF22d3ee)
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF101014))
                .padding(padding)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(tiles) { tile ->
                    DashboardTileCard(
                        title = tile.title,
                        icon = tile.icon,
                        gradientColors = tile.gradientColors,
                        onClick = tile.onClick
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable(onClick = onNavigateToFishList),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF0e7490).copy(alpha = 0.2f)
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Słownik ryb",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun DashboardTileCard(
    title: String,
    icon: ImageVector,
    gradientColors: List<Color>,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.linearGradient(gradientColors))
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.9f),
                    modifier = Modifier.size(28.dp)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
