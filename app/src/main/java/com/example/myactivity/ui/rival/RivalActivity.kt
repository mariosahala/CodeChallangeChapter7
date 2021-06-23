package com.example.myactivity.ui.rival

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myactivity.R
import com.example.myactivity.ui.menu.MenuActivity

class RivalActivity : AppCompatActivity() {
    private lateinit var rv: RecyclerView
    private lateinit var viewModel: RivalViewModel
    private lateinit var ivBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rival)
        getAllRival()
        ivBack = findViewById(R.id.ivBack)

        ivBack.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }
    }

    private fun getAllRival() {
        rv = findViewById(R.id.rvRival)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[RivalViewModel::class.java]
        viewModel.getAllList(rv)
        rv.layoutManager = LinearLayoutManager(this)
    }
}