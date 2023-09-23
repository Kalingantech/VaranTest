package com.example.varantest.Match.Match_Tab_Layout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.varantest.Match.Userprofile_Activity;
import com.example.varantest.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class Shortlisted extends Fragment {


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String my_url, my_name, my_id, other_gender;
    private String my_uid;
    private FirebaseAuth fauth;
    private FirebaseUser mcurrentuser;
    private Recycler_card_Adapter R_adaper;
    private String uid;
    ProgressBar progressBar;
    private RecyclerView recyclerView;
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        Log.d("second_Frag_Lifecycle", "onCreateView");
        fauth = FirebaseAuth.getInstance();
        my_uid = fauth.getCurrentUser().getUid();
        Date date = new Date();

        CollectionReference collectionReference = db.collection("shortlist").document(my_uid).collection("my_shortlist");

        progressBar = view.findViewById(R.id.xml_progressbar);
        recyclerView = view.findViewById(R.id.xml_recyclervview2);
        textView =view.findViewById(R.id.xml_listisempty);
        textView.setVisibility(View.INVISIBLE);
        //Toast.makeText(getContext(),date.toString(),Toast.LENGTH_LONG).show();
        //we are using FirestoreUI which means recycler adapter provided by Firestore. Not normal recylcer adapter.

        Query query = collectionReference
                .whereEqualTo("my_shortlist", true);


        FirestoreRecyclerOptions<Recycler_item_model> recycle_options = new FirestoreRecyclerOptions.Builder<Recycler_item_model>()
                .setQuery(query, Recycler_item_model.class)
                .build();


        R_adaper = new Recycler_card_Adapter(recycle_options){

            @Override
            public void onDataChanged() {
                if(getItemCount() == 0 ){
                    textView.setVisibility(View.VISIBLE);
                }
                progressBar.setVisibility(View.INVISIBLE);
                super.onDataChanged();
            }

        };;
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(R_adaper);
        R_adaper.startListening();

        R_adaper.setonItemclicklisterner(new Recycler_card_Adapter.single_item_click_interface() {
            @Override
            public void onitemclick(DocumentSnapshot documentSnapshot, int position) {
                Recycler_item_model item_model = documentSnapshot.toObject(Recycler_item_model.class);
                Log.d("R_cycle_loader", "position");
                String others_userid = documentSnapshot.getId();
                Intent intent = new Intent(getContext(), Userprofile_Activity.class);
                intent.putExtra("others_userid", others_userid);
                startActivity(intent);
            }
        });

        R_adaper.notifyItemInserted(0);

        return view;
    }

    public void enableThem() {
        //mProgressBar.setVisibility(View.VISIBLE);
        //mTextNoPost.setVisibility(View.VISIBLE);
    }

    public void disableThem() {
        //mProgressBar.setVisibility(View.INVISIBLE);
        //mTextNoPost.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d("First_Frag_Lifecycle", "onPause");
        //this line will save the scroll position after coming back
        R_adaper.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);
    }

    @Override
    public void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("First_Frag_Lifecycle", "onSaveInstanceState");
        //this line will save the scroll position after coming back
        R_adaper.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);
    }

}