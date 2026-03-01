package pl.siedzi.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pl.siedzi.app.ui.theme.EmeraldGreen

/**
 * Chmura tagów gatunków ryb – wyświetla gatunki z opcjonalnym avatarem i liczbą połowów.
 * Design: fish-tag (design-system §7.16) – tło rgba(52,211,153,.1), border rgba(52,211,153,.18).
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SpeciesTagCloud(
    speciesItems: List<SpeciesTagItem>,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        speciesItems.forEach { item ->
            SpeciesTag(
                modifier = Modifier,
                name = item.name,
                count = item.count,
                avatarLetter = item.name.firstOrNull()?.uppercaseChar()?.toString() ?: "?"
            )
        }
    }
}


data class SpeciesTagItem(
    val speciesId: String,
    val name: String,
    val count: Int = 1
)

@Composable
private fun SpeciesTag(
    name: String,
    count: Int,
    avatarLetter: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(EmeraldGreen.copy(alpha = 0.1f))
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(EmeraldGreen.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = avatarLetter,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = EmeraldGreen
            )
        }
        Text(
            text = if (count > 1) "$name × $count" else name,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.75f)
        )
    }
}
