package com.example.varantest.Match.Match_Tab_Layout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.paging.PagingConfig;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.varantest.Match.Userprofile_Activity;
import com.example.varantest.R;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Source;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class All_profiles_Fragment extends Fragment {


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Recycler_Paging_card_Adapter R_adaper;
    private String my_url, my_name, my_id, other_gender;
    private String uid;
    private FirebaseAuth fauth;
    private FirebaseUser mcurrentuser;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fauth = FirebaseAuth.getInstance();
        mcurrentuser = fauth.getCurrentUser();
        uid = fauth.getCurrentUser().getUid();
        CollectionReference collectionReference = db.collection("users");

        // Inflate the layout for this fragment
        Source fromCache = Source.CACHE;
        Log.d("First_Frag_Lifecycle", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_all_profiles, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.xml_recyclervview);
        progressBar = view.findViewById(R.id.xml_progressbar);

        //This is will check if user has created the proile already
        DocumentReference documentReference = db.collection("users").document(uid);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                if (task.getResult().exists()) {
                    my_url = task.getResult().getString("pic1");
                    my_name = task.getResult().getString("username");
                    my_id = task.getResult().getString("customid");
                    other_gender = task.getResult().getString("other_gender");

                }
            }
        }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {

                // The "base query" is a query with no startAt/endAt/limit clauses that the adapter can use
                // to form smaller queries for each page. It should only include where() and orderBy() clauses

                Query query = collectionReference
                        .whereEqualTo("gender",String.valueOf(other_gender));

                PagingConfig config = new PagingConfig( 2,  2, false);

                FirestorePagingOptions<Recycler_item_paging_model> r_options = new FirestorePagingOptions.Builder<Recycler_item_paging_model>()
                        .setLifecycleOwner(getViewLifecycleOwner())
                        .setQuery(query, config, Recycler_item_paging_model.class)
                        .build();


                R_adaper = new Recycler_Paging_card_Adapter(r_options);
                progressBar.setVisibility(View.INVISIBLE);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                recyclerView.setAdapter(R_adaper);
                R_adaper.startListening();

                R_adaper.setonItemclicklisterner(new Recycler_Paging_card_Adapter.single_item_click_interface() {
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
            }
        });

        return view;
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d("First_Frag_Lifecycle", "onPause");
        //this line will save the scroll position after coming back
        R_adaper.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("First_Frag_Lifecycle", "onStop");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("First_Frag_Lifecycle", "onResume");
    }

    @Override
    public void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("First_Frag_Lifecycle", "onSaveInstanceState");
        //this line will save the scroll position after coming back
        R_adaper.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);
    }

}