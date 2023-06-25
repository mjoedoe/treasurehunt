package com.example.treasurehunt.ui.scores;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.treasurehunt.FileIOScores;
import com.example.treasurehunt.ScoreItem;

import java.util.ArrayList;

public class scoresViewModel extends ViewModel {

    private Context context;
    private FileIOScores fileIOScores;
    private final static String fname =  "scores.txt";
    private ArrayList<ScoreItem> scoreItems;
    private final MutableLiveData<String> mText;
    public scoresViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("No this patrick");
    }
    public void setContext(Context context) {
        this.context = context;
        this.fileIOScores = new FileIOScores((AppCompatActivity) this.context);
        scoreItems = fileIOScores.readScores(fname);
    }

    public ArrayList<String> getContentasString() {
        ArrayList<String> asString = new ArrayList<>();
        for (ScoreItem item: scoreItems) {
            asString.add(item.toString());
        }
        return asString;
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void resetScores() {
        // Clear the list and the file.
        fileIOScores.eraseContent(fname);

    }
}