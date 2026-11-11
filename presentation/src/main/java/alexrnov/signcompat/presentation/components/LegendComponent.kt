package alexrnov.signcompat.presentation.components

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LegendComponent(modifier: Modifier = Modifier) {
	Column(
		modifier = modifier
			.fillMaxWidth()
			.background(Color.Red)
	) {
		Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
			Text("Совместимость", color = Color.White)
		}
		Row(modifier = Modifier.fillMaxSize()) {
			// Левая половина (Желтая)
			Column(
				modifier = Modifier
					.fillMaxHeight()
					.weight(0.5f)
					.background(Color.Yellow)
					.padding(8.dp), // Отступы внутри колонки
				verticalArrangement = Arrangement.SpaceEvenly // Равномерно распределяет 3 элемента по вертикали
			) {
				LegendItem(color = Color(0xFF0E9F00), text = "идеальная")
				LegendItem(color = Color(0xFF52E219), text = "высокая")
				LegendItem(color = Color(0xFFC8E32C), text = "нормальная")
			}

			// Правая половина (Пурпурная)
			Column(
				modifier = Modifier
					.fillMaxHeight()
					.weight(0.5f)
					.background(Color.Magenta)
					.padding(8.dp),
				verticalArrangement = Arrangement.SpaceEvenly
			) {
				LegendItem(color = Color(0xFFFFDA00), text = "приемлемая")
				LegendItem(color = Color(0xFFFFA400), text = "низкая")
				LegendItem(color = Color(0xFFFF0000), text = "очень низкая")
			}
		}
	}
}


@Composable
fun LegendItem(color: Color, text: String) {
	Row(
		modifier = Modifier.fillMaxWidth(),
		verticalAlignment = Alignment.CenterVertically // Выравнивает линию и текст по центру по вертикали
	) {
		// Цветная линия
		Box(
			modifier = Modifier
				.width(36.dp)        // Ширина линии
				.height(4.dp)         // Толщина линии
				.background(color, shape = CircleShape) // Скругленные края для аккуратности
		)

		Spacer(modifier = Modifier.width(8.dp)) // Отступ между линией и текстом

		// Надпись (расшифровка)
		Text(
			text = text,
			style = MaterialTheme.typography.bodyMedium
		)
	}
}