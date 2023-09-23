package com.example.varantest.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.varantest.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Profile_Create_Page3 extends AppCompatActivity {

    private Button profile_next_btn;
    private ProgressBar horizontal_progress_bar;
    private FirebaseAuth fauth;
    private FirebaseUser mcurrentuser;
    private FirebaseFirestore db;
    private EditText father_name_ref,mother_name_ref;
    private String uid;
    private Spinner family_status_spinner,father_occupation_spinner,mother_occupation_spinner,brothers_spinner,brothers_married_spinner,sisters_spinner,sisters_married_spinner,eating_habits_spinner,drinking_habits_spinner,smoking_habits_spinner;
    private String family_status_selected,father_occupation_selected,mother_occupation_selected,brothers_selected,brothers_married_selected,sisters_selected,sisters_married_selected,eating_habits_selected,drinking_habits_selected,smoking_habits_selected;
    private ArrayAdapter<CharSequence> family_status_adapter,father_occupation_adapter,mother_occupation_adapter,brothers_adapter,brothers_married_adapter,sisters_adapter,sisters_married_adapter,eating_habits_adapter,drinking_habits_adapter,smoking_habits_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_create_page3);

        profile_next_btn = findViewById(R.id.xml_profile_next);


        //spinner
        family_status_spinner = findViewById(R.id.xml_family_status);
        father_name_ref = findViewById(R.id.xml_father_name);
        father_occupation_spinner = findViewById(R.id.xml_father_occupation);
        mother_name_ref =findViewById(R.id.xml_mother_name);
        mother_occupation_spinner = findViewById(R.id.xml_mother_occupation);
        brothers_spinner = findViewById(R.id.xml_brothers);
        brothers_married_spinner = findViewById(R.id.xml_brothers_married);
        sisters_spinner = findViewById(R.id.xml_sisters);
        sisters_married_spinner = findViewById(R.id.xml_sisters_married);

        eating_habits_spinner = findViewById(R.id.xml_habits_eating);
        drinking_habits_spinner = findViewById(R.id.xml_habits_drinking);
        smoking_habits_spinner = findViewById(R.id.xml_habits_smoking);

        horizontal_progress_bar = findViewById(R.id.xml_p_create_progressbar);
        horizontal_progress_bar.setVisibility(View.INVISIBLE);

        fauth = FirebaseAuth.getInstance();
        mcurrentuser = fauth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        uid = fauth.getCurrentUser().getUid();


        horizontal_progress_bar.setVisibility(View.INVISIBLE);


        /*---------------------family_status_adapter--------------------------------*/
        family_status_adapter = ArrayAdapter.createFromResource(this, R.array.Family_status_array, R.layout.spinner_layout);
        family_status_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        family_status_spinner.setAdapter(family_status_adapter);
        family_status_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                family_status_selected = family_status_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        /*-------------------------family_status_adapter----------------------------*/


        /*---------------------father_Occupation_adapter-------------------------------*/
        father_occupation_adapter = ArrayAdapter.createFromResource(this, R.array.Father_occupation_array, R.layout.spinner_layout);
        father_occupation_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        father_occupation_spinner.setAdapter(father_occupation_adapter);
        father_occupation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                father_occupation_selected = father_occupation_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        /*-------------------------father_Occupation_adapter---------------------------*/

        /*---------------------mother_Occupation_adapter--------------------------------*/
        mother_occupation_adapter = ArrayAdapter.createFromResource(this, R.array.Mother_occupation_array, R.layout.spinner_layout);
        mother_occupation_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mother_occupation_spinner.setAdapter(mother_occupation_adapter);
        mother_occupation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mother_occupation_selected = mother_occupation_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        /*-------------------------mother_Occupation_adapter----------------------------*/

        /*---------------------brothers_adapter--------------------------------*/
        brothers_adapter = ArrayAdapter.createFromResource(this, R.array.Brothers_array, R.layout.spinner_layout);
        brothers_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brothers_spinner.setAdapter(brothers_adapter);
        brothers_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                brothers_selected = brothers_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        /*-------------------------brothers_adapter----------------------------*/

        /*---------------------brothers_married_adapter--------------------------------*/
        brothers_married_adapter = ArrayAdapter.createFromResource(this, R.array.Brothers_married_array, R.layout.spinner_layout);
        brothers_married_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brothers_married_spinner.setAdapter(brothers_married_adapter);
        brothers_married_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                brothers_married_selected = brothers_married_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        /*-------------------------brothers_married_adapter----------------------------*/

        /*---------------------sisters_adapter--------------------------------*/
        sisters_adapter = ArrayAdapter.createFromResource(this, R.array.Sisters_array, R.layout.spinner_layout);
        sisters_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sisters_spinner.setAdapter(sisters_adapter);
        sisters_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sisters_selected = sisters_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        /*-------------------------sisters_adapter----------------------------*/

        /*---------------------sisters_married_adapter--------------------------------*/
        sisters_married_adapter = ArrayAdapter.createFromResource(this, R.array.Sisters_married_array, R.layout.spinner_layout);
        sisters_married_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sisters_married_spinner.setAdapter(sisters_married_adapter);
        sisters_married_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sisters_married_selected = sisters_married_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        /*-------------------------sisters_married_adapter----------------------------*/

        /*---------------------eating_habits_adapter--------------------------------*/
        eating_habits_adapter = ArrayAdapter.createFromResource(this, R.array.Eating_habits_array, R.layout.spinner_layout);
        eating_habits_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eating_habits_spinner.setAdapter(eating_habits_adapter);
        eating_habits_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                eating_habits_selected = eating_habits_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        /*-------------------------eating_habits_adapter----------------------------*/

        /*---------------------drinking_habits_adapter--------------------------------*/
        drinking_habits_adapter = ArrayAdapter.createFromResource(this, R.array.Drinking_habits_array, R.layout.spinner_layout);
        drinking_habits_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drinking_habits_spinner.setAdapter(drinking_habits_adapter);
        drinking_habits_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                drinking_habits_selected = drinking_habits_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        /*-------------------------drinking_habits_adapter----------------------------*/

        /*---------------------smoking_habits_adapter--------------------------------*/
        smoking_habits_adapter = ArrayAdapter.createFromResource(this, R.array.Smoking_habits_array, R.layout.spinner_layout);
        smoking_habits_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        smoking_habits_spinner.setAdapter(smoking_habits_adapter);
        smoking_habits_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                smoking_habits_selected = smoking_habits_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        /*-------------------------smoking_habits_adapter----------------------------*/


        profile_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if (family_status_adapter.equals("Select your Family status")) {
                    Toast.makeText(Profile_Create_Page3.this, "Select your Family status", Toast.LENGTH_LONG).show();
                } else if (father_occupation_adapter.equals("Select your Father Occupation")) {
                    Toast.makeText(Profile_Create_Page3.this, "Select your Father Occupation", Toast.LENGTH_LONG).show();
                } else if (mother_occupation_adapter.equals("Select your Mother Occupation")) {
                    Toast.makeText(Profile_Create_Page3.this, "Select your Mother Occupation", Toast.LENGTH_LONG).show();
                } else if (brothers_adapter.equals("No of Brothers")) {
                    Toast.makeText(Profile_Create_Page3.this, "No of Brothers", Toast.LENGTH_LONG).show();
                } else if (brothers_married_adapter.equals("No of Brothers Married")) {
                    Toast.makeText(Profile_Create_Page3.this, "No of Brothers Married", Toast.LENGTH_LONG).show();
                } else if (sisters_adapter.equals("No of Sisters")) {
                    Toast.makeText(Profile_Create_Page3.this, "No of Sisters", Toast.LENGTH_LONG).show();
                }else if (sisters_married_adapter.equals("No of Sisters Married")) {
                    Toast.makeText(Profile_Create_Page3.this, "No of Sisters Married", Toast.LENGTH_LONG).show();
                }else if (eating_habits_adapter.equals("Select your Eating habits")) {
                    Toast.makeText(Profile_Create_Page3.this, "Select your Eating habits", Toast.LENGTH_LONG).show();
                }else if (drinking_habits_adapter.equals("Select your Drinking habits")) {
                    Toast.makeText(Profile_Create_Page3.this, "Select your Drinking habits", Toast.LENGTH_LONG).show();
                }else if (smoking_habits_adapter.equals("Select your Smoking habits")) {
                    Toast.makeText(Profile_Create_Page3.this, "Select your Smoking habits", Toast.LENGTH_LONG).show();
                }
                else {

                   String father_name = father_name_ref.getText().toString();
                   String mother_name = mother_name_ref.getText().toString();
                    horizontal_progress_bar.setVisibility(View.VISIBLE);

                    DocumentReference user_doc = db.collection("users").document(uid);
                    Map<String, Object> user_details = new HashMap<>();
                    user_details.put("family_status", family_status_selected);
                   user_details.put("father_name", father_name);
                   user_details.put("father_occupation", father_occupation_selected);
                   user_details.put("mother_name", mother_name);
                   user_details.put("mother_occupation", mother_occupation_selected);
                    user_details.put("brothers", brothers_selected);
                    user_details.put("brothers_married", brothers_married_selected);
                    user_details.put("sisters", sisters_selected);
                    user_details.put("sisters_married", sisters_married_selected);
                    user_details.put("eating_habits", eating_habits_selected);
                    user_details.put("drinking_habits", drinking_habits_selected);
                    user_details.put("smoking_habits", smoking_habits_selected);

                    user_doc.update(user_details).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(Profile_Create_Page3.this, "Profile  upload success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Profile_Create_Page3.this, com.example.varantest.Login.Profile_Create_Page4.class));
                            finish();
                            horizontal_progress_bar.setVisibility(View.INVISIBLE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(Profile_Create_Page3.this, "Profile upload failed", Toast.LENGTH_SHORT).show();
                            horizontal_progress_bar.setVisibility(View.INVISIBLE);
                            Log.d(TAG, "" + e.toString());
                        }
                    });
                    //start uploading the data to firestore
                }
            }
        });

    }
}