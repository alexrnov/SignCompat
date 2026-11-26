package alexrnov.signcompat.presentation.components

import alexrnov.signcompat.domain.usecase.Gender
import alexrnov.signcompat.presentation.toColor
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GenderComponent(
	userGender: Gender,
	targetGender: Gender,
	onUserGenderSelected: (Gender) -> Unit,
	onTargetGenderSelected: (Gender) -> Unit,
	modifier: Modifier = Modifier
) {
	Row(
		modifier = modifier
			.fillMaxWidth()
			.wrapContentHeight()
			.background(Color.Magenta)
	) {
		GenderSelector(
			title = "Мой пол",
			selectedGender = userGender,
			onGenderSelected = onUserGenderSelected,
			modifier = Modifier
				.weight(0.5f)
				.padding(end = 4.dp)
				.background(Color.Red)
		)

		GenderSelector(
			title = "Пол других знаков",
			selectedGender = targetGender,
			onGenderSelected = onTargetGenderSelected,
			modifier = Modifier
				.weight(0.5f)
				.padding(start = 4.dp)
				.background(Color.Blue)
		)
	}
}

@Composable
fun GenderSelector(
	title: String,
	selectedGender: Gender,
	onGenderSelected: (Gender) -> Unit,
	modifier: Modifier = Modifier
) {
	val dividerColor = Color.LightGray

	Column(
		modifier = modifier,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(
			text = title,
			maxLines = 1,
			softWrap = false,
			overflow = TextOverflow.Ellipsis, // Сработает, если текст не влезет даже при labelSmall
			style = MaterialTheme.typography.labelLarge,
			autoSize = TextAutoSize.StepBased(
				minFontSize = MaterialTheme.typography.labelMedium.fontSize,
				maxFontSize = MaterialTheme.typography.labelLarge.fontSize,
				stepSize = 0.5.sp
			),
			textAlign = TextAlign.Center,
			modifier = Modifier.fillMaxWidth()
		)

		Row(
			modifier = Modifier.fillMaxWidth()
		) {
			genders.forEach { genderButton ->
				val isSelected = selectedGender == genderButton.type

				Surface(
					modifier = Modifier
						.weight(0.5f)
						.fillMaxHeight(),
					shape = RectangleShape,
					color = if (isSelected) {
						MaterialTheme.colorScheme.primaryContainer
					} else {
						MaterialTheme.colorScheme.surfaceContainer
					},
					onClick = { onGenderSelected(genderButton.type) }
				) {
					Row(
						modifier = Modifier
							.fillMaxSize()
							.border(width = 0.5.dp, color = dividerColor, shape = RectangleShape)
							.padding(start = 5.dp, end = 3.dp), // <-- Добавляем отступ от левого края
						verticalAlignment = Alignment.CenterVertically,
						horizontalArrangement = Arrangement.Start // <-- Выравниваем содержимое по левому краю
					) {
						Box(
							modifier = Modifier
								.padding(end = 5.dp)
								.fillMaxHeight(0.80f)
								.aspectRatio(1f)
								.background(color = genderButton.ringColor, shape = CircleShape)
								.padding(4.dp),
							contentAlignment = Alignment.Center
						) {
							Box(
								modifier = Modifier
									.fillMaxSize()
									.background(color = Color.White, shape = CircleShape),
								contentAlignment = Alignment.Center
							) {
								//Text(text = genderButton.icon, fontSize = 14.sp)
							}
						}

						Text(
							text = genderButton.name,
							maxLines = 1,
							softWrap = false,
							overflow = TextOverflow.Ellipsis,
							style = MaterialTheme.typography.labelSmall,
						)
					}
				}
			}
		}
	}
}

data class GenderButton(
	val type: Gender,
	val name: String,
	val icon: String,
	val ringColor: Color
)

val genders = listOf(
	GenderButton(Gender.MALE, "Male", "♂️", Gender.MALE.toColor()),
	GenderButton(Gender.FEMALE, "Female", "♀️", Gender.FEMALE.toColor())
)