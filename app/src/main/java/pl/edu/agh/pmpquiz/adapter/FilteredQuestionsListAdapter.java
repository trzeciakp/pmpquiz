package pl.edu.agh.pmpquiz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pl.edu.agh.pmpquiz.app.R;
import pl.edu.agh.pmpquiz.model.Question;

/**
 * Created by Marcin on 2014-05-31.
 */
public class FilteredQuestionsListAdapter extends ArrayAdapter<Question> {
    private ArrayList<Question> questions;
    private LayoutInflater layoutInflater;

    private static class ViewHolder{
        public TextView quesion;
        public TextView answer;
    }

    public FilteredQuestionsListAdapter(Context context, ArrayList<Question> questions) {
        super(context, R.layout.list_item_filtered_questions, questions);
        this.questions = questions;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.list_item_filtered_questions, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.quesion = (TextView) convertView.findViewById(R.id.question);
            viewHolder.answer = (TextView) convertView.findViewById(R.id.answer);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.quesion.setText(questions.get(position).getText().substring(0,30));
        viewHolder.answer.setText(questions.get(position).getAnswers().get(questions.get(position).getCorrect()));

        return convertView;
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public Question getItem(int position) {
        return questions.get(position);
    }
}
