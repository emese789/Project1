package com.example.planningpokeradmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder> {

    private Context Context;
    private List<QuestionForm> QuestionList;
    private static Switch lastChecked = null;
    private static int lastCheckedPos = 0;
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference questionsReferece = database.getReference().child("Questions");
    private static String groupKod;
    private static FragmentManager context;

    public QuestionsAdapter(ArrayList<QuestionForm> questionList, String groupKod, FragmentManager context){
        this.QuestionList = questionList;
        this.groupKod = groupKod;
        this.context = context;
    }
    public QuestionsAdapter(Questions context, List<QuestionForm> questionList) {

    }

    public void AddNewItem(QuestionForm newQuestion){
        if(newQuestion != null){
            this.QuestionList.add(newQuestion);
        }
    }

    @NonNull
    @Override
    public QuestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(Context);
        View view = inflater.inflate(R.layout.recycler_view,null);
        return new QuestionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsViewHolder holder, final int position) {
        QuestionForm question = QuestionList.get(position);
        holder.questionText.setText(question.getQuestionText());
        holder.available.setChecked(QuestionList.get(position).getAvailable());
        holder.available.setTag(new Integer(position));
        if( QuestionList.get(position).getAvailable() && holder.available.isChecked()){
            lastChecked = holder.available;
            lastCheckedPos = position;
        }
        holder.available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch cb = (Switch) v;
                int clickedPos = ((Integer)cb.getTag()).intValue();

                if (cb.isChecked())
                {
                    if(lastChecked != null)
                    {
                        lastChecked.setChecked(false);
                        QuestionList.get(lastCheckedPos).setAvailable(false);
                        final String q = QuestionList.get(lastCheckedPos).questionText;
                        questionsReferece.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot item : dataSnapshot.getChildren()){
                                    if(item.child("Question").getValue().toString().equals(q)){
                                        String key = item.getKey();
                                        questionsReferece.child(key).child("active").setValue("false");
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    lastChecked = cb;
                    lastCheckedPos = clickedPos;
                }
                else
                    lastChecked = null;
                QuestionList.get(clickedPos).setAvailable(cb.isChecked());
                questionsReferece.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot item : dataSnapshot.getChildren()){
                            if(item.child("Question").getValue().toString().equals(QuestionList.get(position).questionText)){
                                String key = item.getKey();
                                if(item.child("active").getValue().toString().equals("false"))
                                    questionsReferece.child(key).child("active").setValue("true");
                                else
                                    questionsReferece.child(key).child("active").setValue("false");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return QuestionList.size();
    }

    public class QuestionsViewHolder extends RecyclerView.ViewHolder {
        public TextView questionText;
        public Switch available;
        public QuestionsViewHolder(View itemView){
            super(itemView);

            questionText = itemView.findViewById(R.id.question);
            available = itemView.findViewById(R.id.switch1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        FragmentTransaction fr = context.beginTransaction();
                        fr.addToBackStack(null);
                        Bundle args = new Bundle();
                        args.putString("Questions",questionText.getText().toString());
                        args.putString("groupKod", groupKod);
                        fr.commit();
                    }
                }
            });

        }
    }
}
