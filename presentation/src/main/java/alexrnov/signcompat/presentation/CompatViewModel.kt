package alexrnov.signcompat.presentation

import alexrnov.signcompat.domain.usecase.CompatLevel
import alexrnov.signcompat.domain.usecase.CompatUseCase
import alexrnov.signcompat.domain.usecase.Gender
import alexrnov.signcompat.domain.usecase.Person
import alexrnov.signcompat.domain.usecase.SignCompatResult
import alexrnov.signcompat.domain.usecase.ZodiacSign
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CompatUiState(
	val userSign: ZodiacSign = ZodiacSign.ARIES,
	val userGender: Gender = Gender.MALE,
	val targetGender: Gender = Gender.FEMALE,
	val results: List<SignCompatResult> = emptyList()
)

class CompatViewModel(
	private val compatUseCase: CompatUseCase
) : ViewModel() {

	private val _uiState = MutableStateFlow(CompatUiState())
	val uiState = _uiState.asStateFlow()

	init {
		// Сразу считаем список для начальных значений по умолчанию
		recalculate()
	}

	fun onUserSignChanged(newSign: ZodiacSign) {
		Log.i("signcompat", "sign: $newSign")
		_uiState.update { it.copy(userSign = newSign) }
		recalculate()
	}

	fun onUserGenderChanged(newGender: Gender) {
		_uiState.update { it.copy(userGender = newGender) }
		recalculate()
	}

	fun onTargetGenderChanged(newTargetGender: Gender) {
		_uiState.update { it.copy(targetGender = newTargetGender) }
		recalculate()
	}

	private fun recalculate() {
		val current = _uiState.value
		val user = Person(sign = current.userSign, gender = current.userGender)

		// Получаем список из 12 результатов (при желании можно добавить .sortedByDescending { it.level.score })
		val calculatedList = compatUseCase(user = user, targetGender = current.targetGender)

		_uiState.update { it.copy(results = calculatedList) }
	}
}