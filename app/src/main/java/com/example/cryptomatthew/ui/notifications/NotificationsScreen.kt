package com.example.cryptomatthew.ui.notifications

import android.Manifest
import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    notificationsEnabled: Boolean,
    onConfirm: (time: TimePickerState) -> Unit,
    onDismiss: () -> Unit,
    onEnableNotificationChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        NotificationSwitchPanel(
            notificationsEnabled,
            onEnableNotificationChange,
            Modifier
                .padding(10.dp)
                .fillMaxWidth()
        )
        AnimatedVisibility(notificationsEnabled) {

            TimePickerPanel(onConfirm, onDismiss, modifier.fillMaxWidth())

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerPanel(
    onConfirm: (time: TimePickerState) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Text(
            "Выберите время получения уведомления",
            modifier = Modifier.padding(bottom = 8.dp)
        )
        TimeInput(
            state = timePickerState,
        )
        Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {

            Button(onClick = onDismiss) {
                Text("Dismiss picker")
            }
            Button(onClick = { onConfirm(timePickerState) }) {
                Text("Confirm selection")
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NotificationSwitchPanel(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {


    Row(
        modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        val permissionIsNeeded = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
        val notificationPermissionState = if (permissionIsNeeded) {
            rememberPermissionState(
                Manifest.permission.POST_NOTIFICATIONS,
                onPermissionResult = {
                    if (it) onCheckedChange(!checked)
                }
            )
        } else null

        Text("Включить уведомления о курсе")
        Switch(
            checked = checked,
            onCheckedChange = {
                if (notificationPermissionState != null) {
                    if (notificationPermissionState.status.isGranted) {
                        onCheckedChange(it)
                    } else if (!notificationPermissionState.status.isGranted || notificationPermissionState.status.shouldShowRationale) {
                        notificationPermissionState.launchPermissionRequest()
                    }
                } else {
                    onCheckedChange(it)
                }

            }
        )

    }
}