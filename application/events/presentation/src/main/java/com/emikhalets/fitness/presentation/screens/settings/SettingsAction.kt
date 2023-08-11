package com.emikhalets.simpleevents.presentation.screens.settings

import android.net.Uri
import com.emikhalets.simpleevents.domain.entity.AlarmEntity
import com.emikhalets.ui.UiAction

sealed class SettingsAction : UiAction {
    class ExportClick(val uri: Uri) : SettingsAction()
    class ImportClick(val uri: Uri) : SettingsAction()
    class SwitchAlarm(val entity: AlarmEntity, val enabled: Boolean) : SettingsAction()
    class UpdateAlarm(val entity: AlarmEntity) : SettingsAction()
    class DeleteAlarm(val entity: AlarmEntity) : SettingsAction()
    object GetAlarms : SettingsAction()
    object ApplyError : SettingsAction()
    object ApplyImported : SettingsAction()
    object ApplyExported : SettingsAction()
}
