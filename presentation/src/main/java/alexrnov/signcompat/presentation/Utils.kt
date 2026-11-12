package alexrnov.signcompat.presentation

import alexrnov.signcompat.domain.usecase.ZodiacSign
import alexrnov.signcompat.presentation.components.Sign

fun getZodiacSigns() = listOf(
	Sign(ZodiacSign.ARIES, "Овен", "♈\uFE0E", "21.03-19.04"),
	Sign(ZodiacSign.TAURUS, "Телец", "♉\uFE0E", "20.04-20.05"),
	Sign(ZodiacSign.GEMINI, "Близнецы", "♊\uFE0E", "21.05-20.06"),
	Sign(ZodiacSign.CANCER, "Рак", "♋\uFE0E", "21.06-22.07"),
	Sign(ZodiacSign.LEO, "Лев", "♌\uFE0E", "23.07-22.08"),
	Sign(ZodiacSign.VIRGO, "Дева", "♍\uFE0E", "23.08-22.09"),
	Sign(ZodiacSign.LIBRA, "Весы", "♎\uFE0E", "23.09-22.10"),
	Sign(ZodiacSign.SCORPIO, "Скорпион", "♏\uFE0E", "23.10-21.11"),
	Sign(ZodiacSign.SAGITTARIUS, "Стрелец", "♐\uFE0E", "22.11-21.12"),
	Sign(ZodiacSign.CAPRICORN, "Козерог", "♑\uFE0E", "22.12-19.01"),
	Sign(ZodiacSign.AQUARIUS, "Водолей", "♒\uFE0E", "20.01-18.02"),
	Sign(ZodiacSign.PISCES, "Рыбы", "♓\uFE0E", "19.02-20.03")
)