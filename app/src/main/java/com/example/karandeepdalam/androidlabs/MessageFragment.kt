package com.example.karandeepdalam.androidlabs


import android.app.Activity
import android.os.Bundle
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView



/**
 * A simple [Fragment] subclass.
 *
 */
class MessageFragment : Fragment(){

    var amITablet = false
    var parent: ChatWindow? = null         //is the window displaying fragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var dataPassed = arguments      //get your data back

        var string = dataPassed.getString("Message")
        var id = dataPassed.getLong("ID")

        var screen = inflater.inflate(R.layout.fragment_message, container, false)
        var textView = screen.findViewById<TextView>(R.id.message)
        var textViewId = screen.findViewById<TextView>(R.id.id_text)
        textView.setText(string)
        textViewId.setText("ID: "+id)


        var button1 = screen.findViewById<Button>(R.id.deleteButton)

        //add a click handler:
        button1.setOnClickListener {
            if (amITablet) {
                parent?.deleteMessage(id)
                parent?.fragmentManager?.beginTransaction()?.remove(this)?.commit()     //remove from screen
            }
            else { //phone
                var dataBack = Intent()
                dataBack.putExtra("ID", id)
                activity.setResult(Activity.RESULT_OK, dataBack)
                activity.finish()
            }
        }

        // Inflate the layout for this fragment
        return screen
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if(amITablet)
            parent = context as ChatWindow          // need parent for later removing fragment


    }


}
