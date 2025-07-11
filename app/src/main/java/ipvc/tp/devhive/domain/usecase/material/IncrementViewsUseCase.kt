package ipvc.tp.devhive.domain.usecase.material

import ipvc.tp.devhive.domain.repository.MaterialRepository
import javax.inject.Inject

class IncrementViewsUseCase @Inject constructor(
    private val materialRepository: MaterialRepository
) {
    suspend operator fun invoke(materialId: String): Result<Boolean> {
        return try {
            materialRepository.incrementViews(materialId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}