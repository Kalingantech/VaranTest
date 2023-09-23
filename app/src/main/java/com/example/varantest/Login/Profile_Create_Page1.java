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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Profile_Create_Page1 extends AppCompatActivity {

    private EditText profilename_ref, user_phoneno_ref,user_native_ref;
    private Button profile_next_btn;
    private ProgressBar horizontal_progress_bar;
    private FirebaseAuth fauth;
    private FirebaseUser mcurrentuser;
    private FirebaseFirestore db;
    private String uid;
    public String other_gender,customid;
    private Spinner creator_spinner,gender_spinner,mstatus_spinner,age_spinner,height_spinner,body_type_spinner, country_spinner, state_spinner, city_spinner;
    private String selected_creator,selected_gender,selected_mstatus,selected_age,selected_height,selected_body_type, selected_country, selected_state, selected_city;
    private ArrayAdapter<CharSequence> creator_adapter,gender_adapter,mstatus_adapter,age_adapter,height_adapter,body_type_adapter, country_adapter, state_adapter, city_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_create_page1);

        /*------------------------------------------*/
        //get email id from signup
        String emailid = getIntent().getStringExtra("emailID");
        /*------------------------------------------*/

        fauth = FirebaseAuth.getInstance();
        mcurrentuser = fauth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        uid = fauth.getCurrentUser().getUid();

        profilename_ref = findViewById(R.id.xml_profile_name);
        user_phoneno_ref = findViewById(R.id.xml_profile_phoneno);
        user_native_ref =findViewById(R.id.xml_profile_native);
        profile_next_btn = findViewById(R.id.xml_profile_next);

        //personal info spinner
        creator_spinner = findViewById(R.id.xml_created_by);
        gender_spinner = findViewById(R.id.xml_gender_check);
        age_spinner = findViewById(R.id.xml_profile_age);
        mstatus_spinner = findViewById(R.id.xml_mstatus);
        height_spinner = findViewById(R.id.xml_height);

        //location spinner
        country_spinner = findViewById(R.id.xml_country);
        state_spinner = findViewById(R.id.xml_state);
        city_spinner = findViewById(R.id.xml_city);
        body_type_spinner=findViewById(R.id.xml_body_type);

        horizontal_progress_bar = findViewById(R.id.xml_p_create_progressbar);
        horizontal_progress_bar.setVisibility(View.INVISIBLE);




        /*---------------------spinner creator--------------------------------*/
        creator_adapter = ArrayAdapter.createFromResource(this, R.array.creator_array, R.layout.spinner_layout);
        creator_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        creator_spinner.setAdapter(creator_adapter);
        creator_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_creator = creator_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        /*-------------------------spinner creator----------------------------*/

        /*---------------------spinner gender--------------------------------*/
        gender_adapter = ArrayAdapter.createFromResource(this, R.array.gender_array, R.layout.spinner_layout);
        gender_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_spinner.setAdapter(gender_adapter);
        gender_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_gender = gender_spinner.getSelectedItem().toString();
                if(selected_gender.equals("Male")){
                    other_gender = "Female";
                }else if(selected_gender.equals("Female")){
                    other_gender = "Male";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        /*-------------------------spinner gender----------------------------*/

        /*---------------------spinner age--------------------------------*/
        age_adapter = ArrayAdapter.createFromResource(this, R.array.age_array, R.layout.spinner_layout);
        age_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        age_spinner.setAdapter(age_adapter);
        age_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_age = age_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        /*-------------------------spinner age----------------------------*/


        /*---------------------spinner mstatus--------------------------------*/
        mstatus_adapter = ArrayAdapter.createFromResource(this, R.array.mstatus_array, R.layout.spinner_layout);
        mstatus_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mstatus_spinner.setAdapter(mstatus_adapter);
        mstatus_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_mstatus = mstatus_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        /*-------------------------spinner mstatus----------------------------*/




        /*---------------------spinner height--------------------------------*/
        height_adapter = ArrayAdapter.createFromResource(this, R.array.height_array, R.layout.spinner_layout);
        height_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        height_spinner.setAdapter(height_adapter);
        height_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected_height_temp = height_spinner.getSelectedItem().toString();
                //Below code will split "210 cm/6ft 11in" based on space
                String[] selected_height_array1 = selected_height_temp.split("\\s* \\s*");
                //Below code will get the first string "210" from  "210 cm/6ft 11in"
                selected_height = Collections.min(Arrays.asList(selected_height_array1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        /*-------------------------spinner height----------------------------*/


        /*---------------------spinner Body_type--------------------------------*/
        body_type_adapter = ArrayAdapter.createFromResource(this, R.array.body_type_array, R.layout.spinner_layout);
        body_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        body_type_spinner.setAdapter(body_type_adapter);
        body_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_body_type = body_type_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        /*-------------------------spinner Body_type----------------------------*/


        /*-----------------------Country_state_city_spinner------------------------------*/
        country_adapter = ArrayAdapter.createFromResource(this, R.array.array_country, R.layout.spinner_layout);
        country_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country_spinner.setAdapter(country_adapter);
        country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                state_spinner = findViewById(R.id.xml_state);

                selected_country = country_spinner.getSelectedItem().toString();      //Obtain the country

                int adapterViewID = adapterView.getId();
                if (adapterViewID == R.id.xml_country) {
                    switch (selected_country) {
                        case "Select Your country":
                            state_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                    R.array.array_default_country, R.layout.spinner_layout);
                            break;
                        case "India":
                            state_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                    R.array.array_India, R.layout.spinner_layout);
                            break;
                        case "Singapore":
                            state_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                    R.array.array_Singapore, R.layout.spinner_layout);
                            break;
                        case "Sri lanka":
                            state_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                    R.array.array_Srilanka, R.layout.spinner_layout);
                            break;
                        case "Malaysia":
                            state_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                    R.array.array_Malaysia, R.layout.spinner_layout);
                            break;
                        case "other":
                            state_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                    R.array.array_other_country, R.layout.spinner_layout);
                            break;

                        default:
                            break;
                    }
                    state_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     // Specify the layout to use when the list of choices appears
                    state_spinner.setAdapter(state_adapter);

                    state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            /*-----------------------------------------------------*/

                            city_spinner = findViewById(R.id.xml_city);

                            selected_state = state_spinner.getSelectedItem().toString();      //Obtain the selected State

                            int adapterViewID = adapterView.getId();
                            if (adapterViewID == R.id.xml_state) {
                                switch (selected_state) {
                                    case "Select Your State":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_default_districts, R.layout.spinner_layout);
                                        break;
                                    case "Andhra Pradesh":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_andhra_pradesh_districts, R.layout.spinner_layout);
                                        break;
                                    case "Arunachal Pradesh":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_arunachal_pradesh_districts, R.layout.spinner_layout);
                                        break;
                                    case "Assam":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_assam_districts, R.layout.spinner_layout);
                                        break;
                                    case "Bihar":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_bihar_districts, R.layout.spinner_layout);
                                        break;
                                    case "Chhattisgarh":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_chhattisgarh_districts, R.layout.spinner_layout);
                                        break;
                                    case "Goa":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_goa_districts, R.layout.spinner_layout);
                                        break;
                                    case "Gujarat":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_gujarat_districts, R.layout.spinner_layout);
                                        break;
                                    case "Haryana":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_haryana_districts, R.layout.spinner_layout);
                                        break;
                                    case "Himachal Pradesh":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_himachal_pradesh_districts, R.layout.spinner_layout);
                                        break;
                                    case "Jharkhand":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_jharkhand_districts, R.layout.spinner_layout);
                                        break;
                                    case "Karnataka":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_karnataka_districts, R.layout.spinner_layout);
                                        break;
                                    case "Kerala":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_kerala_districts, R.layout.spinner_layout);
                                        break;
                                    case "Madhya Pradesh":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_madhya_pradesh_districts, R.layout.spinner_layout);
                                        break;
                                    case "Maharashtra":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_maharashtra_districts, R.layout.spinner_layout);
                                        break;
                                    case "Manipur":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_manipur_districts, R.layout.spinner_layout);
                                        break;
                                    case "Meghalaya":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_meghalaya_districts, R.layout.spinner_layout);
                                        break;
                                    case "Mizoram":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_mizoram_districts, R.layout.spinner_layout);
                                        break;
                                    case "Nagaland":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_nagaland_districts, R.layout.spinner_layout);
                                        break;
                                    case "Odisha":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_odisha_districts, R.layout.spinner_layout);
                                        break;
                                    case "Punjab":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_punjab_districts, R.layout.spinner_layout);
                                        break;
                                    case "Rajasthan":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_rajasthan_districts, R.layout.spinner_layout);
                                        break;
                                    case "Sikkim":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_sikkim_districts, R.layout.spinner_layout);
                                        break;
                                    case "Tamil Nadu":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_tamil_nadu_districts, R.layout.spinner_layout);
                                        break;
                                    case "Telangana":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_telangana_districts, R.layout.spinner_layout);
                                        break;
                                    case "Tripura":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_tripura_districts, R.layout.spinner_layout);
                                        break;
                                    case "Uttar Pradesh":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_uttar_pradesh_districts, R.layout.spinner_layout);
                                        break;
                                    case "Uttarakhand":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_uttarakhand_districts, R.layout.spinner_layout);
                                        break;
                                    case "West Bengal":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_west_bengal_districts, R.layout.spinner_layout);
                                        break;
                                    case "Andaman and Nicobar Islands":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_andaman_nicobar_districts, R.layout.spinner_layout);
                                        break;
                                    case "Chandigarh":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_chandigarh_districts, R.layout.spinner_layout);
                                        break;
                                    case "Dadra and Nagar Haveli":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_dadra_nagar_haveli_districts, R.layout.spinner_layout);
                                        break;
                                    case "Daman and Diu":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_daman_diu_districts, R.layout.spinner_layout);
                                        break;
                                    case "Delhi":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_delhi_districts, R.layout.spinner_layout);
                                        break;
                                    case "Jammu and Kashmir":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_jammu_kashmir_districts, R.layout.spinner_layout);
                                        break;
                                    case "Lakshadweep":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_lakshadweep_districts, R.layout.spinner_layout);
                                        break;
                                    case "Ladakh":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_ladakh_districts, R.layout.spinner_layout);
                                        break;
                                    case "Puducherry":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_puducherry_districts, R.layout.spinner_layout);
                                        break;
                                    case "other":
                                        city_adapter = ArrayAdapter.createFromResource(adapterView.getContext(),
                                                R.array.array_other_city, R.layout.spinner_layout);
                                        break;
                                    default:
                                        break;
                                }
                                city_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     // Specify the layout to use when the list of choices appears
                                city_spinner.setAdapter(city_adapter);        //Populate the list of city in respect of the State selected

                                //To obtain the selected city from the spinner
                                city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                                        selected_city = city_spinner.getSelectedItem().toString();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                    }
                                });
                            }


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        profile_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //start uploading the data to firestore
                String name = profilename_ref.getText().toString();
                String phoneno = user_phoneno_ref.getText().toString();
                String user_native = user_native_ref.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(Profile_Create_Page1.this, "Please write your name", Toast.LENGTH_LONG).show();
                }
                if (phoneno.isEmpty()) {
                    Toast.makeText(Profile_Create_Page1.this, "Please write your phone number", Toast.LENGTH_LONG).show();
                }

                if (selected_creator.equals("Profile created by")) {
                    Toast.makeText(Profile_Create_Page1.this, "Profile created by", Toast.LENGTH_LONG).show();
                } else if (selected_gender.equals("Select gender status")) {
                    Toast.makeText(Profile_Create_Page1.this, "Select gender status", Toast.LENGTH_LONG).show();

                } else if (selected_age.equals("Select age")) {
                    Toast.makeText(Profile_Create_Page1.this, "Please select your age", Toast.LENGTH_LONG).show();
                } else if (selected_mstatus.equals("Select marriage status")) {
                    Toast.makeText(Profile_Create_Page1.this, "Please select your marriage status", Toast.LENGTH_LONG).show();
                } else if (selected_height.equals("Select height")) {
                    Toast.makeText(Profile_Create_Page1.this, "Please select your height", Toast.LENGTH_LONG).show();
                }else if (selected_body_type.equals("Select your body type")) {
                    Toast.makeText(Profile_Create_Page1.this, "Select your body type", Toast.LENGTH_LONG).show();
                }else if (selected_country.equals("Select Your country")) {
                    Toast.makeText(Profile_Create_Page1.this, "Please select your country from the list", Toast.LENGTH_LONG).show();
                } else if (selected_state.equals("Select Your State")) {
                    Toast.makeText(Profile_Create_Page1.this, "Please select your state from the list", Toast.LENGTH_LONG).show();
                } else if (selected_city.equals("Select Your District")) {
                    Toast.makeText(Profile_Create_Page1.this, "Please select your City from the list", Toast.LENGTH_LONG).show();
                } else {


                    horizontal_progress_bar.setVisibility(View.VISIBLE);
                    Date date = new Date();
                    DocumentReference user_doc = db.collection("users").document(uid);
                    //Profile_model profile = new Profile_model(uid,customid,gender, String.valueOf(downloaduri), profilename, age, phoneno);
                    Map<String, Object> user_details = new HashMap<>();

                    user_details.put("emailid", emailid);
                    user_details.put("customid", customid);
                    user_details.put("uid", uid);

                    user_details.put("creator", selected_creator);
                    user_details.put("gender", selected_gender);
                    user_details.put("other_gender", other_gender);
                    user_details.put("name", name);
                    user_details.put("age", selected_age);
                    user_details.put("mstatus", selected_mstatus);
                    user_details.put("height", selected_height);
                    user_details.put("body_type", selected_body_type);
                    user_details.put("phoneno", phoneno);

                    user_details.put("country", selected_country);
                    user_details.put("state", selected_state);
                    user_details.put("city", selected_city);
                    user_details.put("user_native", user_native);


                    //below details updated in profile create page 2
                    user_details.put("education", null);
                    user_details.put("education_details", null);
                    user_details.put("employment", null);
                    user_details.put("occupation", null);
                    user_details.put("employment_details", null);
                    user_details.put("salary", null);

                    user_details.put("nakshatram", null);
                    user_details.put("rasi", null);
                    user_details.put("dosham", null);

                    //below details updated in profile create page 3
                    user_details.put("family_status", null);
                    user_details.put("father_name", null);
                    user_details.put("father_occupation", null);
                    user_details.put("mother_name", null);
                    user_details.put("mother_occupation", null);
                    user_details.put("brothers", null);
                    user_details.put("brothers_married", null);
                    user_details.put("sisters", null);
                    user_details.put("sisters_married", null);
                    user_details.put("eating_habits", null);
                    user_details.put("drinking_habits", null);
                    user_details.put("smoking_habits", null);
//below details updated in profile create page 4
                    user_details.put("preferred_mstatus", null);
                    user_details.put("preferred_min_age", null);
                    user_details.put("preferred_max_age", null);
                    user_details.put("preferred_min_height", null);
                    user_details.put("preferred_max_height", null);
                    user_details.put("preferred_employment", null);
                    user_details.put("preferred_salary", null);
                    user_details.put("preferred_expectation", null);

//below details updated in profile create page 5
                    user_details.put("pic1", null);
                    user_details.put("pic2", null);
                    user_details.put("horoscope", null);
                    user_details.put("photo_hide", null);
//timestamps
                    user_details.put("login", date);


                    user_doc.set(user_details).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(Profile_Create_Page1.this, "Profile  upload success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Profile_Create_Page1.this, com.example.varantest.Login.Profile_Create_Page2.class));
                            finish();
                            horizontal_progress_bar.setVisibility(View.INVISIBLE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(Profile_Create_Page1.this, "Profile upload failed", Toast.LENGTH_SHORT).show();
                            horizontal_progress_bar.setVisibility(View.INVISIBLE);
                            Log.d(TAG, "" + e.toString());
                        }
                    });
                    //start uploading the data to firestore
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        //this will create new document and update custom ID & uid


        final DocumentReference customid_reff = db.collection("userid").document("id_generator");
        db.runTransaction(new Transaction.Function<Void>() {
            @Override
            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshot = transaction.get(customid_reff);
                long new_customno = snapshot.getLong("customno") + 1;
                transaction.update(customid_reff, "customno", new_customno);
                transaction.update(customid_reff, "uid", uid);
                customid = "M" + new_customno;

                /*DocumentReference user_doc = db.collection("users").document(uid);
                Map<String, Object> add_customID = new HashMap<>();
                add_customID.put("customid", customid);
                add_customID.put("uid", uid);
                user_doc.set(add_customID);*/

                return null;
            }

        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Transaction success & the custom ID is " + customid);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Transaction failure.", e);
                    }
                });
        /*--------customid-----------*/

    }
}