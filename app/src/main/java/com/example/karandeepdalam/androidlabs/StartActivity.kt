package com.example.karandeepdalam.androidlabs

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button

class StartActivity : Activity() {

    val ACTIVITY_NAME = "StartActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        var button1 = findViewById(R.id.button) as? Button
        var button2 = findViewById(R.id.Chatbutton) as? Button
        var button3 = findViewById(R.id.WeatherForecast)as? Button

        //add a click handler:
        button1?.setOnClickListener {

            // create an Intent to go to the ListItemsActivity:
            val newActivity = Intent( this, ListItemsActivity::class.java)


            //transition to the new page:
            startActivityForResult(newActivity, 50)
        }

        button2?.setOnClickListener {

            Log.i(ACTIVITY_NAME, "User clicked Start Chat")

            // create an Intent to go to the ChatWindow:
            val newActivity = Intent( this, ChatWindow::class.java)

            //transition to the new page:
            startActivityForResult(newActivity, 10)
        }

        button3?.setOnClickListener {

            Log.i(ACTIVITY_NAME, "User clicked Weather Forecast")

            // create an Intent to go to the ChatWindow:
            val newActivity = Intent( this, WeatherForecast::class.java)

            //transition to the new page:
            startActivityForResult(newActivity, 200)
        }
    }

    override fun onActivityResult(requestCode: Int, responseCode: Int, data: Intent?) {
        if (requestCode == 50) {
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult")
        }
    }


    override fun onResume() {
        super.onResume()
        Log.i(ACTIVITY_NAME, "In onCreate()")
    }

    override fun onStart() {
        super.onStart()
        Log.i(ACTIVITY_NAME, "In onStart()")
    }

    override fun onPause() {
        super.onPause()
        Log.i(ACTIVITY_NAME, "In onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.i(ACTIVITY_NAME, "In onStop()")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(ACTIVITY_NAME, "In onDestroy()")
    }
}
