package com.example.clinicacare.presentation.dashboard.dashboard_screen.dash_settings.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.clinicacare.core.presentation.ResetPasswordViewModel

@Composable
fun SettingIcon(
    title: String,
    value: String? = null,
    trailingIcon: ImageVector? = null,
    onClick: (() -> Unit)? = null,
    isResetPass: Boolean = false,
    isLogout: Boolean = false
) {

    val background = if (isResetPass) {
        MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f)
    } else if (isLogout) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
    }

    val textColor = if (isResetPass || isLogout) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onPrimary
    }

    val iconColor = if (isResetPass || isLogout) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onPrimary
    }

    TextButton(
        onClick = {
            if (onClick != null) {
                onClick()
            }
        },
        modifier = Modifier
            .padding(
                horizontal = 16.dp,
                vertical = 5.dp
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = background,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(
            horizontal = 9.dp,
            vertical = 20.dp
        )
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(2f),
            textAlign = TextAlign.Start,
            color = textColor
        )

        value?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyLarge,
                color = textColor
            )
        }

        trailingIcon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                tint = iconColor
            )
        }
    }
}