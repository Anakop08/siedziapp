package pl.siedzi.app.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow

/**
 * Tytuł TopAppBar dla długich tekstów (np. nazwa łowiska).
 * Gdy tekst nie mieści się – przewija się (marquee) lub obcina z wielokropkiem (ellipsis).
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopAppBarTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
            .fillMaxWidth()
            .basicMarquee(
                iterations = 3,
                delayMillis = 1500
            )
    )
}
