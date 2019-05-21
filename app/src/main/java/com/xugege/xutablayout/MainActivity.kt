package com.xugege.xutablayout

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.xugege.xutablayout.delegate.BlankFragment

class MainActivity : AppCompatActivity(), BlankFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
