package pl.siedzi.app.navigation

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
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
import pl.siedzi.app.ui.map.MapCropScreen
import pl.siedzi.app.ui.onboarding.OnboardingScreen
import pl.siedzi.app.ui.onboarding.OnboardingViewModel
import pl.siedzi.app.ui.planning.PlanningFlowScreen
import pl.siedzi.app.ui.planning.PlanningViewModel
import pl.siedzi.app.ui.placeholder.PlaceholderScreen
import pl.siedzi.app.ui.session.CheckInScreen
import pl.siedzi.app.ui.session.SessionHubScreen
import pl.siedzi.app.ui.session.SessionHubViewModel
import pl.siedzi.app.ui.tackle.TackleFormScreen
import pl.siedzi.app.ui.tackle.TackleFormViewModel
import pl.siedzi.app.ui.tackle.SessionTimelineScreen
import pl.siedzi.app.ui.tackle.SessionTimelineViewModel
import pl.siedzi.app.ui.tackle.ChangeSetupScreen
import pl.siedzi.app.ui.tackle.ChangeSetupViewModel
import pl.siedzi.app.ui.catchflow.CatchPhotoScreen
import pl.siedzi.app.ui.catchflow.CatchPhotoViewModel
import pl.siedzi.app.ui.catchflow.CatchCardScreen
import pl.siedzi.app.ui.catchflow.CatchCardViewModel
import pl.siedzi.app.data.PendingCatchPhotos
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
            val viewModel: PlanningViewModel = hiltViewModel()
            val backStackEntry = navController.currentBackStackEntry
            val selectedFisheryId = backStackEntry?.savedStateHandle?.get<String>("selected_fishery_id")
            androidx.compose.runtime.LaunchedEffect(selectedFisheryId) {
                selectedFisheryId?.takeIf { it.isNotEmpty() }?.let { id ->
                    viewModel.setSelectedFisheryId(id)
                }
            }
            PlanningFlowScreen(
                selectedFisheryId = selectedFisheryId,
                viewModel = viewModel,
                onSelectFishery = { navController.navigate(SiedziRoute.PlanningSelectFishery.path) },
                onStartSession = { trip ->
                    viewModel.startSession(trip) { session ->
                        navController.navigate(SiedziRoute.CheckIn.create(session.id))
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(SiedziRoute.PlanningSelectFishery.path) {
            val viewModel: FisheryListViewModel = hiltViewModel()
            val fisheries by viewModel.fisheries.collectAsState()
            val searchQuery by viewModel.searchQuery.collectAsState()

            FisheryListScreen(
                fisheries = fisheries,
                searchQuery = searchQuery,
                onSearchChange = viewModel::onSearchChange,
                onFisheryClick = { },
                onAddClick = { },
                onImportSample = { },
                onImportJson = { },
                onBack = { navController.popBackStack() },
                selectionMode = true,
                onFisherySelect = { fishery ->
                    navController.previousBackStackEntry?.savedStateHandle?.set("selected_fishery_id", fishery.id)
                    navController.popBackStack()
                }
            )
        }
        composable(SiedziRoute.Sesja.path) {
            PlaceholderScreen(
                title = "Rozpocznij Sesję",
                onBack = { navController.popBackStack() },
                onPlanningClick = { navController.navigate(SiedziRoute.Planowanie.path) }
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
        ) { backStackEntry ->
            val viewModel: FisheryFormViewModel = hiltViewModel()
            val fishery by viewModel.fishery.collectAsState()
            val topoClipUriFromMap = backStackEntry.savedStateHandle.get<String>("topo_clip_uri")

            FisheryFormScreen(
                fishery = fishery,
                topoClipUriFromMap = topoClipUriFromMap,
                onSave = { name, address, lat, lon, stationNote, topoClipUri ->
                    viewModel.save(name, address, lat, lon, stationNote, topoClipUri) {
                        navController.popBackStack()
                    }
                },
                onOpenMapCrop = { lat, lon ->
                    navController.navigate(SiedziRoute.MapCrop.create(lat, lon))
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = SiedziRoute.MapCrop.path,
            arguments = listOf(
                navArgument("lat") { type = NavType.StringType },
                navArgument("lon") { type = NavType.StringType }
            )
        ) { mapCropBackStackEntry ->
            val lat = mapCropBackStackEntry.arguments?.getString("lat")?.toDoubleOrNull() ?: 0.0
            val lon = mapCropBackStackEntry.arguments?.getString("lon")?.toDoubleOrNull() ?: 0.0

            MapCropScreen(
                lat = lat,
                lon = lon,
                onSave = { uri ->
                    navController.previousBackStackEntry?.savedStateHandle?.set("topo_clip_uri", uri)
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = SiedziRoute.CheckIn.path,
            arguments = listOf(navArgument("sessionId") { type = NavType.StringType })
        ) { backStackEntry ->
            val sessionId = backStackEntry.arguments?.getString("sessionId") ?: ""
            CheckInScreen(
                onConfirm = { navController.navigate(SiedziRoute.SessionHub.create(sessionId)) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = SiedziRoute.SessionHub.path,
            arguments = listOf(navArgument("sessionId") { type = NavType.StringType })
        ) { backStackEntry ->
            val sessionId = backStackEntry.arguments?.getString("sessionId") ?: ""
            val viewModel: SessionHubViewModel = hiltViewModel()
            val fisheryName by viewModel.fisheryName.collectAsState()

            SessionHubScreen(
                sessionId = sessionId,
                fisheryName = fisheryName,
                onNavigateToTackleForm = {
                    navController.navigate(SiedziRoute.TackleForm.create(sessionId))
                },
                onNavigateToTimeline = {
                    navController.navigate(SiedziRoute.SessionTimeline.create(sessionId))
                },
                onNavigateToChangeSetup = {
                    navController.navigate(SiedziRoute.ChangeSetup.create(sessionId))
                },
                onEndSession = {
                    viewModel.endSession {
                        navController.navigate(SiedziRoute.Dashboard.path) {
                            popUpTo(SiedziRoute.Dashboard.path) { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = SiedziRoute.TackleForm.path,
            arguments = listOf(navArgument("sessionId") { type = NavType.StringType })
        ) { backStackEntry ->
            val sessionId = backStackEntry.arguments?.getString("sessionId") ?: ""
            val viewModel: TackleFormViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            val fisheryName by viewModel.fisheryName.collectAsState()

            TackleFormScreen(
                fisheryName = fisheryName.ifBlank { "Sesja" },
                state = state,
                onRodChange = { viewModel.updateRod(null, it) },
                onReelChange = { viewModel.updateReel(null, it) },
                onLineChange = { viewModel.updateLine(null, it) },
                onBaitChange = { viewModel.updateBait(null, it) },
                onHookChange = { viewModel.updateHook(null, it) },
                onGroundbaitChange = viewModel::updateGroundbait,
                onHasBoatChange = viewModel::setHasBoat,
                onHasEchosounderChange = viewModel::setHasEchosounder,
                onCastClick = { viewModel.castRod { navController.popBackStack() } },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = SiedziRoute.SessionTimeline.path,
            arguments = listOf(navArgument("sessionId") { type = NavType.StringType })
        ) { backStackEntry ->
            val sessionId = backStackEntry.arguments?.getString("sessionId") ?: ""
            val viewModel: SessionTimelineViewModel = hiltViewModel()
            val fisheryName by viewModel.fisheryName.collectAsState()
            val entries by viewModel.entries.collectAsState()

            SessionTimelineScreen(
                fisheryName = fisheryName,
                entries = entries,
                onBranieClick = {
                    navController.navigate(SiedziRoute.CatchPhoto.create(sessionId))
                },
                onChangeSetupClick = {
                    navController.navigate(SiedziRoute.ChangeSetup.create(sessionId))
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = SiedziRoute.ChangeSetup.path,
            arguments = listOf(navArgument("sessionId") { type = NavType.StringType })
        ) {
            val viewModel: ChangeSetupViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            val previousTime by viewModel.previousTime.collectAsState()
            val previousSetupTags by viewModel.previousSetupTags.collectAsState()

            ChangeSetupScreen(
                previousTime = previousTime,
                previousSetupTags = previousSetupTags,
                state = state,
                onRodChange = { viewModel.updateRod(null, it) },
                onReelChange = { viewModel.updateReel(null, it) },
                onLineChange = { viewModel.updateLine(null, it) },
                onBaitChange = { viewModel.updateBait(null, it) },
                onHookChange = { viewModel.updateHook(null, it) },
                onGroundbaitChange = viewModel::updateGroundbait,
                onHasBoatChange = viewModel::setHasBoat,
                onHasEchosounderChange = viewModel::setHasEchosounder,
                onCastAgainClick = { viewModel.castAgain { navController.popBackStack() } },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = SiedziRoute.CatchPhoto.path,
            arguments = listOf(navArgument("sessionId") { type = NavType.StringType })
        ) {
            val context = LocalContext.current
            val permissionLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { }
            LaunchedEffect(Unit) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    permissionLauncher.launch(
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                    )
                }
            }

            val viewModel: CatchPhotoViewModel = hiltViewModel()
            val photoUris by viewModel.photoUris.collectAsState()

            CatchPhotoScreen(
                photoUris = photoUris,
                canAddMore = viewModel.canAddMore,
                onPhotosSelected = { viewModel.addPhotos(it) },
                onRemovePhoto = { viewModel.removePhoto(it) },
                onNavigateToCatchCard = {
                    PendingCatchPhotos.set(photoUris)
                    navController.navigate(SiedziRoute.CatchCard.create(viewModel.getSessionId()))
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = SiedziRoute.CatchCard.path,
            arguments = listOf(navArgument("sessionId") { type = NavType.StringType })
        ) {
            val viewModel: CatchCardViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            val speciesList by viewModel.speciesList.collectAsState(initial = emptyList())

            CatchCardScreen(
                state = state,
                speciesList = speciesList,
                onSpeciesSelect = { viewModel.setSpecies(it) },
                onWeightChange = { viewModel.setWeight(it) },
                onLengthChange = { viewModel.setLength(it) },
                onNicknameChange = { viewModel.setNickname(it) },
                onSave = {
                    viewModel.saveCatch {
                        navController.navigate(SiedziRoute.SessionTimeline.create(viewModel.getSessionId())) {
                            popUpTo(SiedziRoute.SessionTimeline.path) { inclusive = false }
                            launchSingleTop = true
                        }
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
