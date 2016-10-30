package com.iandouglas.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.q2_answers_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.q2_answers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void scoreHandler(View view) {
        try {
            String answer = getString(R.string.perfect_score);
            int score = calculateScore();
            if (score < 4) {
                answer = String.valueOf(score) + getString(R.string.score_end);
            }
            Toast.makeText(view.getContext(), answer, Toast.LENGTH_LONG).show();
        } catch (NullPointerException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private int calculateScore() throws NullPointerException {
        int score = 0;

        // question 1
        RadioGroup rg = (RadioGroup) findViewById(R.id.q1_radiogroup);
        if (rg.getCheckedRadioButtonId() == R.id.rbtn_question1_2) {
            score += 1;
        } else if (rg.getCheckedRadioButtonId() == -1) {
            throw new NullPointerException(getString(R.string.q1_noanswer));
        }

        // question 2
        Spinner spinner = (Spinner) findViewById(R.id.q2_answers_spinner);
        int position = spinner.getSelectedItemPosition();
        if (position == 3) {
            score += 1;
        } else if (position == 0) {
            throw new NullPointerException(getString(R.string.q2_noanswer));
        }

        // question 3
        CheckBox cb1 = (CheckBox) findViewById(R.id.q3_checkbox1);
        CheckBox cb2 = (CheckBox) findViewById(R.id.q3_checkbox2);
        CheckBox cb3 = (CheckBox) findViewById(R.id.q3_checkbox3);
        CheckBox cb4 = (CheckBox) findViewById(R.id.q3_checkbox4);
        if (cb1.isChecked() && !cb2.isChecked() && cb3.isChecked() && !cb4.isChecked()) {
            ++score;  //so we adding 1 to the score
        } else if (!cb1.isChecked() && !cb2.isChecked() && !cb3.isChecked() && !cb4.isChecked()) {
            throw new NullPointerException(getString(R.string.q3_noanswer));
        }

        // question 4
        EditText setvar = (EditText) findViewById(R.id.q4_answer);
        String answerText = setvar.getText().toString();
        if (answerText.length() != 0) {
            if (answerText.toLowerCase().replaceAll("\\s", "").equals(getString(R.string.q4_rightanswer))) {
                ++score;
            }
        } else {
            throw new NullPointerException(getString(R.string.q4_noanswer));
        }

        return score;
    }
}