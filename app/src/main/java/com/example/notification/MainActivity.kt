package com.example.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.RemoteInput
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.IconCompat
import com.example.notification.ui.theme.NotificationTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotificationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val KEY_TEXT_REPLY = "key_text_reply"
                    var replyLabel: String = "Reply Label"
                    var remoteInput: RemoteInput = RemoteInput.Builder(KEY_TEXT_REPLY).run {
                        setLabel(replyLabel)
                        build()
                    }
//                    val replyPendingIntent:PendingIntent = PendingIntent.getBroadcast(applicationContext,)
                    var id = 0

                    val intent = Intent(this, ViewNotification::class.java).apply {

                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                    }

                    val pendingIntent: PendingIntent =
                        PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

                    val notification = NotificationCompat.Builder(this, "channel_id")
                        .setSmallIcon(R.drawable.img)
                        .setContentTitle("Hello Title")
                        .setContentText("Hello world")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .build()

//                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.0){}
                    val name = "channel_name"
                    val descriptionText = "channel_description"
                    val importance = NotificationManager.IMPORTANCE_DEFAULT
                    val channel = NotificationChannel("channel_id", name, importance).apply {
                        description = descriptionText
                    }
                    Column(modifier = Modifier.fillMaxSize()) {
                        Button(onClick = {
                            id += 1
                            val notificationManager: NotificationManager =
                                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                            notificationManager.notify(id, notification)
                            notificationManager.createNotificationChannel(channel)
                        }) {

                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotificationTheme {

    }
}