package com.example.groceryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    TextView n1;
    TextView t;
    TextView n2;
    Button check_answer_button;
    Button next_problem_button;
    EditText answer_textbox;
    TextView score_textview;

    ArrayList<String> correct_and_incorrect_answers = new ArrayList<String>(10);
    String correct_marker = "correct";
    String incorrect_marker = "incorrect";
    int number_of_correct_problems = 0;
    int problem_number = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        n1 = findViewById(R.id.number1_textview);

        t = findViewById(R.id.operation_textview);

        n2 = findViewById(R.id.number2_textview);

        score_textview = findViewById(R.id.score_textview);

        check_answer_button = findViewById(R.id.check_answer_button);
        next_problem_button = findViewById(R.id.next_problem_button);

        addRandomNumbersAndSymbolsToTextViews(n1, t, n2);


        answer_textbox = findViewById(R.id.answer_textbox);

        check_answer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String my_answer = String.valueOf(answer_textbox.getText());

                if(my_answer.equals("")) {
                    Toast.makeText(MainActivity.this,
                            "You have not entered anything.", Toast.LENGTH_SHORT).show();
                }

                if(checkMyAnswer(answer_textbox,n1,t,n2)) {
                    correct_and_incorrect_answers.add(correct_marker);
                    number_of_correct_problems += 1;
                    score_textview.setText(number_of_correct_problems + "/10 are correct");
                    Toast.makeText(MainActivity.this,
                            "Correct! Move on to the next one!!.", Toast.LENGTH_SHORT).show();
                } else {
                    correct_and_incorrect_answers.add(incorrect_marker);
                    check_answer_button.setEnabled(false);
                    Toast.makeText(MainActivity.this,
                            "Incorrect! Try again!!.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        next_problem_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              addRandomNumbersAndSymbolsToTextViews(n1,t,n2);
              answer_textbox.setText("");
              problem_number += 1;
              check_answer_button.setEnabled(true);

              if(problem_number == 10) {
                  passScoreDataToNextPageAndGoToNextPage();
              }
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void passScoreDataToNextPageAndGoToNextPage() {
        String s = score_textview.getText().toString();
        Intent i = new Intent(getApplicationContext(), MainActivity2.class);
        i.putExtra("final score",s);
        startActivity(i);
    }



    public static int randomizeFirstNumber() {

        int first_random_number = (int)(Math.random()*300) +1 ;

        return first_random_number;

    }

    public static int randomizeSecondNumber() {

        int second_random_number = (int)(Math.random()*300)+ 1;

        return second_random_number;

    }


    public static String randomizeOperation() {
        ArrayList<String> operators_list = new ArrayList<String>();
        operators_list.add("+");
        operators_list.add("-");
        Random r = new Random();
        int index = r.nextInt(2);
        return operators_list.get(index);
    }

    public static void addRandomNumbersAndSymbolsToTextViews(TextView n1, TextView t, TextView n2) {

        n1.setText(String.valueOf(randomizeFirstNumber()));
        t.setText(randomizeOperation());
        n2.setText(String.valueOf(randomizeSecondNumber()));

    }



    public static boolean checkMyAnswer(EditText t, TextView n1, TextView o, TextView n2) {


        int myanswer = Integer.valueOf(t.getText().toString());

        double correct_answer = 0;
        int number1 = Integer.valueOf(n1.getText().toString());
        int number2 = Integer.valueOf(n2.getText().toString());


        String correct_operator = String.valueOf(o.getText().toString());

        switch (correct_operator) {
            case "+":
                correct_answer = number1+number2;
                break;
            case "-":
                correct_answer = number1-number2;
                break;
        }

        return myanswer == correct_answer;

    }


}