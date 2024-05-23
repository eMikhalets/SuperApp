package com.emikhalets.superapp.domain.salary.use_case

import com.emikhalets.superapp.domain.salary.model.SalaryModel
import com.emikhalets.superapp.domain.salary.repository.SalaryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSalariesUseCase @Inject constructor(
    private val salaryRepository: SalaryRepository,
) {

    operator fun invoke(): Flow<List<SalaryModel>> {
        return salaryRepository.getSalaries()
    }
}