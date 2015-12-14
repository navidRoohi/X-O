package com.gigarchitecture.navid.ticgame;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private boolean noughtsTurn = false;
    private char board[][] = new char[6][6];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setupOnClickListeners();
        resetButtons();
    }

    public void newGame(View view) {
        noughtsTurn = false;
        board = new char[6][6];
        resetButtons();
    }

    public void resetButtons() {

        TableLayout T = (TableLayout) findViewById(R.id.tableLayout);

        for (int y = 0; y < T.getChildCount(); y++) {
            if (T.getChildAt(y) instanceof TableRow) {
                TableRow R = (TableRow) T.getChildAt(y);

                for (int x = 0; x < R.getChildCount(); x++) {
                    if (R.getChildAt(x) instanceof ImageButton) {
                        ImageButton B = (ImageButton) R.getChildAt(x);

                        B.setImageResource(com.gigarchitecture.navid.ticgame.R.drawable.empty);
                        B.setEnabled(true);
                    }
                }
            }

        }
        TextView t = (TextView) findViewById(R.id.titleText);
        t.setText(R.string.title);
    }

    private boolean checkWin() {

        char winner = '\0';

        if (checkWinner(board, 6, 'X')) {
            winner = 'X';
        } else if (checkWinner(board, 6, 'O')) {
            winner = 'O';
        }

        if (winner == '\0') {
            return false; // nobody won
        } else {
            // display winner
            TextView T = (TextView) findViewById(R.id.titleText);
            T.setText(winner + " wins");
            return true;
        }
    }


    private boolean checkWinner(char[][] board, int size, char player) {

        int num =1;
        int num1 = 1;
        int num2 = 1;
        int num3 = 1;

        ArrayList<Integer> list = new ArrayList<>();


        // check each column

        list.clear();
        for (int x = 0; x < size; x++) {
            int total = 0;

            for (int y = 0; y < size; y++) {
                if (board[x][y] == player) {
                    list.add(y);
                    total++;
                }
            }

            if (total >= 4) {

                for (int i = 0 ; i<list.size()-1;i++){
                    if (list.get(i+1) - list.get(i) != 1){

                        num +=1;
                    }
                }
                if (num == 1){
                    return true;
                }
            }
        }


        list.clear();
        // check each row
        for (int y = 0; y < size; y++) {
            int total = 0;

            for (int x = 0; x < size; x++) {
                if (board[x][y] == player) {
                    list.add(x);
                    total++;
                }
            }
            if (total >= 4) {
                for (int i = 0 ; i<list.size()-1;i++){
                    if (list.get(i+1) - list.get(i) != 1){
                        num1 +=1;
                    }
                }
                if (num1 == 1){
                    return true;
                }
            }
        }

        // forward diag
        list.clear();

        for (int x = 0; x < size; x++) {
            int total = 0;
            for (int y = 0; y < size; y++) {
                if (x == y && board[x][y] == player) {
                    list.add(x);
                    total++;
                }
            }

            if (total >= 4) {
                if (total >= 4) {
                    for (int i = 0; i < list.size() - 1; i++) {
                        if (list.get(i + 1) - list.get(i) != 1) {
                            num2 += 1;
                        }
                    }
                    if (num2 == 1) {
                        return true;
                    }
                }
            }
        }

        // backward diag

        list.clear();
        for (int x = 0; x < size; x++) {
            int total = 0;
            for (int y = 0; y < size; y++) {
                if (x + y == size - 1 && board[x][y] == player) {
                    list.add(y + x);
                    total++;
                }
            }

            if (total >= 4) {

                for (int i = 0; i < list.size() - 1; i++) {
                    if (list.get(i + 1) - list.get(i) != 2) {
                        num3 += 1;
                    }
                }
                if (num3 == 1) {
                    return true;
                }
            }

        }
        return false; // nobody won
    }



    private void disableButtons(){
        TableLayout T = (TableLayout) findViewById(R.id.tableLayout);
        for (int y = 0; y < T.getChildCount(); y++) {
            if (T.getChildAt(y) instanceof TableRow) {
                TableRow R = (TableRow) T.getChildAt(y);
                for (int x = 0; x < R.getChildCount(); x++) {
                    if (R.getChildAt(x) instanceof ImageButton) {
                        ImageButton B = (ImageButton) R.getChildAt(x);
                        B.setEnabled(false);
                    }
                }
            }
        }
    }

    private void setupOnClickListeners() {
        TableLayout T = (TableLayout) findViewById(R.id.tableLayout);
        for (int y = 0; y < T.getChildCount(); y++) {
            if (T.getChildAt(y) instanceof TableRow) {
                TableRow R = (TableRow) T.getChildAt(y);
                for (int x = 0; x < R.getChildCount(); x++) {
                    View V = R.getChildAt(x);
                    V.setOnClickListener(new PlayOnClick(x, y));
                }
            }
        }
    }


    private class PlayOnClick implements View.OnClickListener {

        private int x = 0;
        private int y = 0;

        public PlayOnClick(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void onClick(View view) {
            if (view instanceof ImageButton) {

                ImageButton B = (ImageButton) view;
                board[x][y] = noughtsTurn ? 'O' : 'X';

                B.setImageResource(noughtsTurn ? R.drawable.o : R.drawable.x);
                B.setEnabled(false);
                noughtsTurn = !noughtsTurn;

                if (checkWin()) {
                    disableButtons();
                }
            }
        }
    }
}