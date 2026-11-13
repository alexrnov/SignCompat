package alexrnov.signcompat.domain

import alexrnov.signcompat.domain.usecase.Gender
import alexrnov.signcompat.domain.usecase.UserPreferences
import alexrnov.signcompat.domain.usecase.ZodiacSign
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
	val userPreferences: Flow<UserPreferences>
	suspend fun updateUserSign(sign: ZodiacSign)
	suspend fun updateUserGender(gender: Gender)
	suspend fun updateTargetGender(gender: Gender)
}