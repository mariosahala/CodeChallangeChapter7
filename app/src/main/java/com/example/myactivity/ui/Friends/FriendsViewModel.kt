package com.example.myactivity.ui.Friends

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.myactivity.data.database.AppDatabase
import com.example.myactivity.data.database.Friends
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FriendsViewModel(application: Application) : AndroidViewModel(application) {
    private val friendName = MutableLiveData<String>()
    private val friendEmail = MutableLiveData<String>()
    val resultSuccess = MutableLiveData<String>()
    val resultFailure = MutableLiveData<String>()
    private val db = AppDatabase.getInstance(application)

    fun addFriends(name: String, email: String) {
        friendName.value = name
        friendEmail.value = email

        GlobalScope.launch {
            val friend = Friends(name = name, email = email)
            val result = db?.friendsDao()?.addFriend(friend)

            GlobalScope.launch(Dispatchers.Main) {
                if (result != 0.toLong()) {
                    resultSuccess.value = "Success, $name added!"

                } else {
                    resultFailure.value = "Failure, $name failed to add!"
                }
            }
        }
    }

    fun fetchData(recyclerView: RecyclerView) {
        GlobalScope.launch {
            val list = db?.friendsDao()?.getAllFriends()
            GlobalScope.launch(Dispatchers.Main) {
                val adapter = list?.let { FriendsAdapter(it) }
                recyclerView.adapter = adapter
            }
        }
    }

    fun deleteData(context: Context, viewHolder: RecyclerView.ViewHolder) {
        GlobalScope.launch {
            val adapter = db?.friendsDao()?.getAllFriends()?.let { FriendsAdapter(it) }
            GlobalScope.launch(Dispatchers.Main) {
                adapter?.deleteItem(context, viewHolder.absoluteAdapterPosition)
            }
        }
    }

}