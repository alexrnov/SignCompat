package alexrnov.signcompat.presentation.components

import alexrnov.signcompat.domain.usecase.Person
import alexrnov.signcompat.domain.usecase.ZodiacSign
import alexrnov.signcompat.presentation.getZodiacSigns
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
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
import androidx.compose.foundation.text.TextAutoSize
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SignComponent(
	selectedSign: ZodiacSign,
	onSignSelected: (ZodiacSign) -> Unit,
	modifier: Modifier = Modifier
) {
	val signs = remember { getZodiacSigns() }

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
					// Сравниваем напрямую с enum из ViewModel
					val isSelected = selectedSign == sign.type

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
						onClick = { onSignSelected(sign.type) } // Сообщаем ViewModel о выборе
					) {
						Row(
							modifier = Modifier
								.fillMaxSize()
								.border(width = 0.5.dp, color = dividerColor, shape = RectangleShape),
							verticalAlignment = Alignment.CenterVertically
						) {
							Box(
								modifier = Modifier
									.padding(start = 5.dp)
									.fillMaxHeight(0.6f)
									.aspectRatio(1f)
									.background(color = Color(0xFF8E24AA), shape = CircleShape),
								contentAlignment = Alignment.Center
							) {
								Text(text = sign.icon, style = whiteEmojiStyle, fontSize = 14.sp)
							}

							Box(
								modifier = Modifier.weight(1f).padding(end = 3.dp),
								contentAlignment = Alignment.Center
							) {
								Column(
									horizontalAlignment = Alignment.CenterHorizontally,
									verticalArrangement = Arrangement.spacedBy(2.dp)
								) {
									Text(
										text = sign.name,
										modifier = Modifier
											.padding(start = 3.dp, end = 3.dp)
											.then(
												if (isSelected) {
													Modifier.basicMarquee(iterations = Int.MAX_VALUE)
												} else {
													Modifier
												}
											),
										maxLines = 1,
										overflow = if (isSelected) TextOverflow.Clip else TextOverflow.Ellipsis,
										style = MaterialTheme.typography.labelLarge,
										color = MaterialTheme.colorScheme.onSurfaceVariant
									)

									Text(
										text = sign.period,
										maxLines = 1,
										overflow = TextOverflow.Ellipsis,
										style = MaterialTheme.typography.bodySmall,
										autoSize = TextAutoSize.StepBased(
											minFontSize = 10.sp,
											maxFontSize = MaterialTheme.typography.bodySmall.fontSize,
											stepSize = 0.5.sp
										),
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

data class Sign(
	val type: ZodiacSign,
	val name: String,
	val icon: String,
	val period: String
)