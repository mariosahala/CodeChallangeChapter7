package com.example.myactivity.ui.history

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myactivity.R
import com.example.myactivity.ui.menu.MenuActivity

class HistoryActivity : AppCompatActivity() {
    lateinit var viewModel: HistoryViewModel
    lateinit var rvHistory: RecyclerView
    lateinit var ivBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        supportActionBar?.hide()
        rvHistory = findViewById(R.id.rvHistory)
        ivBack = findViewById(R.id.ivBack)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[HistoryViewModel::class.java]

        viewModel.listScore()
        viewModel.result.observe(this, Observer {
            val layoutManager = LinearLayoutManager(this)
            val adapter = HistoryAdapter(it)
            rvHistory.layoutManager = layoutManager
            rvHistory.adapter = adapter
        })

        ivBack.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }

    }
}