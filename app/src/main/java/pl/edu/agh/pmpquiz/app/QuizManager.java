package pl.edu.agh.pmpquiz.app;

import pl.edu.agh.pmpquiz.model.Question;

/**
 *
 */
public interface QuizManager {


    Question getNextQuestion();

    Question getPreviousQuestion();

    Question getQuestion(int i);
}
