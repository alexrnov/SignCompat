package alexrnov.signcompat

import alexrnov.signcompat.domain.domainModule
import alexrnov.signcompat.presentation.presentationModule
import android.app.Application
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext

class SingCompatApplication : Application() {
	override fun onCreate() {
		super.onCreate()

		startKoin {
			androidContext(this@SingCompatApplication)
			modules(
				presentationModule,
				domainModule
			)
		}
	}
}