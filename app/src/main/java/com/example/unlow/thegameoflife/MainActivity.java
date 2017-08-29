package com.example.unlow.thegameoflife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ButtonAdapter buttonAdapter;
    private TextView roundNumber;
    private TextView scoreTextView;
    static boolean start = false;
    static int score = 0;
    private int round = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        buttonAdapter = new ButtonAdapter(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        roundNumber = (TextView) findViewById(R.id.roundNumberTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        roundNumber.setText(String.format(Locale.getDefault(), "%d", round));
        scoreTextView.setText(String.format(Locale.getDefault(), "%d", score));
        findViewById(R.id.nextRoundButton).setOnClickListener(this);
        findViewById(R.id.newGameButton).setOnClickListener(view -> {
            GridView gridview = (GridView) findViewById(R.id.gridview);
            gridview.setAdapter(buttonAdapter);
            start = false;
            round = 0;
            roundNumber.setText(String.format(Locale.getDefault(), "%d", round));
            score = 0;
            scoreTextView.setText(String.format(Locale.getDefault(), "%d", score));
        });

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(buttonAdapter);
    }

    @Override
    public void onClick(View v) {
        int height = ButtonAdapter.height, width = ButtonAdapter.width;
        List<Integer> toChange = new ArrayList<>();
        for (int i = 0; i < height * width; i++) {
            Button button = (Button) findViewById(i);
            if (buttonAdapter.checkStates(button))
                toChange.add(i);
            else if (button.getText().equals("dead+") || button.getText().equals("live+")) {
                buttonAdapter.refreshFrame(button);
                score--;
            }
        }
        for (int id : toChange)
            buttonAdapter.changeState((Button) findViewById(id));
        if (!toChange.isEmpty())
            roundNumber.setText(String.format(Locale.getDefault(), "%d", ++round));

        scoreTextView.setText(String.format(Locale.getDefault(), "%d", score));
        start = true;
    }
}


