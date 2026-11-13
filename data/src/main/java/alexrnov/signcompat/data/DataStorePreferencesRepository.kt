package alexrnov.signcompat.data

import alexrnov.signcompat.domain.PreferencesRepository
import alexrnov.signcompat.domain.usecase.Gender
import alexrnov.signcompat.domain.usecase.UserPreferences
import alexrnov.signcompat.domain.usecase.ZodiacSign
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Создаём делегат DataStore на верхнем уровне файла
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_compat_prefs")

class DataStorePreferencesRepository(
	private val context: Context
) : PreferencesRepository {

	private object Keys {
		val USER_SIGN = stringPreferencesKey("user_sign")
		val USER_GENDER = stringPreferencesKey("user_gender")
		val TARGET_GENDER = stringPreferencesKey("target_gender")
	}

	override val userPreferences: Flow<UserPreferences> = context.dataStore.data.map { prefs ->
		val userSign = prefs[Keys.USER_SIGN]
			?.let { runCatching { ZodiacSign.valueOf(it) }.getOrNull() }
			?: ZodiacSign.ARIES

		val userGender = prefs[Keys.USER_GENDER]
			?.let { runCatching { Gender.valueOf(it) }.getOrNull() }
			?: Gender.MALE

		val targetGender = prefs[Keys.TARGET_GENDER]
			?.let { runCatching { Gender.valueOf(it) }.getOrNull() }
			?: Gender.FEMALE

		UserPreferences(
			userSign = userSign,
			userGender = userGender,
			targetGender = targetGender
		)
	}

	override suspend fun updateUserSign(sign: ZodiacSign) {
		context.dataStore.edit { prefs ->
			prefs[Keys.USER_SIGN] = sign.name
		}
	}

	override suspend fun updateUserGender(gender: Gender) {
		context.dataStore.edit { prefs ->
			prefs[Keys.USER_GENDER] = gender.name
		}
	}

	override suspend fun updateTargetGender(gender: Gender) {
		context.dataStore.edit { prefs ->
			prefs[Keys.TARGET_GENDER] = gender.name
		}
	}
}