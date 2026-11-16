package alexrnov.signcompat.data

import alexrnov.signcompat.domain.DescriptionRepository
import alexrnov.signcompat.domain.usecase.Person
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AssetDescriptionRepository(
	private val context: Context
) : DescriptionRepository {

	// Кэшируем распарсенный JSON в памяти после первого чтения
	private var cachedDescriptions: Map<String, String>? = null

	override suspend fun getDescription(first: Person, second: Person): String = withContext(Dispatchers.IO) {
		val map = cachedDescriptions ?: loadFromAssets().also { cachedDescriptions = it }

		// Формируем прямой ключ (например, MALE_PISCES_FEMALE_SCORPIO)
		val directKey = "${first.gender.name}_${first.sign.name}_${second.gender.name}_${second.sign.name}"

		// Для однополых пар проверяем также обратный порядок ключей
		val reverseKey = "${second.gender.name}_${second.sign.name}_${first.gender.name}_${first.sign.name}"

		map[directKey]
			?: map[reverseKey]
			?: "Подробное описание для этой пары скоро появится."
	}

	private fun loadFromAssets(): Map<String, String> {
		return runCatching {
			val jsonString = context.assets.open("compatibility_descriptions.json")
				.bufferedReader()
				.use { it.readText() }

			val jsonObject = org.json.JSONObject(jsonString)
			val result = mutableMapOf<String, String>()
			jsonObject.keys().forEach { key ->
				result[key] = jsonObject.getString(key)
			}
			result
		}.getOrDefault(emptyMap())
	}
}