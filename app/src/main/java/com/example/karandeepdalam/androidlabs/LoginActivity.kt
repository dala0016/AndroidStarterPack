package com.example.karandeepdalam.androidlabs

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : Activity() {

    val ACTIVITY_NAME = "StartActivity";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //get the EditText field
        val editText = findViewById<EditText>(R.id.logInText)

        //open the sharedpreferences file called SavedData
        val prefs = getSharedPreferences("SavedData", Context.MODE_PRIVATE)

        //Look for the value reserved under the name UserInput. If not there, return Default answer
        val userString = prefs.getString("DefaultEmail", "email@domin.com")

        //A logging message at the error level of priority
        Log.e("In MainActivity", "string found is:" + userString)

        //put the string found from the user preferences into the EditText field
        editText.setText(userString)


        var button1 = findViewById(R.id.logInButton) as? Button

        //add a click handler:
        button1?.setOnClickListener {

            // create an Intent to go to the StartActivity:
            val newActivity = Intent( this, StartActivity::class.java);

            //Get what the user typed in the editText:
            val typedString = editText.getText().toString()


            //get an editor object to saved to SharedPreferences:
            val prefs = prefs.edit()

            //Under the name UserInput, save what the user typed:
            prefs.putString("DefaultEmail", typedString)



            prefs.commit()//writes a file

            //Put the user string under the reservation "UserName" to send to the next page
            newActivity.putExtra("UserName: ", typedString)

            //transition to the new page:
            startActivityForResult(newActivity, 100)
            }
    }
    override fun onResume() {
        super.onResume()
        Log.i(ACTIVITY_NAME, "In onCreate()");
    }

    override fun onStart() {
        super.onStart()
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    override fun onPause() {
        super.onPause()
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    override fun onStop() {
        super.onStop()
        Log.i(ACTIVITY_NAME, "In onStop()");

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}

