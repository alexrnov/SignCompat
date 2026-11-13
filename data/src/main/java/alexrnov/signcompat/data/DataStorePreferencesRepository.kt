package alexrnov.signcompat.data

import alexrnov.signcompat.domain.PreferencesRepository
import alexrnov.signcompat.domain.usecase.Gender
import alexrnov.signcompat.domain.usecase.UserPreferences
import alexrnov.signcompat.domain.usecase.ZodiacSign
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

// Автоматически восстанавливаем файл, если он оказался повреждён на диске
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
	name = "user_compat_prefs",
	corruptionHandler = ReplaceFileCorruptionHandler(
		produceNewData = { emptyPreferences() }
	)
)

class DataStorePreferencesRepository(
	private val context: Context
) : PreferencesRepository {

	private object Keys {
		val USER_SIGN = stringPreferencesKey("user_sign")
		val USER_GENDER = stringPreferencesKey("user_gender")
		val TARGET_GENDER = stringPreferencesKey("target_gender")
	}

	override val userPreferences: Flow<UserPreferences> = context.dataStore.data
		.catch { exception ->
			// Если произошла ошибка ввода-вывода при чтении — отдаём пустые настройки,
			// а остальные критические исключения пробрасываем дальше
			if (exception is IOException) {
				emit(emptyPreferences())
			} else {
				throw exception
			}
		}
		.map { prefs ->
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
		runCatching {
			context.dataStore.edit { prefs ->
				prefs[Keys.USER_SIGN] = sign.name
			}
		}
	}

	override suspend fun updateUserGender(gender: Gender) {
		runCatching {
			context.dataStore.edit { prefs ->
				prefs[Keys.USER_GENDER] = gender.name
			}
		}
	}

	override suspend fun updateTargetGender(gender: Gender) {
		runCatching {
			context.dataStore.edit { prefs ->
				prefs[Keys.TARGET_GENDER] = gender.name
			}
		}
	}
}