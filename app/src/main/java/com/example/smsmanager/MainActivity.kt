package com.example.smsmanager

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) !=PackageManager.PERMISSION_GRANTED)

        {
          ActivityCompat.requestPermissions(this,
              arrayOf(Manifest.permission.RECEIVE_SMS,Manifest.permission.SEND_SMS),
              111)
        }

          else
              receiveMessage()

        val editText = findViewById<EditText>(R.id.editText)
        val editText2 = findViewById<EditText>(R.id.editText)
        val Button : Button= findViewById<Button>(R.id.button)

        Button.setOnClickListener{
            var sms = SmsManager.getDefault()
            //pass args
            sms.sendTextMessage(editText.text.toString(),"Phone Number",editText2 .text.toString(),null,null)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode===111 && grantResults[0]== PackageManager.PERMISSION_GRANTED)

            //creating the function
            receiveMessage()
    }

    private fun receiveMessage() {
        //creating a broadcastreceiver
        var br= object : BroadcastReceiver(){
            override fun onReceive(p0: Context?, p1: Intent?) {
                if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.KITKAT){
                   for (sms:SmsMessage in Telephony.Sms.Intents.getMessagesFromIntent(p1)){
                      val editText = findViewById<EditText>(R.id.editText)
                      val editText2 = findViewById<EditText>(R.id.editText)
                       editText.setText(sms.originatingAddress)
                       editText2.setText(sms.displayMessageBody)
                   }
                }
            }
        }
//listening to events
        registerReceiver(br, IntentFilter("android.provider.telephony.SMS_RECEIVED"))
    }
}













