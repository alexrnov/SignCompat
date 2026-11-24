package alexrnov.signcompat.presentation.components

import alexrnov.signcompat.domain.usecase.CompatLevel
import alexrnov.signcompat.presentation.toColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun LegendComponent(modifier: Modifier = Modifier) {
	Column(
		modifier = modifier
			.fillMaxWidth()
			//.background(Color.Red)
	) {
		Box(
			modifier = Modifier.fillMaxWidth(),
			contentAlignment = Alignment.Center
		) {
			Text(
				text = "Совместимость",
				//style = MaterialTheme.typography.bodyMedium,
				style = MaterialTheme.typography.titleMedium,
				color = MaterialTheme.colorScheme.onSurfaceVariant,
				//color = Color.White,
				maxLines = 1
			)
		}

		// Используем weight(1f) вместо fillMaxSize(), чтобы Row заняла ровно оставшееся место
		Row(modifier = Modifier.weight(1f)) {
			// Левая половина
			Column(
				modifier = Modifier
					.fillMaxHeight()
					.weight(0.5f)
					//.background(Color.Yellow)
					.padding(horizontal = 8.dp, vertical = 2.dp) // Уменьшаем вертикальный padding для малых высот
			) {
				LegendItem(
					color = CompatLevel.IDEAL.toColor(),
					text = "идеальная",
					modifier = Modifier.weight(1f)
				)
				LegendItem(
					color = CompatLevel.HIGH.toColor(),
					text = "высокая",
					modifier = Modifier.weight(1f)
				)
				LegendItem(
					color = CompatLevel.NORMAL.toColor(),
					text = "нормальная",
					modifier = Modifier.weight(1f)
				)
			}

			// Правая половина
			Column(
				modifier = Modifier
					.fillMaxHeight()
					.weight(0.5f)
					//.background(Color.Magenta)
					.padding(horizontal = 8.dp, vertical = 2.dp)
			) {
				LegendItem(
					color = CompatLevel.ACCEPTABLE.toColor(),
					text = "приемлемая",
					modifier = Modifier.weight(1f)
				)
				LegendItem(
					color = CompatLevel.LOW.toColor(),
					text = "низкая",
					modifier = Modifier.weight(1f)
				)
				LegendItem(
					color = CompatLevel.VERY_LOW.toColor(),
					text = "очень низкая",
					modifier = Modifier.weight(1f)
				)
			}
		}
	}
}

@Composable
fun LegendItem(
	color: Color,
	text: String,
	modifier: Modifier = Modifier
) {
	val textStyle = MaterialTheme.typography.labelLarge.copy(
		platformStyle = PlatformTextStyle(includeFontPadding = false)
	)
	val density = LocalDensity.current

	Row(
		modifier = modifier.fillMaxWidth(),
		verticalAlignment = Alignment.CenterVertically
	) {
		Box(
			modifier = Modifier
				.width(36.dp)
				.height(4.dp)
				.background(color, shape = CircleShape)
				.alignBy { placeable ->
					// Вычисляем оптическую середину строчной буквы в пикселях с учетом fontScale
					val xHeightCenterPx = with(density) { (textStyle.fontSize * 0.28f).roundToPx() }
					// Совмещаем геометрический центр линии (measuredHeight / 2) с оптическим центром букв
					(placeable.measuredHeight / 2) + xHeightCenterPx
				}
		)

		Spacer(modifier = Modifier.width(8.dp))

		Text(
			text = text,
			modifier = Modifier.alignBy(FirstBaseline),
			style = textStyle,
			color = MaterialTheme.colorScheme.onSurfaceVariant,
			maxLines = 1,
			overflow = TextOverflow.Ellipsis
		)
	}
}