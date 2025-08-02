package com.ritesh.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button[][] buttons = new Button[3][3];
    private boolean playerXTurn = true;
    private int roundCount;
    private int playerXScore;
    private int playerOScore;

    private TextView textViewPlayerX;
    private TextView textViewPlayerO;
    private TextView textViewStatus;
    private Button resetButton;

    private String playerXName = "Player X";
    private String playerOName = "Player O";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayerX = findViewById(R.id.playerXScore);
        textViewPlayerO = findViewById(R.id.playerOScore);
        textViewStatus = findViewById(R.id.statusText);
        resetButton = findViewById(R.id.resetButton);

        // Get player names from intent
        Intent intent = getIntent();
        if (intent != null) {
            playerXName = intent.getStringExtra("playerXName");
            playerOName = intent.getStringExtra("playerOName");
        }

        textViewPlayerX.setText(playerXName + ": 0");
        textViewPlayerO.setText(playerOName + ": 0");
        textViewStatus.setText("Turn: " + playerXName);

        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int id = getResources().getIdentifier("button" + i + j, "id", getPackageName());
                buttons[i][j] = findViewById(id);
                final int row = i;
                final int col = j;
                buttons[i][j].setOnClickListener(view -> onButtonClick(row, col));
            }
        }

        resetButton.setOnClickListener(v -> resetGame());
    }

    private void onButtonClick(int row, int col) {
        if (!buttons[row][col].getText().toString().equals("")) return;

        buttons[row][col].setText(playerXTurn ? "X" : "O");

        roundCount++;

        if (checkForWin()) {
            if (playerXTurn) {
                playerWins("X");
            } else {
                playerWins("O");
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            playerXTurn = !playerXTurn;
            textViewStatus.setText("Turn: " + (playerXTurn ? playerXName : playerOName));
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                field[i][j] = buttons[i][j].getText().toString();

        for (int i = 0; i < 3; i++) {
            if (!field[i][0].equals("") &&
                    field[i][0].equals(field[i][1]) &&
                    field[i][0].equals(field[i][2])) {
                return true;
            }
            if (!field[0][i].equals("") &&
                    field[0][i].equals(field[1][i]) &&
                    field[0][i].equals(field[2][i])) {
                return true;
            }
        }

        if (!field[0][0].equals("") &&
                field[0][0].equals(field[1][1]) &&
                field[0][0].equals(field[2][2])) {
            return true;
        }

        if (!field[0][2].equals("") &&
                field[0][2].equals(field[1][1]) &&
                field[0][2].equals(field[2][0])) {
            return true;
        }

        return false;
    }

    private void playerWins(String player) {
        if (player.equals("X")) {
            playerXScore++;
            textViewPlayerX.setText(playerXName + ": " + playerXScore);
            textViewStatus.setText(playerXName + " wins!");
        } else {
            playerOScore++;
            textViewPlayerO.setText(playerOName + ": " + playerOScore);
            textViewStatus.setText(playerOName + " wins!");
        }

        disableButtons();
    }

    private void draw() {
        textViewStatus.setText("It's a draw!");
    }

    private void disableButtons() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                buttons[i][j].setEnabled(false);
    }

    private void resetGame() {
        roundCount = 0;
        playerXTurn = true;
        textViewStatus.setText("Turn: " + playerXName);

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
    }
}
