package com.example.planningpokeradmin;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

class FirebaseDataHelper {
    public static ArrayList<QuestionForm> questions = new ArrayList<>();

    public FirebaseDataHelper() {

    }

    public static class Instance {
        static FirebaseDatabase database = FirebaseDatabase.getInstance();
        static DatabaseReference adminReference = database.getReference().child("Groups");
        static DatabaseReference questionsReference = database.getReference().child("Questions");
        static DatabaseReference answerReference = database.getReference().child("Answers");


        public static String CreateNewGroup(String groupKod,String groupName) {
            Groups group = new Groups(groupKod,groupName);
            String key = adminReference.push().getKey();
            adminReference.child(key).setValue(group);
            return key;
        }

        public static void CreateQuestions(String groupName, ArrayList<QuestionForm> questions) {
            Questions question = new Questions();
            String key = adminReference.push().getKey();
            adminReference.child(key).setValue(question);
            for (QuestionForm item : questions) {
                InsertQuestion(item, groupName);
            }
        }

        public static void InsertQuestion(QuestionForm question, String groupName) {
            String questionKey = questionsReference.push().getKey();
            question.SetGroupName(groupName);
            questionsReference.child(questionKey).setValue(question);
        }
    }

}
