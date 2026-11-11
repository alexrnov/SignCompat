package alexrnov.signcompat.presentation

import alexrnov.signcompat.domain.usecase.CompatLevel
import alexrnov.signcompat.domain.usecase.CompatUseCase
import alexrnov.signcompat.domain.usecase.Person
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CompatViewModel(
	private val compatUseCase: CompatUseCase
) : ViewModel() {

	private val _compatLevel = MutableStateFlow<CompatLevel?>(null)
	val compatLevel = _compatLevel.asStateFlow()

	fun getCompatibility(first: Person, second: Person)  {
		val compatLevel =compatUseCase.getCompatibility(first, second)
		_compatLevel.value = compatLevel
	}
}