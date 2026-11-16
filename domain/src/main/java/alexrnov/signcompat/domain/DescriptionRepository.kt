package alexrnov.signcompat.domain

import alexrnov.signcompat.domain.usecase.Person

interface DescriptionRepository {
	suspend fun getDescription(first: Person, second: Person): String
}