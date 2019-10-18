package com.example.karandeepdalam.androidlabs

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.*

class ChatWindow : Activity() {

    val CREATION_STATEMTNT = "CREATE TABLE MESSAGES ( _id INTEGER PRIMARY KEY AUTOINCREMENT," + "aMessage text(string), PRICE INTEGER(int))"
    var messages = ArrayList<String>()  //automatically grows

    var db : SQLiteDatabase ? = null
    var messageClicked = 0
    lateinit var theAdapter :MyAdapter
    lateinit var results: Cursor
    lateinit var dbHelper :ChatDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_window)

        Log.i("ChatDatabaseHelper", "Calling onCreate");
        dbHelper = ChatDatabaseHelper()      // will open the database
        theAdapter = MyAdapter(this)

        db = dbHelper.writableDatabase      // we can read and write to this database

        //run a query, this will return the table with two columns of id and messages with its respective rows
        results = db?.query(dbHelper.TABLE_NAME, arrayOf( dbHelper.KEY_MESSAGE, dbHelper.KEY_ID),
                null, null, null, null, null, null)!!

        var numRows = results.count         // that will tell us the number of rows saved
        results.moveToFirst()       //read from the first row, moves the cursor to the top row

        val keyIndex = results.getColumnIndex(dbHelper.KEY_MESSAGE)        // which column number is KEY_MESSAGE(Messages)

        while (!results.isAfterLast)
        {
            var thisMessage = results.getString(keyIndex)!!
            messages.add(thisMessage)       //pre load messages from database
            results.moveToNext()
        }

        var myList = findViewById<ListView>(R.id.myList)
        var inputText =  findViewById<EditText>(R.id.inpuText)
        var button = findViewById<Button>(R.id.doneButton)
        var fragmentLocation = findViewById<FrameLayout>(R.id.fragment_location)        //returns null on phone, not null on tablet
        var amITablet = (fragmentLocation != null)


        myList.setOnItemClickListener { parent, view, position, id ->
            var infoToPass = Bundle()
            infoToPass.putString("Message", messages.get(position))                                //send message to fragament
            infoToPass.putLong("ID",id)                                                             //send id to fragment
            messageClicked = position                                                               //to store what position you clicked on

            // you have clicked a chat message
            if(amITablet)
            {   //tablet version of the program

                var myNewFragment = MessageFragment()
                myNewFragment.arguments = infoToPass                                                //bundle goes to fragment

                myNewFragment.parent = this
                myNewFragment.amITablet = true

            var loadFragment = getFragmentManager().beginTransaction()           //this is a fragment transaction
            loadFragment.replace(R.id.fragment_location, myNewFragment)                             //put myNewFragment in fragment_location
            loadFragment.commit()                                                                   //make it run
            }
            else
            {   //phone version of the program
                var detailsActivity = Intent(this, MessageDetails::class.java)
                detailsActivity.putExtras(infoToPass)                    // send data to next page
                startActivityForResult(detailsActivity, 35)

            }
        }


        button.setOnClickListener {
            //inputText.setText("")
            var userTyped = inputText.text.toString()       //read what user typed
            messages.add(userTyped)     // add to arrayList

            //adding messages to database:
            var newRow = ContentValues()                    //creating a new object to write into the database
            newRow.put(dbHelper.KEY_MESSAGE, userTyped)     // add to the Messages column in a new row what user typed

            db?.insert(dbHelper.TABLE_NAME,"", newRow)   //inserts a new row

            //run a query, this will return the table with two columns of id and messages with its respective rows
            results = db?.query(dbHelper.TABLE_NAME, arrayOf( dbHelper.KEY_MESSAGE, dbHelper.KEY_ID),
                    null, null, null, null, null, null)!!

            inputText.setText("")                        //to clear the window
            theAdapter.notifyDataSetChanged()            //reload the updated list
            //setResult(88)
            //finish()
        }
        myList.adapter=theAdapter
    }

    fun deleteMessage(id:Long)
    {
        //delete the message with id
        db?.delete(dbHelper.TABLE_NAME,"_id=$id", null)

        //run a query, this will return the table with two columns of id and messages with its respective rows
        results = db?.query(dbHelper.TABLE_NAME, arrayOf( dbHelper.KEY_MESSAGE, dbHelper.KEY_ID),
                null, null, null, null, null, null)!!
        messages.removeAt(messageClicked)
        theAdapter.notifyDataSetChanged()


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if((requestCode == 35) && (resultCode == Activity.RESULT_OK))
        {
            var num = data!!.getLongExtra("ID", 0)
            deleteMessage(num)
        }
    }

    inner class MyAdapter(ctx : Context) : ArrayAdapter<String>(ctx, 0 ) {

        override fun getCount(): Int {
            return messages.size
        }

        override fun getItem(position: Int): String? {
            return messages.get(position)
        }

        override fun getItemId(position: Int): Long {
            results.moveToPosition(position)
            return results.getInt(results.getColumnIndex("_id")) .toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            var inflater = LayoutInflater.from(parent.context)

            var thisRow = null as View?
             if (position % 2 == 0)
                 thisRow = inflater.inflate(R.layout.chat_row_outgoing, null)
            else
                 thisRow = inflater.inflate(R.layout.chat_row_incoming, null)

            var textView = thisRow.findViewById<TextView>(R.id.text_view)
            textView.setText( getItem (position))
            return thisRow
        }

        //return what should be at row position


        // what is the database ID of item at position

    }
    val DATABASE_NAME = "Messages.db"       // name of the database with the name Messages
    val VERSION_NUM = 5                    // version of the initial table you created


    inner class ChatDatabaseHelper : SQLiteOpenHelper(this@ChatWindow, DATABASE_NAME, null, VERSION_NUM)
    {
        val KEY_ID = "_id"
        val TABLE_NAME = "ChatMessages"
        val KEY_MESSAGE = "Messages"

        override fun onCreate(db : SQLiteDatabase)
        {
            db.execSQL("CREATE TABLE  $TABLE_NAME (  _id INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_MESSAGE + " TEXT)")
            Log.i("ChatDatabaseHelper", "Calling onCreate");

        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME)   //delete all current data
            onCreate(db)    //create new table
            Log.i("ChatDatabaseHelper", "Calling onUpgrade, Old Version: $oldVersion New Version: $newVersion");
        }
    }

    override fun onDestroy() {
     super.onDestroy()
        db?.close()
    }

}


