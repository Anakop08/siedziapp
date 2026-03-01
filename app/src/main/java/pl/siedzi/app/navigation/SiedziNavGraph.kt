package pl.siedzi.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.hilt.navigation.compose.hiltViewModel
import pl.siedzi.app.ui.dashboard.DashboardScreen
import pl.siedzi.app.ui.disclaimer.DisclaimerScreen
import pl.siedzi.app.ui.fish.FishDetailScreen
import pl.siedzi.app.ui.fish.FishDetailViewModel
import pl.siedzi.app.ui.fish.FishListScreen
import pl.siedzi.app.ui.fish.FishListViewModel
import pl.siedzi.app.ui.fishery.FisheryFormScreen
import pl.siedzi.app.ui.fishery.FisheryFormViewModel
import pl.siedzi.app.ui.fishery.FisheryListScreen
import pl.siedzi.app.ui.fishery.FisheryListViewModel
import pl.siedzi.app.ui.onboarding.OnboardingScreen
import pl.siedzi.app.ui.onboarding.OnboardingViewModel
import pl.siedzi.app.ui.placeholder.PlaceholderScreen
import pl.siedzi.app.ui.splash.SplashDestination
import pl.siedzi.app.ui.splash.SplashScreen
import pl.siedzi.app.ui.splash.SplashViewModel

@Composable
fun SiedziNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SiedziRoute.Splash.path
    ) {
        composable(SiedziRoute.Splash.path) {
            val viewModel: SplashViewModel = hiltViewModel()
            val destination by viewModel.destination.collectAsState(initial = null)

            SplashScreen(
                onNavigateNext = {
                    when (destination) {
                        is SplashDestination.Onboarding -> navController.navigateAndReplace(
                            SiedziRoute.Onboarding.path
                        )
                        is SplashDestination.Dashboard -> navController.navigateAndReplace(
                            SiedziRoute.Dashboard.path
                        )
                        else -> { /* waiting */ }
                    }
                },
                viewModel = viewModel
            )
        }
        composable(SiedziRoute.Onboarding.path) {
            val viewModel: OnboardingViewModel = hiltViewModel()

            OnboardingScreen(
                onComplete = {
                    viewModel.completeOnboarding()
                    navController.navigateAndReplace(SiedziRoute.Dashboard.path)
                },
                onSkip = {
                    viewModel.completeOnboarding()
                    navController.navigateAndReplace(SiedziRoute.Dashboard.path)
                }
            )
        }
        composable(SiedziRoute.Dashboard.path) {
            DashboardScreen(
                onNavigateToWspomnienia = { navController.navigate(SiedziRoute.Wspomnienia.path) },
                onNavigateToPlanowanie = { navController.navigate(SiedziRoute.Planowanie.path) },
                onNavigateToSesja = { navController.navigate(SiedziRoute.Sesja.path) },
                onNavigateToGaleria = { navController.navigate(SiedziRoute.Galeria.path) },
                onNavigateToUstawienia = { navController.navigate(SiedziRoute.Ustawienia.path) },
                onNavigateToFishList = { navController.navigate(SiedziRoute.FishList.path) }
            )
        }
        composable(SiedziRoute.Disclaimer.path) {
            DisclaimerScreen(
                onAccept = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }
        composable(SiedziRoute.Wspomnienia.path) {
            PlaceholderScreen(
                title = "Wspomnienia",
                onBack = { navController.popBackStack() }
            )
        }
        composable(SiedziRoute.Planowanie.path) {
            PlaceholderScreen(
                title = "Zaplanuj Wyprawę",
                onBack = { navController.popBackStack() }
            )
        }
        composable(SiedziRoute.Sesja.path) {
            PlaceholderScreen(
                title = "Rozpocznij Sesję",
                onBack = { navController.popBackStack() }
            )
        }
        composable(SiedziRoute.Galeria.path) {
            PlaceholderScreen(
                title = "Galeria",
                onBack = { navController.popBackStack() }
            )
        }
        composable(SiedziRoute.Ustawienia.path) {
            PlaceholderScreen(
                title = "Ustawienia",
                onBack = { navController.popBackStack() },
                onDisclaimerClick = { navController.navigate(SiedziRoute.Disclaimer.path) },
                onFisheriesClick = { navController.navigate(SiedziRoute.FisheryList.path) }
            )
        }
        composable(SiedziRoute.FishList.path) {
            val viewModel: FishListViewModel = hiltViewModel()
            val species by viewModel.species.collectAsState()
            val searchQuery by viewModel.searchQuery.collectAsState()

            FishListScreen(
                species = species,
                searchQuery = searchQuery,
                onSearchChange = viewModel::onSearchChange,
                onSpeciesClick = { species ->
                    navController.navigate(SiedziRoute.FishDetail.create(species.id))
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = SiedziRoute.FishDetail.path,
            arguments = listOf(navArgument("speciesId") { type = NavType.StringType })
        ) {
            val viewModel: FishDetailViewModel = hiltViewModel()
            val species by viewModel.species.collectAsState()

            FishDetailScreen(
                species = species,
                onBack = { navController.popBackStack() }
            )
        }
        composable(SiedziRoute.FisheryList.path) {
            val viewModel: FisheryListViewModel = hiltViewModel()
            val fisheries by viewModel.fisheries.collectAsState()
            val searchQuery by viewModel.searchQuery.collectAsState()

            FisheryListScreen(
                fisheries = fisheries,
                searchQuery = searchQuery,
                onSearchChange = viewModel::onSearchChange,
                onFisheryClick = { fishery ->
                    navController.navigate(SiedziRoute.FisheryForm.create(fishery.id))
                },
                onAddClick = { navController.navigate(SiedziRoute.FisheryForm.create("new")) },
                onImportSample = { viewModel.importSample() },
                onImportJson = { viewModel.importFromJson() },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = SiedziRoute.FisheryForm.path,
            arguments = listOf(navArgument("fisheryId") { type = NavType.StringType })
        ) {
            val viewModel: FisheryFormViewModel = hiltViewModel()
            val fishery by viewModel.fishery.collectAsState()

            FisheryFormScreen(
                fishery = fishery,
                onSave = { name, address, lat, lon, stationNote ->
                    viewModel.save(name, address, lat, lon, stationNote) {
                        navController.popBackStack()
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}

private fun NavHostController.navigateAndReplace(route: String) {
    navigate(route) {
        popUpTo(SiedziRoute.Splash.path) { inclusive = true }
        launchSingleTop = true
    }
}
