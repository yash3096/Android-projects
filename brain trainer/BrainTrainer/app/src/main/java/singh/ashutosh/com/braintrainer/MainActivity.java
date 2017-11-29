package singh.ashutosh.com.braintrainer;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    RelativeLayout gameRelativeLayout;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button startButton, playAgainButton;
    int locationOfCorrectAnswer;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int score = 0, numberOfQuestions = 0;
    TextView resultTextView, scoreTextView, sumTextView, timerTextView;

    public void playAgain(View view){
        score = 0;
        numberOfQuestions = 0;
        playAgainButton.setVisibility(View.INVISIBLE);
        timerTextView.setText("0s");
        scoreTextView.setText(score + "/" + numberOfQuestions);
        resultTextView.setText("");

        generateQuestion();

        new CountDownTimer(32000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText( String.valueOf((millisUntilFinished / 1000) - 1) + "s");
            }

            @Override
            public void onFinish() {
                playAgainButton.setVisibility(View.VISIBLE);
                //timerTextView.setText("0s");
                resultTextView.setText("Your score: " + score + "/" + numberOfQuestions);
            }
        }.start();
    }

    public void generateQuestion(){
        //setting the top centered horizontal text
        sumTextView = (TextView) findViewById(R.id.sumTextView);

        Random random = new Random();
        int a = random.nextInt(21);
        int b = random.nextInt(21);

        sumTextView.setText(a + " + " + b);
        answers.clear();

        locationOfCorrectAnswer = random.nextInt(4);

        int incorrectAnswer;
        for (int i = 0; i < 4; i++){
            if(i == locationOfCorrectAnswer){
                answers.add(a+b);
            }
            else{
                incorrectAnswer = random.nextInt(41);
                while (incorrectAnswer == a+b || answers.contains(incorrectAnswer)){
                    incorrectAnswer = random.nextInt(41);
                }
                answers.add(incorrectAnswer);
            }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get( 1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));

    }

    public void chooseAnswer(View view){
        //Log.i("GUTEN TAG: ", view.getTag().toString());
        //Log.i("GUTEN TAG: ", String.valueOf(locationOfCorrectAnswer));


        if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){
            score++;
            resultTextView.setText("Correct!");
        }
        else {
            resultTextView.setText("Wrong!");
        }
        numberOfQuestions++;
        scoreTextView.setText(score + "/" + numberOfQuestions);
        generateQuestion();
    }

    public void start(View view){
        startButton.setVisibility(View.INVISIBLE);
        gameRelativeLayout.setVisibility(View.VISIBLE);
        playAgain(findViewById(R.id.playAgainButton)); // any view will do.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.startButton);

        sumTextView = (TextView) findViewById(R.id.sumTextView);
        //setting 4 buttons
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        gameRelativeLayout = (RelativeLayout) findViewById(R.id.gameRelativeLayout);
    }
}