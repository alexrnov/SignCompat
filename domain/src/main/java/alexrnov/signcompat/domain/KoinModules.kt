package alexrnov.signcompat.domain

import alexrnov.signcompat.domain.usecase.CompatUseCase
import org.koin.dsl.module

val domainModule = module {
	factory { CompatUseCase() }
}