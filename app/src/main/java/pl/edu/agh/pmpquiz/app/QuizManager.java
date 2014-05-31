package pl.edu.agh.pmpquiz.app;

import pl.edu.agh.pmpquiz.model.Question;
import pl.edu.agh.pmpquiz.model.Quiz;

/**
 *
 */
public interface QuizManager {


    Question getNextQuestion();

    Question getPreviousQuestion();

    Question getQuestion(int i);

    Quiz getQuiz();
}
