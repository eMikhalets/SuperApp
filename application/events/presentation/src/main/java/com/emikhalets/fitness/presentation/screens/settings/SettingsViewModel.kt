package com.emikhalets.simpleevents.presentation.screens.settings

import android.net.Uri
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.domain.entity.AlarmEntity
import com.emikhalets.simpleevents.domain.usecase.alarms.AddAlarmUseCase
import com.emikhalets.events.domain.usecase.alarms.AlarmsDeleteUseCase
import com.emikhalets.events.domain.usecase.alarms.AlarmsGetUseCase
import com.emikhalets.events.domain.usecase.backups.ExportEventsUseCase
import com.emikhalets.events.domain.usecase.backups.ImportEventsUseCase
import com.emikhalets.events.domain.usecase.events.EventsGetUseCase
import com.emikhalets.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getAlarmsUseCase: AlarmsGetUseCase,
    private val addAlarmsUseCase: AddAlarmUseCase,
    private val deleteAlarmUseCase: AlarmsDeleteUseCase,
    private val importEventsUseCase: ImportEventsUseCase,
    private val exportEventsUseCase: ExportEventsUseCase,
    private val getEventsUseCase: EventsGetUseCase,
) : BaseViewModel<SettingsState, SettingsAction>() {

    override fun createInitialState(): SettingsState = SettingsState()

    override fun handleEvent(action: SettingsAction) {
    }

    fun resetError() = setState { it.copy(error = null) }
    fun resetImported() = setState { it.copy(imported = false) }
    fun resetExported() = setState { it.copy(exported = false) }

    fun loadAllNotificationsGlobal() {
        launchIO {
            setState { it.copy(loading = true) }
            getAlarmsUseCase()
                .onSuccess { result ->
                    result.collectLatest { list ->
                        setState { it.copy(loading = false, eventAlarms = list) }
                    }
                }
                .onFailure { error ->
                    val uiError = UiString.Message(error.message)
                    setState { it.copy(loading = false, error = uiError) }
                }
        }
    }

    fun updateNotificationGlobal(notification: AlarmEntity, enabled: Boolean) {
        launchIO {
            val entity = notification.copy(enabled = enabled)
            addAlarmsUseCase(entity)
                .onSuccess {
                    loadAllNotificationsGlobal()
                }
                .onFailure { error ->
                    val uiError = UiString.Message(error.message)
                    setState { it.copy(loading = false, error = uiError) }
                }
        }
    }

    fun importEvents(uri: Uri) {
        launchIO {
            importEventsUseCase(uri)
                .onSuccess { result ->
                    setState {
                        if (result.isEmpty()) {
                            val uiError = UiString.Resource(R.string.error_import_events)
                            it.copy(error = uiError)
                        } else {
                            it.copy(imported = true)
                        }
                    }
                }
                .onFailure { error ->
                    val uiError = UiString.Message(error.message)
                    setState { it.copy(loading = false, error = uiError) }
                }
        }
    }

    fun exportEvents(uri: Uri) {
        launchIO {
            getEventsUseCase()
                .onSuccess { eventsResult ->
//                    exportEventsUseCase(uri, eventsResult)
//                        .onSuccess {
//                            setState { it.copy(exported = true) }
//                        }
//                        .onFailure { error ->
//                            val uiError = UiString.Message(error.message)
//                            setState { it.copy(loading = false, error = uiError) }
//                        }
                }
                .onFailure { error ->
                    val uiError = UiString.Message(error.message)
                    setState { it.copy(loading = false, error = uiError) }
                }
        }
    }

    fun updateNotification(notification: AlarmEntity) {
        launchIO {
            addAlarmsUseCase(notification)
                .onFailure { error ->
                    val uiError = UiString.Message(error.message)
                    setState { it.copy(loading = false, error = uiError) }
                }
        }
    }

    fun deleteNotification(notification: AlarmEntity) {
        launchIO {
            deleteAlarmUseCase(notification)
                .onFailure { error ->
                    val uiError = UiString.Message(error.message)
                    setState { it.copy(loading = false, error = uiError) }
                }
        }
    }
}
