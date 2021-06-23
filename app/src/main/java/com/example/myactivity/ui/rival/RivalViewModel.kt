package com.example.myactivity.ui.rival

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.myactivity.data.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RivalViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)

    fun getAllList(recyclerView: RecyclerView) {
        GlobalScope.launch {
            val listRival = db?.friendsDao()?.getAllFriends()
            GlobalScope.launch(Dispatchers.Main) {
                val adapter = listRival?.let { RivalAdapter(it) }
                recyclerView.adapter = adapter
            }
        }
    }
}