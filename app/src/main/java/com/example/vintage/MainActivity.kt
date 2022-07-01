package com.example.vintage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.vintage.fragments.DownloadFragment
import com.example.vintage.fragments.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Setting Home Fragment as default fragment
        replaceFragment(HomeFragment())

        homeFrag.setOnClickListener{
            Toast.makeText( this, "Home", Toast.LENGTH_SHORT).show()
            replaceFragment(HomeFragment())
        }

        downloadFrag.setOnClickListener {
            Toast.makeText( this, "Download", Toast.LENGTH_SHORT).show()
            replaceFragment(DownloadFragment())
        }
    }

    // Function for Replacing Fragments on Clicking Bottom Navigation (Stack Overflow)
    private fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.replaceFrag, fragment)
        transaction.commit()
    }
}