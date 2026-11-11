package alexrnov.signcompat.presentation.components

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GenderComponent() {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.wrapContentHeight() // Даем контейнеру подстроиться под высоту 40.dp наших кнопок
			.padding(top = 4.dp)
			.background(Color.Magenta)
	) {
		GenderSelector(
			title = "мой пол",
			selectedGenderIndex = 0, // ТУТ: передай переменную состояния (например, myGenderState)
			onGenderSelected = { index ->
				// ТУТ: обновляй свое состояние при клике
			},
			modifier = Modifier
				.weight(0.5f)
				.background(Color.Red) // Оставляем твой красный фон для проверки
		)

		// 2. Блок: Пол других знаков
		GenderSelector(
			title = "пол других знаков",
			selectedGenderIndex = 1, // ТУТ: передай переменную состояния (например, otherGenderState)
			onGenderSelected = { index ->
				// ТУТ: обновляй свое состояние при клике
			},
			modifier = Modifier
				.weight(0.5f)
				.background(Color.Blue) // Оставляем твой синий фон для проверки
		)
	}
}

@Composable
fun GenderSelector(
	title: String,
	selectedGenderIndex: Int,
	onGenderSelected: (Int) -> Unit,
	modifier: Modifier = Modifier
) {

	val dividerColor = Color.LightGray

	Column(
		modifier = modifier.padding(2.dp),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(
			text = title,
			style = MaterialTheme.typography.labelMedium,
			textAlign = TextAlign.Center,
			modifier = Modifier
				.fillMaxWidth()
				.padding(bottom = 4.dp)
		)

		Row(modifier = Modifier.fillMaxWidth().height(44.dp)) {
			genders.forEachIndexed { index, gender ->
				val isSelected = selectedGenderIndex == index

				Surface(
					modifier = Modifier.weight(0.5f).fillMaxHeight(),
					shape = RectangleShape,
					color = if (isSelected) {
						MaterialTheme.colorScheme.primaryContainer
					} else {
						MaterialTheme.colorScheme.surfaceContainer
					},
					onClick = { onGenderSelected(index) }
				) {
					Row(
						modifier = Modifier
							.fillMaxSize()
							.border(width = 0.5.dp, color = dividerColor, shape = RectangleShape),
						verticalAlignment = Alignment.CenterVertically,
						horizontalArrangement = Arrangement.Center
					) {
						val ringColor = if (index == 0) Color(0xFF2196F3) else Color(0xFFFF6293)
						Box(
							modifier = Modifier
								.padding(end = 8.dp)
								.fillMaxHeight(0.8f)
								.aspectRatio(1f)
								.background(color = ringColor, shape = CircleShape)
								.padding(5.dp),
							contentAlignment = Alignment.Center
						) {
							Box(
								modifier = Modifier
									.fillMaxSize()
									.background(color = Color.White, shape = CircleShape),
								contentAlignment = Alignment.Center
							) {
								Text(text = gender.icon, fontSize = 14.sp)
							}
						}

						Text(
							text = gender.name,
							maxLines = 1,
							style = MaterialTheme.typography.labelMedium
						)
					}
				}
			}
		}
	}
}

data class GenderButton(val name: String, val icon: String)

val genders = listOf(GenderButton("Муж.", "♂️"), GenderButton("Жен.", "♀️"))