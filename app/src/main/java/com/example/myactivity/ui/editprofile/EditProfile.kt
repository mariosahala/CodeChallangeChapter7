package com.example.myactivity.ui.editprofile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.myactivity.R
import com.example.myactivity.data.lokal.SharedPref
import com.example.myactivity.data.model.ResponseGet
import com.example.myactivity.data.model.login.ResponeLogin
import com.example.myactivity.data.model.update.ResponseUpdate
import com.example.myactivity.ui.menu.MenuActivity
import com.example.myactivity.ui.profile.ProfileActivity
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.File

class EditProfile : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var ivPhotoProfile: ImageView
    private lateinit var btnTambahFoto: Button
    private lateinit var ivBackToMenu : ImageView
    private lateinit var btnSave: Button
    private lateinit var viewModel : EditProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        viewModel = ViewModelProvider(this,defaultViewModelProviderFactory).get(EditProfileViewModel::class.java)
        val pref = SharedPref(this)

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        ivPhotoProfile = findViewById(R.id.ivPhotoProfile)
        btnTambahFoto = findViewById(R.id.btnTambahFoto)
        btnSave = findViewById(R.id.btnSave)
        ivBackToMenu = findViewById(R.id.ivBackToMenu)

        etName.setText(pref.username)
        etEmail.setText(pref.email)

        viewModel.image()
        viewModel.imageProfile.observe(this, Observer {
//           ivPhotoProfile.setImageURI(Uri.fromFile(File(it.toString())))
            Glide
                .with(this)
                .load(it.toString())
                .centerCrop()
                .into(ivPhotoProfile)
        })

        btnTambahFoto.setOnClickListener {
            getImageResult.launch(
                CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .getIntent(this)
            )
        }


        btnSave.setOnClickListener {
            val email = etEmail.text.toString()
            val username = etName.text.toString()
            viewModel.update(username, email)
            viewModel.pref.username = username
            viewModel.pref.email = email

        }
        ivBackToMenu.setOnClickListener{
            startActivity(Intent(this,ProfileActivity::class.java))
        }
        viewModel.resultSuksesUpdateData.observe(this, Observer {
            Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show()
        })
        viewModel.resultErrorUpdateData.observe(this, Observer {
            Toast.makeText(this, "Update Failed, Try to Check Your Connection!", Toast.LENGTH_SHORT).show()
        })
        viewModel.path.observe(this, Observer {
            ivPhotoProfile.setImageURI(Uri.fromFile(File(it.toString())))
        })

    }

    private val getImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val resultImage = CropImage.getActivityResult(result.data)
            if (result.resultCode === Activity.RESULT_OK) {
                val resultUri: Uri = resultImage.uri
                viewModel.path.value = resultUri.path.toString()
            } else if (result.resultCode === CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = resultImage.error
                Toast.makeText(this, "Fail to Load Image", Toast.LENGTH_SHORT).show()
            }
        }

}