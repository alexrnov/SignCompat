package alexrnov.signcompat.presentation.components

import alexrnov.signcompat.domain.usecase.Person
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.EmojiSupportMatch
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SignComponent(modifier: Modifier = Modifier) {
	// 2. Добавляем временные интервалы для каждого знака
	val signs = listOf(
		Sign("Овен", "♈\uFE0E", "21.03-19.04"),
		Sign("Телец", "♉\uFE0E", "20.04-20.05"),
		Sign("Близнецы", "♊\uFE0E", "21.05-20.06"),
		Sign("Рак", "♋\uFE0E", "21.06-22.07"),
		Sign("Лев", "♌\uFE0E", "23.07-22.08"),
		Sign("Дева", "♍\uFE0E", "23.08-22.09"),
		Sign("Весы", "♎\uFE0E", "23.09-22.10"),
		Sign("Скорпион", "♏\uFE0E", "23.10-21.11"),
		Sign("Стрелец", "♐\uFE0E", "22.11-21.12"),
		Sign("Козерог", "♑\uFE0E", "22.12-19.01"),
		Sign("Водолей", "♒\uFE0E", "20.01-18.02"),
		Sign("Рыбы", "♓\uFE0E", "19.02-20.03")
	)

	var selectedSign by remember { mutableStateOf<Sign?>(null) }
	val rows = remember(signs) { signs.chunked(3) }

	val whiteEmojiStyle = TextStyle(
		color = Color.White,
		platformStyle = PlatformTextStyle(emojiSupportMatch = EmojiSupportMatch.None)
	)

	val dividerColor = Color.LightGray

	Column(
		modifier = modifier
			.fillMaxWidth()
			.border(width = 1.dp, color = dividerColor, shape = RectangleShape)
	) {
		rows.forEach { rowItems ->
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.weight(1f)
			) {
				rowItems.forEach { sign ->
					val isSelected = selectedSign == sign

					Surface(
						modifier = Modifier
							.weight(1f)
							.fillMaxHeight(),
						shape = RectangleShape,
						color = if (isSelected) {
							MaterialTheme.colorScheme.primaryContainer
						} else {
							MaterialTheme.colorScheme.surfaceContainer
						},
						onClick = { selectedSign = sign }
					) {
						// ИСПРАВЛЕНО: Вернули модификатор .border сюда
						Row(
							modifier = Modifier
								.fillMaxSize()
								.border(width = 0.5.dp, color = dividerColor, shape = RectangleShape),
							verticalAlignment = Alignment.CenterVertically
						) {
							// Фиолетовый кружок с иконкой
							Box(
								modifier = Modifier
									.padding(start = 6.dp)
									.fillMaxHeight(0.6f)
									.aspectRatio(1f)
									.background(color = Color(0xFF8E24AA), shape = CircleShape),
								contentAlignment = Alignment.Center
							) {
								Text(text = sign.icon, style = whiteEmojiStyle, fontSize = 14.sp)
							}

							// Блок с названием и датами
							Box(
								modifier = Modifier.weight(1f),
								contentAlignment = Alignment.Center
							) {
								Column(
									horizontalAlignment = Alignment.CenterHorizontally,
									verticalArrangement = Arrangement.spacedBy(2.dp)
								) {
									Text(
										text = sign.name,
										style = MaterialTheme.typography.labelMedium
									)
									Text(
										text = sign.period,
										style = MaterialTheme.typography.bodySmall,
										color = MaterialTheme.colorScheme.onSurfaceVariant
									)
								}
							}
						}
					}
				}
			}
		}
	}
}

data class Sign(val name: String, val icon: String, val period: String)