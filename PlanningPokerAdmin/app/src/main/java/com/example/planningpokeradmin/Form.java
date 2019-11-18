package com.example.planningpokeradmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Form extends Fragment {
    public EditText name,groupName;
    public Button create;
    public DatabaseReference dbform;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_form,container,false);

        name=v.findViewById(R.id.name_et);
        groupName=v.findViewById(R.id.groupName_et);
        create=v.findViewById(R.id.create_btn);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("EDMT_FIREBASE");
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addForm();
                FragmentTransaction fr=getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container,new Questions());
                fr.commit();
            }
        });
        return v;
    }
    private void addForm ()
    {

       // String id=dbform.push().getKey();

        name=getView().findViewById(R.id.name_et);
        String eName=name.getText().toString();

        groupName=getView().findViewById(R.id.groupName_et);
        String gName=groupName.getText().toString();

        Fire_Form form = new Fire_Form(eName,gName);
        //dbform.child(id).setValue(form);
        databaseReference.push().setValue(form);

        Toast.makeText(getActivity(),"Saved", Toast.LENGTH_SHORT).show();

    }

}
