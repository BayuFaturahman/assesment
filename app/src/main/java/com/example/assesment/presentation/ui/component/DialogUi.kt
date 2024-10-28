package com.example.assesment.presentation.ui.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.assesment.presentation.ui.theme.black
import com.example.assesment.presentation.ui.theme.light1
import com.example.assesment.presentation.ui.theme.primary

@Composable
fun DialogUi(
    dialogTitle: String,
    isDialogSingel: Boolean,

    dialogSubTitle: String,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    AlertDialog(
        containerColor = light1,
        modifier = Modifier.fillMaxWidth(0.92f),
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = true,
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        ),
        shape = RoundedCornerShape(20.dp),
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text(text = "Yes", fontSize = 16.sp ,color = primary)
            }
        },
        dismissButton = {
        if(!isDialogSingel)
            {TextButton(
                onClick = { onDismissRequest() }) {
                Text(text = "Cancel",fontSize = 16.sp, color = primary)
            }}
        },
        title = {
            Text(text = dialogTitle, fontSize = 18.sp,color = primary, fontWeight = FontWeight.Bold)
        },
        text = {
            Text(text = dialogSubTitle,color = primary)
        })
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFCFC)
@Composable
fun DialogPreview() {
    DialogUi(dialogSubTitle = "Log out", dialogTitle = "Are you sure Logout?", onConfirmation = {
        Log.d("TEST","KSNDKNS")

    }, isDialogSingel = false,onDismissRequest = {})


}
