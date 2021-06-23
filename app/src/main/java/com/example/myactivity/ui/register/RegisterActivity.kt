package com.example.myactivity.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myactivity.R
import com.example.myactivity.ui.login.LoginActivity
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etRePassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etRePassword = findViewById(R.id.etRePassword)
        btnSignUp = findViewById(R.id.btnSignUp)
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        btnSignUp.setOnClickListener {
            when {
                etName.text.toString().length < 6 -> {
                    etName.error = "Name Minimal 6 Huruf"
                }
                etPassword.text.toString().isEmpty() -> {
                    etPassword.error = "Password Belum Diisi"
                }
                etRePassword.text.toString() != etPassword.text.toString() -> {
                    etRePassword.error = "Password Tidak Sama"
                }
                else -> {
                    val name = etName.text.toString()
                    val email = etEmail.text.toString()
                    val password = etPassword.text.toString()
                    viewModel.onProcess(email = email, password = password, username = name)
                }
            }
        }

        etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches())
                    btnSignUp.isEnabled = true
                else {
                    btnSignUp.isEnabled = false
                    etEmail.error = "Isi Email dengan contoh format xxxx@gmail.com"
                }
            }

        })

        viewModel.resultOnFailed.observe(this, Observer {
            Snackbar.make(btnSignUp,"Email Sudah Terpakai",Snackbar.LENGTH_SHORT).show()
        })
        viewModel.resultOnSuccess.observe(this, Observer {
            startActivity(Intent(this, LoginActivity::class.java))
        })
        viewModel.resultOnFailure.observe(this, Observer {
            Snackbar.make(btnSignUp,"Koneksi Jaringan Terganggu",Snackbar.LENGTH_SHORT).show()
        })
    }
}