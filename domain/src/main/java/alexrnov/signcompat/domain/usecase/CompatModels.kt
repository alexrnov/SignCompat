package alexrnov.signcompat.domain.usecase

enum class Gender { MALE, FEMALE }

data class Person(val sign: ZodiacSign, val gender: Gender)

enum class ZodiacSign {
	ARIES, // Овен
	TAURUS, // Телец
	GEMINI, // Близнецы
	CANCER, // Рак
	LEO, // Лев
	VIRGO, // Дева
	LIBRA, // Весы
	SCORPIO, // Скорпион
	SAGITTARIUS, // Стрелец
	CAPRICORN, // Козерог
	AQUARIUS, // Водолей
	PISCES // Рыбы
}

enum class CompatLevel(val score: Int) {
	IDEAL(6), HIGH(5), NORMAL(4), ACCEPTABLE(3), LOW(2), VERY_LOW(1)
}

data class SignCompatResult(
	val targetSign: ZodiacSign,
	val level: CompatLevel
)