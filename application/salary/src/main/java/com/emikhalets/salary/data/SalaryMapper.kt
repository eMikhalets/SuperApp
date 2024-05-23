package com.emikhalets.salary.data

import com.emikhalets.core.database.salary.table_salaries.SalaryDb
import com.emikhalets.salary.domain.model.SalaryModel
import com.emikhalets.salary.domain.model.SalaryType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object SalaryMapper {

    fun toDb(model: SalaryModel): SalaryDb {
        return SalaryDb(
            id = model.id,
            value = model.value,
            timestamp = model.timestamp,
            type = model.type.toString(),
        )
    }

    fun toDbList(list: List<SalaryModel>): List<SalaryDb> {
        return list.map { toDb(it) }
    }

    fun toDbFlow(flow: Flow<List<SalaryModel>>): Flow<List<SalaryDb>> {
        return flow.map { toDbList(it) }
    }

    fun toModel(model: SalaryDb): SalaryModel {
        return SalaryModel(
            id = model.id,
            value = model.value,
            timestamp = model.timestamp,
            type = SalaryType.valueOf(model.type),
        )
    }

    fun toModelList(list: List<SalaryDb>): List<SalaryModel> {
        return list.map { toModel(it) }
    }

    fun toModelFlow(flow: Flow<List<SalaryDb>>): Flow<List<SalaryModel>> {
        return flow.map { toModelList(it) }
    }
}