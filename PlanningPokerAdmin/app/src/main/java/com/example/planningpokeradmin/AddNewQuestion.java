package com.example.planningpokeradmin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class AddNewQuestion extends Fragment {
    EditText question;
    String groupId;
    Button add_btn;
    Switch aSwitch;
    List<QuestionForm> questionList;
    DatabaseReference databaseReference;
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_new_question, container, false);
        groupId = getArguments().getString("groupKod");
        Log.d("FFFF",groupId);
        question = v.findViewById(R.id.newQuestion_et);
        add_btn = v.findViewById(R.id.add_new_question);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String codeS=groupId;
                final String gnameS=question.getText().toString();
                databaseReference= FirebaseDatabase.getInstance().getReference("Questions");
                databaseReference.child(codeS).child("Questions").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        if(dataSnapshot.exists()==true)
                        {
                            Toast.makeText(getContext() , "Group exist!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else {
                            addQuestion();
                            FragmentTransaction fr=getFragmentManager().beginTransaction();
                            Fragment f = new Questions();
                            fr.addToBackStack(null);
                            fr.replace(R.id.fragment_container,f);
                            Bundle args = new Bundle();
                            args.putString("groupKod",codeS);
                            f.setArguments(args);
                            fr.commit();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        return v;
    }
    public void addQuestion(){
        String id = databaseReference.push().getKey();
        question=getView().findViewById(R.id.newQuestion_et);
        String codeS=groupId;
        aSwitch=getView().findViewById(R.id.switch2);
        String questionS=question.getText().toString();
        databaseReference= FirebaseDatabase.getInstance().getReference("Questions");
        boolean fstatus;
        if(aSwitch.isChecked()==true)
        {
            fstatus = true;
        }
        else
        {
            fstatus = false;
        }
        QuestionForm questionForm = new QuestionForm(questionS,fstatus,codeS);
        databaseReference.child(codeS).child("Question").setValue(questionS);
        Toast.makeText(getActivity(), "QUESTION IS ADDED!", Toast.LENGTH_SHORT).show();

    }
}
