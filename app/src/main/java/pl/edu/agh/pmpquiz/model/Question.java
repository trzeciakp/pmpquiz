package pl.edu.agh.pmpquiz.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;

/**
 *
 */
public class Question {

    @Element
    private String text;

    @Element
    private String explanation;

    @ElementList
    private List<String> answers;

    @Attribute
    private int correct;

    @Attribute
    private int id;

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Question{" +
                "text='" + text + '\'' +
                ", explanation='" + explanation + '\'' +
                ", answers=" + answers.toArray() +
                ", correctAnswer=" + correct +
                '}';
    }

    public String getText() {
        return text;
    }

    public String getExplanation() {
        return explanation;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public int getCorrect() {
        return correct;
    }
}
