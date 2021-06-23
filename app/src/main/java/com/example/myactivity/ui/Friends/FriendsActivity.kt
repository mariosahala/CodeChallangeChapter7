package com.example.myactivity.ui.Friends

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myactivity.R
import com.example.myactivity.ui.menu.MenuActivity

class FriendsActivity : AppCompatActivity() {
    private lateinit var rvFriends: RecyclerView
    private lateinit var btnAddFriends: Button
    private lateinit var ivBack: ImageView
    private lateinit var viewModel: FriendsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)
        rvFriends = findViewById(R.id.rvFriends)
        ivBack = findViewById(R.id.ivBack)
        btnAddFriends = findViewById(R.id.btnAddFriends)
        viewModel = ViewModelProvider(
            this@FriendsActivity,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[FriendsViewModel::class.java]
        rvFriends.layoutManager = LinearLayoutManager(this)

        btnAddFriends.setOnClickListener {
            addFriends()
        }

        ivBack.setOnClickListener {
            finish()
        }

        val swipeDelete = object : SwipeDeleteFriends(this@FriendsActivity) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteData(this@FriendsActivity, viewHolder)
            }
        }

        val touchHelper = ItemTouchHelper(swipeDelete)
        touchHelper.attachToRecyclerView(rvFriends)
    }

    private fun addFriends() {
        viewModel = ViewModelProvider(
            this@FriendsActivity,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[FriendsViewModel::class.java]

        val view = LayoutInflater.from(this).inflate(R.layout.dialog_add_friends, null, false)
        val etName = view.findViewById<EditText>(R.id.etName)
        val etEmail = view.findViewById<EditText>(R.id.etEmail)
        val btnAdd = view.findViewById<Button>(R.id.btnAdd)

        val builder = AlertDialog.Builder(this)
        builder.setView(view)

        val dialog = builder.create()

        btnAdd.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            if (name.isEmpty() && email.isEmpty()) {
                etName.error = "Please, input username"
                etEmail.error = "Please, input email"
            } else if (name.isEmpty()) {
                etName.error = "Please, input username"
            } else if (email.isEmpty()) {
                etEmail.error = "Please, input email"
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.error = "Please, input email correctly"
            } else {
                viewModel.addFriends(name, email)
                dialog.dismiss()
            }
        }
        dialog.show()

        viewModel.resultSuccess.observe(this, Observer {
            Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
            fetchData()
        })

        viewModel.resultFailure.observe(this, Observer {
            Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
            fetchData()
        })
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    fun fetchData() {
        viewModel = ViewModelProvider(
            this@FriendsActivity,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[FriendsViewModel::class.java]
        rvFriends = findViewById(R.id.rvFriends)
        viewModel.fetchData(rvFriends)
    }

}
