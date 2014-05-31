package pl.edu.agh.pmpquiz.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.pmpquiz.adapter.FilteredQuestionsListAdapter;
import pl.edu.agh.pmpquiz.model.Question;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;


public class MainActivity extends RoboActivity {

    @InjectView(R.id.questionText)
    TextView textView;

    @InjectView(R.id.answerA)
    Button answerA;
    @InjectView(R.id.answerB)
    Button answerB;
    @InjectView(R.id.answerC)
    Button answerC;
    @InjectView(R.id.answerD)
    Button answerD;

    @InjectResource(R.color.correct) private int correctColor;
    @InjectResource(R.color.incorrect) private int incorrectColor;

    private List<Button> buttons;
    private Question currentQuestion;

    @Inject
    private QuizManager quizManager;

    private boolean isAnswerChosen;

    private String[] answersLetters = new String[] { "A. ", "B. ", "C. ", "D. "};

    @Inject
    private SharedPreferences sharedPreferences;

    private static final String CURRENT_KEY = "CURRENT_QUESTION";

    private ListPopupWindow filterdQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setListeners();
        filterdQuestions = new ListPopupWindow(this);
        buttons = new ArrayList<Button>();
        buttons.add(answerA);
        buttons.add(answerB);
        buttons.add(answerC);
        buttons.add(answerD);
        int current = sharedPreferences.getInt(CURRENT_KEY, Integer.MIN_VALUE);
        Question nextQuestion = (current == Integer.MIN_VALUE ? quizManager.getNextQuestion() : quizManager.getQuestion(current));
        changeQuestion(nextQuestion);
    }

    public void filterQuiz(){
        String searchQuery = ((EditText) findViewById(R.id.filter)).getText().toString();
        ArrayList<Question> filteredQuestions = new ArrayList<Question>();

        for(Question q : quizManager.getQuiz().getQuestions()){
            if(q.getText().contains(searchQuery)){
                filteredQuestions.add(q);
            }
        }

        if(filteredQuestions.size() > 0) {
            showFilteredQuestionsInPopup(filteredQuestions);
        }
        else{
            new AlertDialog.Builder(this)
                    .setMessage("No question")
                    .setCancelable(true)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

    }

    public void showFilteredQuestionsInPopup(ArrayList<Question> questions){
        filterdQuestions.setAdapter(new FilteredQuestionsListAdapter(this, questions));
        filterdQuestions.setAnchorView(findViewById(R.id.scrollView));
        filterdQuestions.setWidth(300);
        filterdQuestions.setHeight(400);
        filterdQuestions.setModal(true);
        filterdQuestions.show();
    }

    public void setListeners(){
        ((EditText) findViewById(R.id.filter)).setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    //dont have time to do it the right way :p
                    filterQuiz();
                }
                return false;
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        sharedPreferences.edit().putInt(CURRENT_KEY, currentQuestion.getId()).apply();
    }

    public void changeQuestion(Question question){
        currentQuestion = question;
        isAnswerChosen = false;
        textView.setText(question.getId() + ". " + question.getText());
        int i = 0;
        for (String answerText : question.getAnswers()) {
            Button button = buttons.get(i);
            button.setBackgroundResource(android.R.drawable.btn_default);
            button.setText(answersLetters[i] + answerText);
            i++;
        }
    }

    public void onAnswerA(View view) {
        onButtonClicked(0);
    }

    public void onAnswerB(View view) {
        onButtonClicked(1);
    }

    public void onAnswerC(View view) {
        onButtonClicked(2);
    }
    public void onAnswerD(View view) {
        onButtonClicked(3);
    }

    private void onButtonClicked(int chosenAnswer) {
        if(isAnswerChosen){
            return;
        }
        isAnswerChosen = true;
        int correctAnswer = currentQuestion.getCorrect();
        if (chosenAnswer != correctAnswer) {
            buttons.get(chosenAnswer).setBackgroundColor(incorrectColor);
        }
        buttons.get(correctAnswer).setBackgroundColor(correctColor);
    }


    public void nextQuestion(View view) {
        Question nextQuestion = quizManager.getNextQuestion();
        changeQuestion(nextQuestion);
    }

    public void previousQuestion(View view) {
        changeQuestion(quizManager.getPreviousQuestion());
    }

    public void showExplanation(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage(currentQuestion.getExplanation())
                .setCancelable(true)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showGoToDialog(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.goTo));

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String inputText = input.getText().toString();
                if(!"".equals(inputText)) {
                    try {
                        int questionNumber = Integer.parseInt(inputText);
                        changeQuestion(quizManager.getQuestion(questionNumber));
                    } catch (NumberFormatException e) {
                        //nothing
                    }
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_explanation) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
