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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompatibilityBottomSheet(
	userSign: ZodiacSign,
	userGender: Gender,
	targetGender: Gender,
	pairDetails: SelectedPairDetails,
	onDismiss: () -> Unit
) {
	val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
	val zodiacSymbols = getZodiacSigns()

	val userSignInfo = zodiacSymbols[userSign.ordinal]
	val targetSignInfo = zodiacSymbols[pairDetails.targetSign.ordinal]
	val levelColor = pairDetails.level.toColor()

	ModalBottomSheet(
		onDismissRequest = onDismiss,
		sheetState = sheetState,
		containerColor = MaterialTheme.colorScheme.surface,
		dragHandle = { BottomSheetDefaults.DragHandle() }
	) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 24.dp)
				.padding(bottom = 32.dp), // Отступ снизу для навигационной панели Android
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(16.dp)
		) {
			/*
			// 1. Иконки двух знаков с кольцами пола
			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(16.dp)
			) {
				Text(
					text = "${userSignInfo.name}",
					style = MaterialTheme.typography.titleLarge,
					textAlign = TextAlign.Center
				)
				SignSheetBadge(
					icon = userSignInfo.icon,
					ringColor = if (userGender == Gender.MALE) Color(0xFF2196F3) else Color(0xFFE91E63)
				)
				Text(
					text = "+",
					style = MaterialTheme.typography.headlineSmall,
					color = MaterialTheme.colorScheme.onSurfaceVariant
				)
				SignSheetBadge(
					icon = targetSignInfo.icon,
					ringColor = if (targetGender == Gender.MALE) Color(0xFF2196F3) else Color(0xFFE91E63)
				)
				Text(
					text = "${targetSignInfo.name}",
					style = MaterialTheme.typography.titleLarge,
					textAlign = TextAlign.Center
				)
			}

			// 3. Цветной бейдж уровня совместимости
			Surface(
				color = levelColor.copy(alpha = 0.15f),
				shape = CircleShape
			) {
				Text(
					text = "${pairDetails.level.toTitle()} (${pairDetails.level.score}/6)",
					color = levelColor,
					style = MaterialTheme.typography.labelLarge,
					modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
				)
			}

			HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

			*/



			// 4. Подробный текст из compatibility_descriptions.json
			Text(
				text = pairDetails.description,
				style = MaterialTheme.typography.bodyLarge,
				color = MaterialTheme.colorScheme.onSurface,
				lineHeight = 24.sp
			)

			Spacer(modifier = Modifier.height(8.dp))

			// 5. Кнопка закрытия
			Button(
				onClick = onDismiss,
				modifier = Modifier.fillMaxWidth()
			) {
				Text("Ok")
			}
		}
	}
}

@Composable
private fun SignSheetBadge(icon: String, ringColor: Color) {
	val whiteEmojiStyle = TextStyle(
		color = Color.White,
		platformStyle = PlatformTextStyle(emojiSupportMatch = EmojiSupportMatch.None)
	)

	Box(
		modifier = Modifier
			.size(56.dp)
			.background(color = ringColor, shape = CircleShape)
			.padding(4.dp),
		contentAlignment = Alignment.Center
	) {
		Box(
			modifier = Modifier
				.fillMaxSize()
				.background(color = Color(0xFF8E24AA), shape = CircleShape),
			contentAlignment = Alignment.Center
		) {
			Text(text = icon, style = whiteEmojiStyle, fontSize = 22.sp)
		}
	}
}