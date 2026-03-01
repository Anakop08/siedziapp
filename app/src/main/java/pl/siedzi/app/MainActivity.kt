package pl.siedzi.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import pl.siedzi.app.di.DatabaseSeeder
import pl.siedzi.app.navigation.SiedziNavGraph
import pl.siedzi.app.ui.theme.SiedziTheme
import javax.inject.Inject
import androidx.compose.runtime.LaunchedEffect

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var databaseSeeder: DatabaseSeeder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SiedziTheme {
                LaunchedEffect(Unit) {
                    databaseSeeder.seedIfNeeded()
                }
                Surface(modifier = Modifier.fillMaxSize()) {
                    SiedziNavGraph()
                }
            }
        }
    }
}
