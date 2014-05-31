package pl.edu.agh.pmpquiz.guice;

import com.google.inject.Binder;
import com.google.inject.Module;
import pl.edu.agh.pmpquiz.app.QuizManager;
import pl.edu.agh.pmpquiz.app.QuizManagerXmlImpl;

/**
 *
 */
public class MyModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(QuizManager.class).to(QuizManagerXmlImpl.class);
    }
}