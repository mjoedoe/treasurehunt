package com.example.treasurehunt.ui.game;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Pair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.treasurehunt.FileIOScores;
import com.example.treasurehunt.R;
import com.example.treasurehunt.ScoreItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class gameViewModel extends ViewModel {

    private Context context;
    private FileIOScores fileIOScores;
    private final static String fname =  "scores.txt";
    private int gameState;
    private int treasure;
    private int pirate;
    private ArrayList<ScoreItem> scoreItems;
    private MutableLiveData<Boolean> gameActive;
    private MutableLiveData<Pair> background;

    public gameViewModel() {
        background = new MutableLiveData<>();
        gameActive = new MutableLiveData<>();
        gameState = 0;
        treasure = 0;
        pirate = 0;

        gameActive.setValue(Boolean.TRUE);
        background.setValue(new Pair<Integer, Integer>(1,R.drawable.ic_button_art));

    }

    public void setContext(Context context) {
        this.context = context;
        this.fileIOScores = new FileIOScores((AppCompatActivity) this.context);
        scoreItems = fileIOScores.readScores(fname);
    }

    public LiveData<Boolean> getGameActive(){return gameActive;}


    public void resetGame() {
        // Reset gameState and Buttons.

        if (gameActive.getValue()) gameActive.setValue(Boolean.FALSE);
        else gameActive.setValue(Boolean.TRUE);

        Random random = new Random();
        treasure = random.nextInt(12) + 1;
        // Generates a random number from 1 to 12

        // Different from treasure
        do {
            pirate = random.nextInt(12) + 1;
        } while (pirate == treasure);

    }

    // Returns the Index and the Drawable the Button needs to be set to.
    public void playing(int buttonIndex) {
        // We only get here if game is active and a button was pressed.

        if (gameState > 3 ||  buttonIndex == pirate) {
            background.setValue(new Pair<Integer, Integer>(buttonIndex, R.drawable.ic_button_art_pirate));
            resetGame();
            gameState = 0;

        } else if (buttonIndex == treasure){
            background.setValue(new Pair<Integer, Integer>(buttonIndex,R.drawable.ic_button_art_treasure));
            resetGame();
            logGame();
            gameState = 0;
        } else {
            background.setValue(new Pair<Integer, Integer>(buttonIndex,R.drawable.ic_button_art_nothing));
        }
        gameState++;
        if (gameState == 3) {

        }

    }

    private void logGame() {
        // Save newest score and write to file.
        //
        LocalDateTime time = LocalDateTime.now();
        ScoreItem scoreItem = new ScoreItem(gameState, time);
        scoreItems.add(scoreItem);
        fileIOScores.writeFile(fname, scoreItems);
    }

    public MutableLiveData<Pair> getButtonBackground() {
        return background;
    }
}