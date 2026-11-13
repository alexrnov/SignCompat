package alexrnov.signcompat.data

import alexrnov.signcompat.domain.PreferencesRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
	// Используем single, так как DataStore должен существовать в единственном экземпляре
	single<PreferencesRepository> { DataStorePreferencesRepository(androidContext()) }
}