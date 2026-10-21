package app.tryliner.liner.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.tryliner.liner.ui.screens.login.LoginScreen
import app.tryliner.liner.ui.screens.search.SearchScreen
import app.tryliner.liner.ui.screens.player.PlayerScreen
import app.tryliner.liner.ui.screens.library.LibraryScreen
import app.tryliner.liner.ui.screens.home.HomeScreen

object Routes {
    const val LOGIN = "login"
    const val HOME = "home"
    const val SEARCH = "search"
    const val PLAYER = "player"
    const val LIBRARY = "library"
    const val ARTIST = "artist/{artistId}"
    const val ALBUM = "album/{albumId}"
    
    fun artist(artistId: String) = "artist/$artistId"
    fun album(albumId: String) = "album/$albumId"
}

@Composable
fun LinerNavHost() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Routes.HOME) {
            HomeScreen(
                onNavigateToSearch = { navController.navigate(Routes.SEARCH) },
                onNavigateToLibrary = { navController.navigate(Routes.LIBRARY) },
                onNavigateToPlayer = { navController.navigate(Routes.PLAYER) }
            )
        }
        
        composable(Routes.SEARCH) {
            SearchScreen(
                onNavigateBack = { navController.popBackStack() },
                onTrackClick = { navController.navigate(Routes.PLAYER) }
            )
        }
        
        composable(Routes.PLAYER) {
            PlayerScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Routes.LIBRARY) {
            LibraryScreen(
                onNavigateBack = { navController.popBackStack() },
                onTrackClick = { navController.navigate(Routes.PLAYER) }
            )
        }
    }
}
