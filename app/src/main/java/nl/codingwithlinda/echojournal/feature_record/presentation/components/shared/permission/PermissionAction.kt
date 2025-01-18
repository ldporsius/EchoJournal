package nl.codingwithlinda.echojournal.feature_record.presentation.components.shared.permission

interface PermissionAction {
    data object OpenDialog : PermissionAction
    data object CloseDialog : PermissionAction
}