package alexrnov.signcompat.presentation.navigation


import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity

import org.koin.androidx.compose.koinViewModel

@Serializable object Settings

fun NavGraphBuilder.settingsScreen(
	innerPadding: PaddingValues,
	isLandscape: Boolean,
	isTablet: Boolean,
) {
	composable<Settings> {

	}
}