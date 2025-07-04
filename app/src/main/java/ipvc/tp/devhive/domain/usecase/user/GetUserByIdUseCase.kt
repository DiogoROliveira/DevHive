package ipvc.tp.devhive.domain.usecase.user

import ipvc.tp.devhive.domain.model.User
import ipvc.tp.devhive.domain.repository.UserRepository
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    private val repository: UserRepository
)
{
    suspend operator fun invoke(userId: String): User? {
        return repository.getUserById(userId)
    }
}