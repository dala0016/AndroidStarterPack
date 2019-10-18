package com.example.karandeepdalam.androidlabs

import android.app.Activity
import android.os.Bundle

class MessageDetails : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_details)

        var infoToPass = intent.extras

        var myNewFragment = MessageFragment()
        myNewFragment.arguments = infoToPass                                                //bundle goes to fragment

        myNewFragment.amITablet = false

        var loadFragment = getFragmentManager().beginTransaction()           //this is a fragment transaction
        loadFragment.replace(R.id.fragment_location, myNewFragment)                             //put myNewFragment in fragment_location
        loadFragment.commit()
    }
}
