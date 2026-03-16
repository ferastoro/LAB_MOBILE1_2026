package com.example.tp1_mobile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etUsername, etName, etBio;
    private Button btnSave;
    private CircleImageView ivEditProfilePic;
    private Uri selectedImageUri; // To astore the URI of the selected image

    // Launcher for picking an image from the gallery
    private final ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    ivEditProfilePic.setImageURI(selectedImageUri);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etUsername = findViewById(R.id.etUsername);
        etName = findViewById(R.id.etName);
        etBio = findViewById(R.id.etBio);
        btnSave = findViewById(R.id.btnSave);
        ivEditProfilePic = findViewById(R.id.ivEditProfilePic);

        // Tangkap data yang dikirim dari MainActivity
        Intent intent = getIntent();
        etUsername.setText(intent.getStringExtra("CURRENT_USERNAME"));
        etName.setText(intent.getStringExtra("CURRENT_NAME"));
        etBio.setText(intent.getStringExtra("CURRENT_BIO"));

        // Load current profile picture if available
        String currentProfilePicUriString = intent.getStringExtra("CURRENT_PROFILE_PIC_URI");
        if (currentProfilePicUriString != null) {
            selectedImageUri = Uri.parse(currentProfilePicUriString);
            ivEditProfilePic.setImageURI(selectedImageUri);
        }

        // Aksi klik untuk mengubah foto profil
        ivEditProfilePic.setOnClickListener(v -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK);
            galleryIntent.setType("image/*");
            pickImageLauncher.launch(galleryIntent);
        });

        // Aksi simpan data
        btnSave.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            // Masukkan data baru ke dalam Intent
            resultIntent.putExtra("NEW_USERNAME", etUsername.getText().toString());
            resultIntent.putExtra("NEW_NAME", etName.getText().toString());
            resultIntent.putExtra("NEW_BIO", etBio.getText().toString());
            if (selectedImageUri != null) {
                resultIntent.putExtra("NEW_PROFILE_PIC_URI", selectedImageUri.toString());
            }

            // Set result OK dan tutup activity ini (kembali ke MainActivity)
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}