package com.example.karandeepdalam.androidlabs

import android.app.LauncherActivity
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_test_toolbar.view.*
import android.support.design.widget.Snackbar
import android.support.v4.app.NotificationCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AlertDialog
import android.widget.EditText

class TestToolbar : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{

    var response = "You clicked on iWatch"
    lateinit  var snackButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_toolbar)

        var toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar( toolbar)

        snackButton = findViewById<Button>(R.id.snackbar_button)
        snackButton.setOnClickListener {
            Snackbar.make(snackButton, "Message to show", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {

            R.id.chat_item -> {

                var mBuilder = NotificationCompat.Builder(this, "Channel_name")
                        .setSmallIcon(R.drawable.jeep)
                        .setAutoCancel(true)
                        .setContentTitle("Go to chat")
                        .setContentText("Lets Chat soon")

                var resultIntent = Intent(this, ChatWindow::class.java)
                var resultPendingIntent = PendingIntent.getActivity( this, 0, resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);mBuilder.setContentIntent(resultPendingIntent);
                val mNotificationId = 1
                val mNotifyMgr = getSystemService(NOTIFICATION_SERVICE) as NotificationManager;
                mNotifyMgr.notify (mNotificationId, mBuilder.build());
            }

            R.id.list_item -> {

                var mBuilder = NotificationCompat.Builder(this, "Channel_name")
                        .setSmallIcon(R.drawable.iwatch)
                        .setAutoCancel(true)
                        .setContentTitle("Go to list")
                        .setContentText("view list items")

                var resultIntent = Intent(this, LauncherActivity.ListItem::class.java)
                var resultPendingIntent = PendingIntent.getActivity( this, 0, resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);mBuilder.setContentIntent(resultPendingIntent);
                val mNotificationId = 2
                val mNotifyMgr = getSystemService(NOTIFICATION_SERVICE) as NotificationManager;
                mNotifyMgr.notify (mNotificationId, mBuilder.build());

            }
            R.id.contact_item -> {

                var mBuilder = NotificationCompat.Builder(this, "Channel_name")
                        .setSmallIcon(R.drawable.bean)
                        .setAutoCancel(true)
                        .setContentTitle("Send Stuff")
                        .setContentText("Pick Which One")

                val emailIntent = Intent(Intent.ACTION_SENDTO)
                var resultPendingIntent = PendingIntent.getActivity( this, 0, emailIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.addAction(R.drawable.row_even, "Email", resultPendingIntent);

                val textIntent = Intent(Intent.ACTION_SEND)
                resultPendingIntent = PendingIntent.getActivity( this, 0, textIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.addAction(R.drawable.row_odd, "Text", resultPendingIntent);

                val mNotificationId = 3
                val mNotifyMgr = getSystemService(NOTIFICATION_SERVICE) as NotificationManager;
                mNotifyMgr.notify (mNotificationId, mBuilder.build());
            }
        }

        var drawer = findViewById<DrawerLayout>(R.id.drawer_id)
        drawer.closeDrawer(GravityCompat.START) //close drawer menu to the left
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_one ->{
                Snackbar.make(snackButton, response, Snackbar.LENGTH_SHORT).show()
            }
            R.id.action_two ->{

                var dialogStuff = layoutInflater.inflate(R.layout.dialog_stuff, null)
                var builder = AlertDialog.Builder(this);
                builder.setView(dialogStuff)        //put middle of screen
                builder.setTitle("Do you want to go back?")
                // Add the buttons
                builder.setPositiveButton("Ok", {dialog, id ->
                    // User clicked OK button
                    finish()

                })
                builder.setNegativeButton("Cancel",  { dialog, id ->
                    // User cancelled the dialog

                })
                // Create the AlertDialog
                var dialog = builder.create()
                dialog.show();

            }
            R.id.action_three ->{

                var dialogStuff = layoutInflater.inflate(R.layout.dialog_stuff, null)
                var editText = dialogStuff.findViewById<EditText>(R.id.new_message)

                var builder = AlertDialog.Builder(this);
                builder.setView(dialogStuff)        //put middle of screen
                builder.setTitle("Do you want to go back?")
                // Add the buttons
                builder.setPositiveButton("Ok", {dialog, id ->
                    // User clicked OK button
                    response = editText.text.toString()

                })
                builder.setNegativeButton("Cancel",  { dialog, id ->
                    // User cancelled the dialog

                })
                // Create the AlertDialog
                var dialog = builder.create()
                dialog.show();
            }
            R.id.action_four ->{
                Toast.makeText(this,"Version 1.0 by Karandeep Dalam", Toast.LENGTH_LONG).show()
            }
        }

        return true
    }
}
