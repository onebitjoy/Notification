package com.abhishek.notification

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.abhishek.notification.ui.theme.NotificationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotificationTheme {

                val context = LocalContext.current

                // we have checked if for any android version >= OREO we hv permission to send notification,
                // the permission is not needed

                var hasNotificationPermission by remember {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

                        mutableStateOf(

                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.POST_NOTIFICATIONS
                            ) == PackageManager.PERMISSION_GRANTED

                        )

                    } else mutableStateOf(true)
                }

                val permissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        hasNotificationPermission = isGranted
                    }
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    // permission button
                    Button(

                        onClick = {
                            permissionLauncher.launch(
                                Manifest.permission.POST_NOTIFICATIONS
                            )
                        },

                        enabled = !hasNotificationPermission,

                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = Color.White,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.Black
                        )

                    ) {
                        Text(text = "Request Permission")
                    }

//                    --------------

                    //notification button
                    Button(onClick = {
                        if (hasNotificationPermission) showNotification()
                        else {
                            Toast.makeText(
                                applicationContext,
                                " Please provide notification permission",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                    {
                        Text(text = "Show notification")
                    }

                }
            }
        }
    }

    private fun showNotification() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // building notification
        val notification =
            NotificationCompat.Builder(applicationContext, "destiny_notification_channel")
                .setContentText("You are notified!")
                .setContentTitle("Notification Title")
                .setSmallIcon(androidx.core.R.drawable.notification_bg_normal)
                .build()

        //notifying user
        notificationManager.notify(1, notification)
    }
}

/* NOTES */
/*
* Notifications need to have permission from the user, which we can get from user-permission(POST_NOTIFICATION)
* Different channels need to be created for android versions OREO and above. For that we need to create an application
* create a channel using NotificationChannel( id:String? , name : String , importance : Int), then we need
* to get an instance of NotificationManager using getSystemService(Context.NOTIFICATION_SERVICE), now we need
* to createNotificationChannel( channel ) to create it, register this application class(MyApp) in Manifest.
*
*
* */