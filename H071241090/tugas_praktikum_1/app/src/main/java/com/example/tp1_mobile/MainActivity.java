package com.example.tp1_mobile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvUsernameTop, tvName, tvBio;
    private Button btnEditProfile;
    private ImageView ivProfilePic;
    private String profilePicUriString;

    // Launcher untuk menangani hasil dari EditProfileActivity
    private final ActivityResultLauncher<Intent> editProfileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    // Mengambil data yang dikirim balik dan mengupdate UI
                    tvUsernameTop.setText(data.getStringExtra("NEW_USERNAME"));
                    tvName.setText(data.getStringExtra("NEW_NAME"));
                    tvBio.setText(data.getStringExtra("NEW_BIO"));
                    
                    String newPicUri = data.getStringExtra("NEW_PROFILE_PIC_URI");
                    if (newPicUri != null) {
                        profilePicUriString = newPicUri;
                        ivProfilePic.setImageURI(Uri.parse(newPicUri));
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi View
        tvUsernameTop = findViewById(R.id.tvUsernameTop);
        tvName = findViewById(R.id.tvName);
        tvBio = findViewById(R.id.tvBio);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        ivProfilePic = findViewById(R.id.ivProfilePic);

        // Aksi klik tombol Edit Profile
        btnEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
            // Kirim data saat ini ke halaman edit agar form langsung terisi
            intent.putExtra("CURRENT_USERNAME", tvUsernameTop.getText().toString());
            intent.putExtra("CURRENT_NAME", tvName.getText().toString());
            intent.putExtra("CURRENT_BIO", tvBio.getText().toString());
            intent.putExtra("CURRENT_PROFILE_PIC_URI", profilePicUriString);

            // Pindah halaman
            editProfileLauncher.launch(intent);
        });
    }
}