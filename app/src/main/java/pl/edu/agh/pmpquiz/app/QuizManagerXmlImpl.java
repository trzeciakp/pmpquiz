package pl.edu.agh.pmpquiz.app;

import android.content.Context;
import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Provider;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.InputStream;

import pl.edu.agh.pmpquiz.model.Question;
import pl.edu.agh.pmpquiz.model.Quiz;

/**
 *
 */
public class QuizManagerXmlImpl implements QuizManager {
    private Quiz quiz;
    int current;

    Context context;

    @Inject
    public QuizManagerXmlImpl(Provider<Context> context) {
        this.context = context.get();
        Serializer serializer = new Persister();

        InputStream source = this.context.getResources().openRawResource(R.raw.quiz_raw_all);

        try {
            quiz = serializer.read(Quiz.class, source);
        } catch (Exception e) {
            Log.e("[PMPQUIZ]", "Exception while reading xml ",e);
        }

        current = quiz.getQuestions().size() - 1;
    }

    @Override
    public Question getNextQuestion() {
        int size = quiz.getQuestions().size();
        current = (current + 1) % size;
        return quiz.getQuestions().get(current);
    }

    @Override
    public Question getPreviousQuestion() {
        int size = quiz.getQuestions().size();
        current = (current + size - 1) % size;
        return quiz.getQuestions().get(current);
    }

    @Override
    public Question getQuestion(int i) {
        int size = quiz.getQuestions().size();
        current = i % size;
        return quiz.getQuestions().get(current);
    }

    @Override
    public Quiz getQuiz() {
        return quiz;
    }
}
