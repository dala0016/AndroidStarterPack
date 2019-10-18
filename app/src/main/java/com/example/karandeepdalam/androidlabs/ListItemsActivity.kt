package com.example.karandeepdalam.androidlabs

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import kotlinx.android.synthetic.main.activity_list_items.*

class ListItemsActivity : Activity() {

    val ACTIVITY_NAME = "StartActivity";
    var REQUEST_IMAGE_CAPTURE = 1
    var button1 = null as ImageButton?;
    var messages = ArrayList<String>()  //automatically grows


    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_items)

        button1 = findViewById< ImageButton >(R.id.image_camera)

       // var sw = findViewById<Switch>(R.id.switch1)
        //var cb = findViewById< CheckBox >(R.id.checkBox1)

        //add a click handler:
        button1?.setOnClickListener {

            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(packageManager) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }

            // create an Intent to go to the ListItemsActivity:
            //val newActivity = Intent( this, ListItemsActivity::class.java);

            //transition to the new page:
            //startActivityForResult(newActivity, 50)

            }
        var sw1 = findViewById< Switch >(R.id.switch1)
        sw1.setOnCheckedChangeListener({ e, f ->

            val builder = AlertDialog.Builder(this)

            builder.setMessage(R.string.dialog_message)
                    .setTitle(R.string.dialog_title)
                    .setPositiveButton(R.string.yes,
                            { dialog, id ->

                        val resultIntent = Intent()
                        val toastMessage = getString(R.string.toastMessage)
                        resultIntent.putExtra("Response", toastMessage)
                        setResult(Activity.RESULT_OK, resultIntent)
                        finish()
                            })
                    .setNegativeButton(R.string.no,
                            {dialog, id ->

                            })

                    .show()
        })

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data.extras.get("data") as Bitmap
            button1?.setImageBitmap(imageBitmap)

        }
    }
    private fun setOnCheckedChange() {
        Log.i(ACTIVITY_NAME, "Testing Switch");
        var switchButton = findViewById<Switch>(R.id.switch1)
        if(switchButton?.isChecked == true)
        {
            val text = "Switch is On"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(this, text, duration)
            toast.show()
        }
        else
        {
            val text = "Switch is OFF"
            val duration = Toast.LENGTH_LONG
            val toast = Toast.makeText(this, text, duration)
            toast.show()
        }
    }
    private fun onCheckedChanged () {
        Log.i(ACTIVITY_NAME, "Testing Check");

        val builder = AlertDialog.Builder(this)

        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title)
                .setPositiveButton(R.string.yes,
                        { dialog, id ->
                            /*
                    val resultIntent = Intent()
                    val toastMessage = getString(R.string.toastMessage)
                    resultIntent.putExtra("Response", toastMessage)
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish() */
                })
                .setNegativeButton(R.string.no,
                        {dialog, id ->

                        })

                .show()
        /*


        val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.dialog_fire_missiles)
                    .setPositiveButton(R.string.fire,
                            DialogInterface.OnClickListener { dialog, id ->
                                // FIRE ZE MISSILES!
                            })
                    .setNegativeButton(R.string.cancel,
                            DialogInterface.OnClickListener { dialog, id ->
                                // User cancelled the dialog
                            })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")


         */







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




