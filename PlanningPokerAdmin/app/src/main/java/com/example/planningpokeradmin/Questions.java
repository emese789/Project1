package com.example.planningpokeradmin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Questions extends Fragment {

    RecyclerView recyclerView;
    private String groupId,Id;
    private QuestionsAdapter adapter;
    private ArrayList<QuestionForm> questionList;
    private RecyclerView.LayoutManager questionLayoutManager;
    private static FirebaseDatabase dbQuestion ;
    Button addNewQuestion;
    private static DatabaseReference questionsRefernece;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_questions, container, false);
        groupId = getArguments().getString("groupKod");
        questionList = new ArrayList<>();
        recyclerView = v.findViewById(R.id.questionListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Log.d("FFFF",groupId);
        //final QuestionForm newQuestion = new QuestionForm("id","questionText",true,"groupId");
       // adapter = new QuestionsAdapter(questionList,groupId,getFragmentManager());

        dbQuestion = FirebaseDatabase.getInstance();
        questionsRefernece = dbQuestion.getReference("Question");
        questionsRefernece.child(groupId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.i("uzenet",groupId);
                        for (DataSnapshot item : dataSnapshot.getChildren()) {
                            String questionText = item.child("Question").getValue().toString();
                            Log.i("uzenet",questionText);
                            //boolean avaiable = questions.getAvailable();
                            questionList.add(new QuestionForm(questionText,true,groupId));
                            Log.i("uzenet",String.valueOf(questionList.size()));
                       }
                        adapter = new QuestionsAdapter(questionList,groupId,getFragmentManager());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        addNewQuestion = v.findViewById(R.id.add_btn);
        addNewQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                Fragment f = new AddNewQuestion();
                fr.addToBackStack(null);
                fr.replace(R.id.fragment_container, f);
                Bundle args = new Bundle();
                args.putString("groupKod", groupId);
                f.setArguments(args);
                fr.commit();

            }
        });

        return v;
    }
}


