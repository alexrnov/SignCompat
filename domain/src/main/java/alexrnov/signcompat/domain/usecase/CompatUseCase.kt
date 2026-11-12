package alexrnov.signcompat.domain.usecase

import alexrnov.signcompat.domain.usecase.Gender.*

class CompatUseCase {
	/**
	 * ### Примеры конкретных отличий между М+М и Ж+Ж в коде
	 * * **Два Льва (`LEO` + `LEO`).** В мужской паре (`maleMaleMatrix`) уровень `LOW` (жёсткая борьба за роль вожака), а в женской (`femaleFemaleMatrix`) — `NORMAL` (женщины-Львы чаще восхищаются стилем друг друга, чем делят территорию).
	 * * **Два Рака (`CANCER` + `CANCER`).** В женской паре — `IDEAL` (глубокая взаимная забота и эмпатия), а в мужской — `NORMAL` (обоим мужчинам бывает сложно проявлять инициативу и брать на себя роль опоры).
	 * * **Два Козерога (`CAPRICORN` + `CAPRICORN`).** В мужской паре — `IDEAL` (непоколебимый деловой и жизненный тандем), а в женской — `NORMAL` (склонность к излишней строгости и эмоциональной закрытости).
	 */
	// метод для одной пары
	operator fun invoke(first: Person, second: Person): CompatLevel {
		return when {
			first.gender == MALE && second.gender == FEMALE -> heteroMatrix[first.sign]?.get(second.sign)
			first.gender == FEMALE && second.gender == MALE -> heteroMatrix[second.sign]?.get(first.sign)
			first.gender == MALE && second.gender == MALE -> maleMaleMatrix[first.sign]?.get(second.sign)
			else -> femaleFemaleMatrix[first.sign]?.get(second.sign)
		} ?: throw IllegalArgumentException("Комбинация не найдена")
	}

	// расчёт для одного человека со всеми знаками указанного пола
	operator fun invoke(user: Person, targetGender: Gender): List<SignCompatResult> {
		return ZodiacSign.entries.map { targetSign ->
			val targetPerson = Person(sign = targetSign, gender = targetGender)
			val level = invoke(user, targetPerson)
			SignCompatResult(targetSign = targetSign, level = level)
		}
	}
}