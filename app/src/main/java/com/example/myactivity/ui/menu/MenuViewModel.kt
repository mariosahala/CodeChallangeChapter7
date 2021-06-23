package com.example.myactivity.ui.menu

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.myactivity.data.lokal.SharedPref

class MenuViewModel(application: Application): AndroidViewModel(application) {
    private val sharedPref = SharedPref(application)
    var name = sharedPref.username.toString()

}