package com.example.cryptomatthew.ui.notifications

import android.Manifest
import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cryptomatthew.ui.HomeViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel: HomeViewModel = hiltViewModel()

    val time by viewModel.notificationsTime.collectAsStateWithLifecycle("12:00")
    val notificationsEnabled by
    viewModel.notificationEnabled.collectAsStateWithLifecycle(false)


    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        NotificationSwitchPanel(
            notificationsEnabled,
            { viewModel.onNotificationEnabledChange(it) },
            Modifier
                .padding(10.dp)
                .fillMaxWidth()
        )
        AnimatedVisibility(notificationsEnabled) {

            TimePickerPanel(
                { viewModel.addNotificationScheduleTime(it) },
                {},
                time,
                modifier.fillMaxWidth()
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerPanel(
    onConfirm: (time: TimePickerState) -> Unit,
    onDismiss: () -> Unit,
    time: String,
    modifier: Modifier = Modifier
) {
    var showTimePickerDialog by remember { mutableStateOf(false) }



    if (showTimePickerDialog) {
        DialWithDialogExample(
            onConfirm = {
                showTimePickerDialog = false
                onConfirm(it)
            },
            onDismiss = {
                showTimePickerDialog = false
                onDismiss()
            }
        )
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                "Время получения уведомления",
                modifier = Modifier
                    .padding(bottom = 8.dp)

            )
            Text(time, modifier = Modifier.clickable { showTimePickerDialog = true })

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