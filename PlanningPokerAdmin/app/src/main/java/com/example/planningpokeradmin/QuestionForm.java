package com.example.planningpokeradmin;

import androidx.fragment.app.Fragment;

public class QuestionForm extends Fragment {
    public  String id, questionText, groupName;
    boolean available;


    public QuestionForm(String questionText, boolean available, String groupName) {

        this.questionText = questionText;
        this.available = available;
        this.groupName = groupName;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getID(){
        return id;
    }

    public void SetGroupName(String groupName) {
        this.groupName = groupName;
    }

}
