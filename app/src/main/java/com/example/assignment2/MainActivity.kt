package com.example.assignment2

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService

class MainActivity : AppCompatActivity() {

    val Channel_ID = "Channel_ID"
    val notificationID = 100




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()

        var notificationButton: Button = findViewById<Button>(R.id.Send_Notification)
        var moveButton = findViewById<Button>(R.id.Move)

        notificationButton.setOnClickListener{
            sendNotification()
        }
        
        moveButton.setOnClickListener({
            val intent:Intent = Intent(this,MainActivity2::class.java)
            intent.putExtra("SPECIAL","INACTIVE")
            startActivity(intent)
        })
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){
            val name = "Notification Title"
            val descriptionText = "Description of Notification"
            val importanceOfNotification = NotificationManager.IMPORTANCE_HIGH
            val channel:NotificationChannel =NotificationChannel(Channel_ID,name,importanceOfNotification).apply {
                description =descriptionText
            }
            val notificationManager:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        }
    private fun sendNotification (){
        val intent = Intent(this, MainActivity2::class.java).apply{
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        intent.putExtra("SPECIAL","ACTIVE")
        val pendingIntent:PendingIntent = PendingIntent.getActivity(this,0,intent,0)

        val builder = NotificationCompat.Builder(this,Channel_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Some Title")
                .setContentText("My Notification")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        with(NotificationManagerCompat.from(this)){
            notify(notificationID,builder.build())
        }

    }
}