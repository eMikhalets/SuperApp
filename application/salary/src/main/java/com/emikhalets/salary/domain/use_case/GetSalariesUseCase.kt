package com.emikhalets.salary.domain.use_case

import com.emikhalets.salary.data.Repository
import com.emikhalets.salary.domain.model.SalaryModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSalariesUseCase @Inject constructor(
    private val repository: Repository,
) {

    operator fun invoke(): Flow<List<SalaryModel>> {
        return repository.getSalaries()
    }
}