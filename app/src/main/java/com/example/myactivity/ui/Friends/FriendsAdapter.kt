package com.example.myactivity.ui.Friends

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myactivity.R
import com.example.myactivity.data.database.AppDatabase
import com.example.myactivity.data.database.Friends
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FriendsAdapter(private val listFriends: MutableList<Friends> = mutableListOf()) :
    RecyclerView.Adapter<FriendsAdapter.ViewHolders>() {

    class ViewHolders(view: View) : RecyclerView.ViewHolder(view) {
        private val tvName = view.findViewById<TextView>(R.id.tvName)
        private val tvEmail = view.findViewById<TextView>(R.id.tvEmail)

        fun onBind(friend: Friends) {
            tvName.text = friend.name
            tvEmail.text = friend.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolders =
        ViewHolders(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item_friend_layout, parent, false)
        )

    override fun getItemCount(): Int = listFriends.size

    override fun onBindViewHolder(holder: ViewHolders, position: Int) {
        holder.onBind(listFriends[position])

        holder.itemView.setOnClickListener {
            val v = LayoutInflater.from(it.context)
                .inflate(R.layout.dialog_edit_friends, null, false)
            val builder = AlertDialog.Builder(it.context)
            builder.setView(v)

            val dialog = builder.create()
            val btnEdit = v.findViewById<Button>(R.id.btnEdit)
            val etName = v.findViewById<EditText>(R.id.etName)
            val etEmail = v.findViewById<EditText>(R.id.etEmail)

            etName.setText(listFriends[position].name)
            etEmail.setText(listFriends[position].email)

            btnEdit.setOnClickListener {
                val db = AppDatabase.getInstance(it.context)
                val friendsDao = db?.friendsDao()

                GlobalScope.launch {
                    listFriends[position].name = etName.text.toString()
                    listFriends[position].email = etEmail.text.toString()
                    val result = friendsDao?.updateFriend(listFriends[position])

                    GlobalScope.launch(Dispatchers.Main) {
                        if (result != 0) {
                            Toast.makeText(
                                it.context,
                                "Success, ${etName.text} updated!",
                                Toast.LENGTH_LONG
                            ).show()
                            dialog.dismiss()
                            (holder.itemView.context as FriendsActivity).fetchData()
                        } else {
                            Toast.makeText(it.context, "Failed to update", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
            dialog.show()
        }
    }

    fun deleteItem(context: Context, position: Int) {
        val db = AppDatabase.getInstance(context)
        val friendsDao = db?.friendsDao()

        GlobalScope.launch {
            val result = friendsDao?.deleteFriend(listFriends[position])

            GlobalScope.launch(Dispatchers.Main) {
                if (result != 0) {
                    Toast.makeText(
                        context,
                        "${listFriends[position].name} Deleted",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "${listFriends[position].name} Delete is failed",
                        Toast.LENGTH_LONG
                    ).show()
                }
                (ViewHolders(View(context)).itemView.context as FriendsActivity).fetchData()
            }
        }
    }
}