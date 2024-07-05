package com.emikhalets.superapp.feature.salary.domain.use_case

import com.emikhalets.superapp.feature.salary.data.SalaryRepository
import com.emikhalets.superapp.feature.salary.domain.SalaryModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSalariesUseCase @Inject constructor(
    private val salaryRepository: SalaryRepository,
) {

    operator fun invoke(): Flow<List<SalaryModel>> {
        return salaryRepository.getSalaries()
    }
}