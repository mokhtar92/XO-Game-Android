package iti.gov.eg.xogame;

import android.content.DialogInterface;
import android.os.TestLooperManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private int a1, a2, a3, b1, b2, b3, c1, c2, c3;
    private int counter;

    @BindViews({R.id.a1, R.id.a2, R.id.a3, R.id.b1, R.id.b2, R.id.b3, R.id.c1, R.id.c2, R.id.c3})
    List<TextView> XO_Board;

    @BindView(R.id.result_text_view)
    TextView resultTextView;

    @BindView(R.id.start_game_text_veiw)
    TextView startGameTextView;

    @BindView(R.id.tap_to_play_text_view)
    TextView tapToPlayTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setDefaultValues();

        for (TextView textView : XO_Board) {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (counter == 0) {
                        tapToPlayTextView.setVisibility(View.GONE);
                        startGameTextView.setText(R.string.str_restart_game);
                        startGameTextView.setVisibility(View.VISIBLE);
                    }
                    v.setClickable(false);
                    TextView currentTextView = (TextView) v;
                    currentTextView.setText(getXorO());

                    //player one set value to 0 else set value to 1.
                    int value = counter % 2 == 0 ? 0 : 1;
                    setPlayerChoice(v.getId(), value);

                    //check winner starting from step 5 when we could get combination of 3 for a player
                    if (counter >= 4) {
                        if (hasWon() && counter < 9) {
                            setGameBoardToUnClickable();
                            String result = "Player " + (counter % 2 == 0 ? "1" : "2") + " wins";
                            resultTextView.setText(result);
                            resultTextView.setVisibility(View.VISIBLE);
                            startGameTextView.setText(R.string.str_start_new_game);

                        } else if (counter == 8) {
                            setGameBoardToUnClickable();
                            resultTextView.setText(R.string.str_draw);
                            resultTextView.setVisibility(View.VISIBLE);
                            startGameTextView.setText(R.string.str_start_new_game);
                        }
                    }
                    counter++;
                }
            });
        }
    }

    @OnClick(R.id.start_game_text_veiw)
    void startNewGame() {
        if (resultTextView.getVisibility() == View.INVISIBLE) {
            showAlertDialogue(getResources().getString(R.string.app_name), getString(R.string.str_restart_alert_message))
                    .setPositiveButton(R.string.str_ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            resetGame();
                        }
                    })
                    .show();

        } else {
            resetGame();
        }
    }

    private String getXorO() {
        if (counter % 2 == 0) {
            return "X";

        } else {
            return "O";
        }
    }

    private void setPlayerChoice(int id, int value) {
        switch (id) {
            case R.id.a1:
                a1 = value;
                break;

            case R.id.a2:
                a2 = value;
                break;

            case R.id.a3:
                a3 = value;
                break;

            case R.id.b1:
                b1 = value;
                break;

            case R.id.b2:
                b2 = value;
                break;

            case R.id.b3:
                b3 = value;
                break;

            case R.id.c1:
                c1 = value;
                break;

            case R.id.c2:
                c2 = value;
                break;

            case R.id.c3:
                c3 = value;
                break;
        }
    }

    private boolean hasWon() {
        if (a1 == a2 && a2 == a3) {
            return true;

        } else if (b1 == b2 && b2 == b3) {
            return true;

        } else if (c1 == c2 && c2 == c3) {
            return true;

        } else if (a1 == b1 && b1 == c1) {
            return true;

        } else if (a2 == b2 && b2 == c2) {
            return true;

        } else if (a3 == b3 && b3 == c3) {
            return true;

        } else if (a1 == b2 && b2 == c3) {
            return true;

        } else if (a3 == b2 && b2 == c1) {
            return true;

        } else {
            return false;
        }
    }

    private void setDefaultValues() {
        counter = 0;
        a1 = 11;
        a2 = 12;
        a3 = 13;
        b1 = 14;
        b2 = 15;
        b3 = 16;
        c1 = 17;
        c2 = 18;
        c3 = 19;
    }

    private void resetGame() {
        for (TextView textView : XO_Board) {
            textView.setText("");
            textView.setClickable(true);
        }
        resultTextView.setVisibility(View.INVISIBLE);
        startGameTextView.setVisibility(View.INVISIBLE);
        setDefaultValues();
        tapToPlayTextView.setVisibility(View.VISIBLE);
    }

    private void setGameBoardToUnClickable() {
        for (TextView textView : XO_Board) {
            textView.setClickable(false);
        }
    }

    private AlertDialog.Builder showAlertDialogue(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setCancelable(false)
                .setMessage(message)
                .setNegativeButton(R.string.str_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        return builder;
    }

    @Override
    public void onBackPressed() {
        showAlertDialogue(getResources().getString(R.string.str_confirmation), getString(R.string.str_exit_alert_message))
                .setPositiveButton(R.string.str_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }
}
