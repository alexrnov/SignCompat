package alexrnov.signcompat.presentation

import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel

val presentationModule = module {
	viewModel { CompatViewModel(compatUseCase = get()) }
}