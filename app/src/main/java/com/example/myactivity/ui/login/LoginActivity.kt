package com.example.myactivity.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myactivity.R
import com.example.myactivity.ui.menu.MenuActivity
import com.example.myactivity.ui.register.RegisterActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {
    private lateinit var etEmailLogin: EditText
    private lateinit var etPasswordLogin: EditText
    private lateinit var btnSignInLogin: Button
    private lateinit var btnSignUpLogin: Button
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        etEmailLogin = findViewById(R.id.etEmailLogin)
        etPasswordLogin = findViewById(R.id.etPasswordLogin)
        btnSignInLogin = findViewById(R.id.btnSignInLogin)
        btnSignUpLogin = findViewById(R.id.btnToSignUpLogin)
        viewModel =
            ViewModelProvider(this, defaultViewModelProviderFactory).get(LoginViewModel::class.java)

        btnSignInLogin.setOnClickListener {
            when {
                etEmailLogin.text.toString().isEmpty() -> {
                    etEmailLogin.error = "Username Belum Diisi"
                }
                etPasswordLogin.text.toString().isEmpty() -> {
                    etPasswordLogin.error = "Password harus Diisi"
                }
                else -> {
                    val email = etEmailLogin.text.toString()
                    val password = etPasswordLogin.text.toString()
                    viewModel.setContent(email, password)
                }
            }
        }

        btnSignUpLogin.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        etEmailLogin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(etEmailLogin.text.toString())
                        .matches()
                )
                    btnSignInLogin.isEnabled = true
                else {
                    btnSignInLogin.isEnabled = false
                    etEmailLogin.error = "Isi Email dengan format xxxx@gmail.com"
                }
            }

        })

        viewModel.resultOnFailed.observe(this, Observer {
            Snackbar.make(btnSignInLogin, "Email atau Password anda Salah", Snackbar.LENGTH_SHORT).show()

        })

        viewModel.resultOnSuccess.observe(this, Observer {
            startActivity(Intent(this@LoginActivity, MenuActivity::class.java))
        })

        viewModel.resultOnFailure.observe(this, Observer {
            Snackbar.make(btnSignInLogin, "Koneksi Jaringan Terganggu", Snackbar.LENGTH_SHORT).show()
        })

    }
}