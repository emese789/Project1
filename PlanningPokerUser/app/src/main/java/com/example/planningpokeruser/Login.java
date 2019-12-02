package com.example.planningpokeruser;

import android.os.Bundle;
import android.util.Log;
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
    public Button login;
    public TextView Name,groupCode;
    public boolean session;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference adminReference = database.getReference().child("Groups");;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        login = v.findViewById(R.id.Login_btn);
        groupCode = v.findViewById(R.id.groupKod);
        Name = v.findViewById(R.id.name);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String groupid = groupCode.getText().toString();
               String uName = Name.getText().toString();
               connect(groupid,uName);
            }
        });
        return v;
    }
    private void connect(final String groupid,final String uName){
        session = false;
        adminReference = database.getReference().child("Groups");
        adminReference.child(groupid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                session = true;
                String value = dataSnapshot.getValue(String.class);
                if (dataSnapshot.exists() == false) {
                    Toast.makeText(getContext(), "Group is not exist!", Toast.LENGTH_SHORT).show();
                } else {
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    Fragment f = new Task();
                    fr.addToBackStack(null);
                    fr.replace(R.id.fragment_container, f);
                    Bundle args = new Bundle();
                    args.putString("groupKod", groupid);
                    args.putString("name",uName);
                    f.setArguments(args);
                    fr.commit();
                }
                if(session == true)
                {
                    groupCode.setError("Does not exists.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}