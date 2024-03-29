package com.example.planningpokeradmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Form extends Fragment {
    private EditText code;
    private EditText gname;
    private Button createGroupBtn;
    private DatabaseReference db;
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_form,container,false);
        code=v.findViewById(R.id.groupCode);
        gname=v.findViewById(R.id.groupName);
        createGroupBtn=v.findViewById(R.id.createGroupButton);
        createGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String codeS=code.getText().toString();
                final String gnameS=gname.getText().toString();
                db= FirebaseDatabase.getInstance().getReference("Groups");
                db.child(codeS).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        if(dataSnapshot.exists()==true)
                        {
                            Toast.makeText(getContext() , "Group exist!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else {
                            addForm();
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

    public void addForm()
    {
        code=getView().findViewById(R.id.groupCode);
        String codeS=code.getText().toString();
        gname=getView().findViewById(R.id.groupName);
        String groupNameS=gname.getText().toString();
        db = FirebaseDatabase.getInstance().getReference("Groups");
        Groups group = new Groups(codeS,groupNameS);
        db.child(codeS).setValue(groupNameS);
        Toast.makeText(getActivity(), "GROUP IS CREATED!", Toast.LENGTH_SHORT).show();

    }



}