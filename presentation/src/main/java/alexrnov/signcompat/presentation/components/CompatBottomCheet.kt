package alexrnov.signcompat.presentation.components

import alexrnov.signcompat.domain.usecase.Gender
import alexrnov.signcompat.domain.usecase.ZodiacSign
import alexrnov.signcompat.presentation.SelectedPairDetails
import alexrnov.signcompat.presentation.getZodiacSigns
import alexrnov.signcompat.presentation.toColor
import alexrnov.signcompat.presentation.toTitle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.EmojiSupportMatch
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompatibilityBottomSheet(
	isTablet: Boolean,
	isLandscape: Boolean,
	pairDetails: SelectedPairDetails,
	onDismiss: () -> Unit
) {
	val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

	ModalBottomSheet(
		onDismissRequest = onDismiss,
		sheetState = sheetState,
		sheetMaxWidth = if (isLandscape) 600.dp else BottomSheetDefaults.SheetMaxWidth,
		containerColor = MaterialTheme.colorScheme.surface,
		dragHandle = { BottomSheetDefaults.DragHandle() }
	) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = sidePadding(isTablet))
				.padding(bottom = verticalPadding(isLandscape, isTablet)), // Отступ снизу для навигационной панели Android
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(16.dp)
		) {
			Text(
				text = pairDetails.description,
				style = MaterialTheme.typography.bodyMedium.copy(
					//fontWeight = FontWeight.Medium,
					hyphens = Hyphens.Auto,
					lineBreak = LineBreak(
						strategy = LineBreak.Strategy.HighQuality,
						strictness = LineBreak.Strictness.Strict,
						wordBreak = LineBreak.WordBreak.Default
					),
					platformStyle = PlatformTextStyle(includeFontPadding = false)
				),
				color = MaterialTheme.colorScheme.onSurfaceVariant, // color = MaterialTheme.colorScheme.onSurface
				textAlign = TextAlign.Justify,
				modifier = Modifier.fillMaxWidth()
			)

			Button(
				onClick = onDismiss,
				modifier = Modifier
					.then(if (isLandscape) Modifier.widthIn(max = 400.dp) else Modifier)
					.fillMaxWidth()
			) {
				Text("Ok")
			}
		}
	}
}

fun sidePadding(isTablet: Boolean): Dp {
	return if (isTablet) 16.dp else 12.dp
}

fun verticalPadding(isLandscape: Boolean, isTablet: Boolean): Dp {
	return when {
		isLandscape -> if (isTablet) 42.dp else 14.dp
		else -> if (isTablet) 48.dp else 0.dp
	}
}