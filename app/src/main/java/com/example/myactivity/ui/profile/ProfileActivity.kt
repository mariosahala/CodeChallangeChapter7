package com.example.myactivity.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.myactivity.R
import com.example.myactivity.data.lokal.SharedPref
import com.example.myactivity.ui.editprofile.EditProfile
import com.example.myactivity.ui.login.LoginActivity
import com.example.myactivity.ui.menu.MenuActivity
import kotlinx.android.synthetic.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var btnLogout : Button
    private lateinit var ivKembaliMenu : ImageView
    private lateinit var ivProfile : ImageView
    private lateinit var tvName : TextView
    private lateinit var tvEmail : TextView
    private lateinit var btnEditAkun : Button
    private lateinit var viewModel : ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.hide()
        val pref = SharedPref (this)
        btnLogout = findViewById(R.id.btnLogOut)
        ivKembaliMenu = findViewById(R.id.ivkembaliMenu)
        ivProfile = findViewById(R.id.ivProfile)
        tvName = findViewById(R.id.tvName)
        tvEmail = findViewById(R.id.tvEmail)
        btnEditAkun = findViewById(R.id.btnEditAkun)
        viewModel = ViewModelProvider(this,defaultViewModelProviderFactory).get(ProfileViewModel::class.java)

        tvName.setText(pref.username)
        tvEmail.setText(pref.email)

        viewModel.getPhoto()
        viewModel.imageProfile.observe(this, Observer {
            Glide
                .with(this)
                .load(it.toString())
                .centerCrop()
                .into(ivProfile)
        })

        btnLogout.setOnClickListener {
            pref.isLogin = false
            startActivity(
                Intent(this, LoginActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            finish()
        }
        
        btnEditAkun.setOnClickListener {
            startActivity(Intent(this,EditProfile::class.java))
        }
        ivKembaliMenu.setOnClickListener {
            startActivity(Intent(this,MenuActivity::class.java))
        }
    }
}