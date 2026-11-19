package alexrnov.signcompat.presentation.components

import alexrnov.signcompat.presentation.SelectedPairDetails
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompatibilityBottomSheet(
	isTablet: Boolean,
	isLandscape: Boolean,
	pairDetails: SelectedPairDetails,
	onDismiss: () -> Unit
) {
	val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
	val scrollState = rememberScrollState()

	ModalBottomSheet(
		onDismissRequest = onDismiss,
		sheetState = sheetState,
		modifier = Modifier.statusBarsPadding(),
		contentWindowInsets = {
			WindowInsets.navigationBars // Шторка не заходит на статус-бар
				.only(WindowInsetsSides.Bottom) // Берем только нижний отступ, игнорируя боковые панели и вырезы (чтобы горизонтальные отступы не сбивались в альбомной ориентации)
		},
		sheetMaxWidth = if (isLandscape) 500.dp else BottomSheetDefaults.SheetMaxWidth,
		containerColor = MaterialTheme.colorScheme.surface,
		dragHandle = { BottomSheetDefaults.DragHandle() }
	) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = horizontalPadding(isTablet))
				.padding(bottom = verticalPadding(isLandscape, isTablet)),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(16.dp)
		) {
			// Прокручиваемая область для текста
			Box(
				modifier = Modifier
					.weight(1f, fill = false) // Ключевой параметр: сжимается под короткий текст, но ограничивает длинный
					.verticalScroll(scrollState)
			) {
				Text(
					text = pairDetails.description,
					style = MaterialTheme.typography.bodyMedium.copy(
						hyphens = Hyphens.Auto,
						lineBreak = LineBreak(
							strategy = LineBreak.Strategy.HighQuality,
							strictness = LineBreak.Strictness.Strict,
							wordBreak = LineBreak.WordBreak.Default
						),
						platformStyle = PlatformTextStyle(includeFontPadding = false)
					),
					color = MaterialTheme.colorScheme.onSurfaceVariant,
					textAlign = TextAlign.Justify,
					modifier = Modifier.fillMaxWidth()
				)
			}

			// Зафиксированная кнопка внизу
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

fun horizontalPadding(isTablet: Boolean): Dp {
	return if (isTablet) 16.dp else 16.dp
}

fun verticalPadding(isLandscape: Boolean, isTablet: Boolean): Dp {
	return when {
		isLandscape -> if (isTablet) 42.dp else 8.dp
		else -> if (isTablet) 48.dp else 0.dp
	}
}