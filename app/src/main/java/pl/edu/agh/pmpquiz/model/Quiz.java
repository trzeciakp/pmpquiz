package pl.edu.agh.pmpquiz.model;

/**
 *
 */

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root
public class Quiz {
    @ElementList
    private List<Question> questions;

    public List<Question> getQuestions() {
        return questions;
    }
}
