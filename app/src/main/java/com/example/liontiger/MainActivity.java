package com.example.liontiger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    enum Player {
        PLAYER_ONE,
        PLAYER_TWO,
        PLAYER_NO
    }

    Player currentPlayer = Player.PLAYER_ONE;
    Player[] playChoices = new Player[9];

    int[][] winnerRowsColumns = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};  // row, column, diagonal winning indices

    //private int tiTag;
    private boolean gameOver = false;
    private boolean finalStage = false;
    private int count = 0;

    private Button btnGameOver;
    private GridLayout gridLayOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGameOver = findViewById(R.id.btnGameOver);
        gridLayOut = findViewById(R.id.gridLayout);

        for (int i = 0; i < playChoices.length; i++) {
            playChoices[i] = Player.PLAYER_NO;
        }

        btnGameOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTheGame();
            }
        });

    }

    public void imageViewIsTapped(View imageView) {

        ImageView tappedImageView = (ImageView) imageView;
        int tiTag = Integer.parseInt(tappedImageView.getTag().toString());
        //tappedImageView.setTranslationX(2000);

        // if there are empty boxes and game not over continue game
        if (playChoices[tiTag] == Player.PLAYER_NO && gameOver == false) {

            playChoices[tiTag] = currentPlayer;
            count = count + 1;

            if (currentPlayer == Player.PLAYER_ONE) {
                tappedImageView.setImageResource(R.drawable.x);
                currentPlayer = Player.PLAYER_TWO;
            } else if (currentPlayer == Player.PLAYER_TWO) {
                tappedImageView.setImageResource(R.drawable.o);
                currentPlayer = Player.PLAYER_ONE;
            }
            tappedImageView.animate().translationXBy(1).alpha(1).setDuration(1000);
            executeLogic();
            //checkIfGameEnds();
//            Toast.makeText(this, tappedImageView.getTag().toString(),
//                    Toast.LENGTH_SHORT).show();

        }
    }

    public void executeLogic() {
        for (int[] iterateRows : winnerRowsColumns) {

            if (playChoices[iterateRows[0]] == playChoices[iterateRows[1]] &&
                    playChoices[iterateRows[1]] == playChoices[iterateRows[2]] &&
                    playChoices[iterateRows[0]] != Player.PLAYER_NO) {

                btnGameOver.setVisibility(View.VISIBLE);
                gameOver = true;
                count = 0;
                String winnerOfGame = "";

                if (currentPlayer == Player.PLAYER_ONE) {
                    winnerOfGame = "Player Two";
                } else if (currentPlayer == Player.PLAYER_TWO) {
                    winnerOfGame = "Player One";
                }

                Toast.makeText(this, winnerOfGame + " is the Winner",
                        Toast.LENGTH_LONG).show();
            }

        }

        if (count == 9) {

            Toast.makeText(this, "No one wins",
                    Toast.LENGTH_LONG).show();
            btnGameOver.setVisibility(View.VISIBLE);
            count = 0;

        }
    }

    public void resetTheGame() {

        for (int index = 0; index < gridLayOut.getChildCount(); index++) {

            ImageView imageView = (ImageView) gridLayOut.getChildAt(index);
            imageView.setImageDrawable(null);
            imageView.setAlpha(0.2f);
            btnGameOver.setVisibility(View.GONE);
            playChoices[index] = Player.PLAYER_NO;
        }

        currentPlayer = Player.PLAYER_ONE;
        gameOver = false;
    }

    public void checkIfGameEnds() {

        for (int i = 0; i < playChoices.length; i++) {
            if ((playChoices[i] == Player.PLAYER_ONE || playChoices[i] == Player.PLAYER_TWO) &&
                    playChoices[i] != Player.PLAYER_NO) {
                finalStage = true;
            }
        }

        if (finalStage) {
            Toast.makeText(this, "No one wins",
                    Toast.LENGTH_LONG).show();
            btnGameOver.setVisibility(View.VISIBLE);
            resetTheGame();


        }

    }
}
