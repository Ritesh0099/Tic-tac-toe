package com.ritesh.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class PlayerSetupActivity extends AppCompatActivity {

    private EditText editTextPlayerX, editTextPlayerO;
    private Button buttonStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_setup);

        editTextPlayerX = findViewById(R.id.editTextPlayerX);
        editTextPlayerO = findViewById(R.id.editTextPlayerO);
        buttonStart = findViewById(R.id.buttonStartGame);

        buttonStart.setOnClickListener(view -> {
            String playerX = editTextPlayerX.getText().toString().trim();
            String playerO = editTextPlayerO.getText().toString().trim();

            if (playerX.isEmpty()) playerX = "Player X";
            if (playerO.isEmpty()) playerO = "Player O";

            Intent intent = new Intent(PlayerSetupActivity.this, MainActivity.class);
            intent.putExtra("playerXName", playerX);
            intent.putExtra("playerOName", playerO);
            startActivity(intent);
            finish();
        });
    }
}
