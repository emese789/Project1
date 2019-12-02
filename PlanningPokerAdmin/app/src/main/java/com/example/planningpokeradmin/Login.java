package com.example.planningpokeradmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Login extends Fragment {
    public Button create, login;
    public TextView groupName,groupKod;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference adminReference;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        login = v.findViewById(R.id.Login_btn);
        create = v.findViewById(R.id.create_btn);
        groupKod = v.findViewById(R.id.groupCod_et);
        groupName = v.findViewById(R.id.groupName_et);


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new Form());
                fr.commit();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String codeS = groupKod.getText().toString();
                final String gnameS = groupName.getText().toString();
                adminReference = database.getReference().child("Groups");
                adminReference.child(codeS).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        if (dataSnapshot.exists() == false) {
                            Toast.makeText(getContext(), "Group is not exist!", Toast.LENGTH_SHORT).show();
                        } else {
                            FragmentTransaction fr = getFragmentManager().beginTransaction();
                            Fragment f = new Questions();
                            fr.addToBackStack(null);
                            fr.replace(R.id.fragment_container, f);
                            Bundle args = new Bundle();
                            args.putString("groupKod", codeS);
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
}