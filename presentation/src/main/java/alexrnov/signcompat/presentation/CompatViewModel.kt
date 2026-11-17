package alexrnov.signcompat.presentation

import alexrnov.signcompat.domain.DescriptionRepository
import alexrnov.signcompat.domain.PreferencesRepository
import alexrnov.signcompat.domain.usecase.CompatLevel
import alexrnov.signcompat.domain.usecase.CompatUseCase
import alexrnov.signcompat.domain.usecase.Gender
import alexrnov.signcompat.domain.usecase.Person
import alexrnov.signcompat.domain.usecase.SignCompatResult
import alexrnov.signcompat.domain.usecase.ZodiacSign
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CompatUiState(
	val isLoading: Boolean = true, // По умолчанию true, пока идёт чтение с диска
	val userSign: ZodiacSign = ZodiacSign.ARIES,
	val userGender: Gender = Gender.MALE,
	val targetGender: Gender = Gender.FEMALE,
	val results: List<SignCompatResult> = emptyList(),
	val selectedPair: SelectedPairDetails? = null // null = BottomSheet закрыт
)

class CompatViewModel(
	private val compatUseCase: CompatUseCase,
	private val preferencesRepository: PreferencesRepository,
	private val descriptionRepository: DescriptionRepository
) : ViewModel() {

	private val _selectedPair = MutableStateFlow<SelectedPairDetails?>(null)

	val uiState: StateFlow<CompatUiState> = combine(
		preferencesRepository.userPreferences,
		_selectedPair
	) { prefs, selectedPair ->
		val user = Person(sign = prefs.userSign, gender = prefs.userGender)
		val calculatedList = compatUseCase(user = user, targetGender = prefs.targetGender)

		CompatUiState(
			isLoading = false,
			userSign = prefs.userSign,
			userGender = prefs.userGender,
			targetGender = prefs.targetGender,
			results = calculatedList,
			selectedPair = selectedPair
		)
	}.stateIn(
		scope = viewModelScope,
		started = SharingStarted.WhileSubscribed(5_000),
		initialValue = CompatUiState(isLoading = true)
	)

	fun onUserSignChanged(newSign: ZodiacSign) {
		viewModelScope.launch {
			preferencesRepository.updateUserSign(newSign)
		}
	}

	fun onUserGenderChanged(newGender: Gender) {
		viewModelScope.launch {
			preferencesRepository.updateUserGender(newGender)
		}
	}

	fun onTargetGenderChanged(newTargetGender: Gender) {
		viewModelScope.launch {
			preferencesRepository.updateTargetGender(newTargetGender)
		}
	}

	fun onSignClicked(targetSign: ZodiacSign) {
		viewModelScope.launch {
			val current = uiState.value
			val user = Person(current.userSign, current.userGender)
			val target = Person(targetSign, current.targetGender)

			val level = compatUseCase(user, target)
			val description = descriptionRepository.getDescription(user, target)

			_selectedPair.value = SelectedPairDetails(
				targetSign = targetSign,
				level = level,
				description = description
			)
		}
	}

	// Вызывается при закрытии шторки свайпом или кнопкой
	fun onDismissBottomSheet() {
		_selectedPair.value = null
	}
}

data class SelectedPairDetails(
	val targetSign: ZodiacSign,
	val level: CompatLevel,
	val description: String
)