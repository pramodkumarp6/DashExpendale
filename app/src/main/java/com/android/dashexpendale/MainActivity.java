package com.android.dashexpendale;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.android.dashexpendale.adapter.CustomAdapter;
import com.android.dashexpendale.app.Config;
import com.android.dashexpendale.app.OkHttpSingleton;
import com.muddzdev.styleabletoast.StyleableToast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener {
    private final String TAG_REGISTER = MainActivity.class.getSimpleName();
    private static final String AUTH = "Basic " + Base64.encodeToString(("user:123456").getBytes(), Base64.NO_WRAP);
    private OkHttpClient client = new OkHttpClient();
    private CustomAdapter customAdapter;


    private EditText Email, Pass, Name, School;
    private ProgressDialog progressDialog;
    private Button b1;
    private Spinner spinnerCountry,spinnerState,spinnerCity;








    String[] State = { "Select State","Uttar Predesh", "Delhi", "Bihar", "Kasmir", "Other"};

    String[] City = { "Select City","tulsipur", "Balrampur", "Gonda", "Noida", "Other"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        List<String>countryNames  =new ArrayList<>();
        countryNames.add( 0,"Select Country");
        countryNames.add("india");
        countryNames.add("America");
        countryNames.add("Rasiya");
        countryNames.add("china");
        countryNames.add("nepal");
        countryNames.add("pakistan");


        spinnerCountry = findViewById(R.id.country);
        spinnerCountry.setPrompt("Country");



        spinnerState  =findViewById(R.id.state);
        spinnerCity =findViewById(R.id.City);


        ArrayAdapter aCountry = new ArrayAdapter(this,R.layout.spinner_item,countryNames);
        aCountry.setDropDownViewResource(R.layout.spinner_dropdown_item);


      /*  ArrayAdapter aState = new ArrayAdapter(this,android.R.layout.simple_spinner_item,State);
        aState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        ArrayAdapter aCity = new ArrayAdapter(this,android.R.layout.simple_spinner_item,City);
        aCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
*/

        spinnerCountry.setOnItemSelectedListener(this);
     //   spinnerState.setOnItemSelectedListener(this);
       // spinnerCity.setOnItemSelectedListener(this);
        //Setting the ArrayAdapter data on the Spinner
        spinnerCountry.setAdapter(aCountry);
        aCountry.setNotifyOnChange(true);
      /*  spinnerState.setAdapter(aState);
        spinnerCity.setAdapter(aCity);*/


        Email = findViewById(R.id.editTextEmail);
        Pass = findViewById(R.id.editTextPassword);
        Name = findViewById(R.id.editTextName);
        School = findViewById(R.id.editTextSchool);
        Button login = (Button) findViewById(R.id.buttonSignUp);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerUser();
            }
        });
    }

    public void registerUser() {
        final String email = Email.getText().toString().trim();

        final String password = Pass.getText().toString().trim();
        final String name = Name.getText().toString().trim();
       // final String gender = School.getText().toString().trim();



        final String gender =spinnerCountry.getSelectedItem().toString().trim();
     //   final String state =spinnerState.getSelectedItem().toString().trim();
      //  final String city = spinnerCity.getSelectedItem().toString().trim();

        if (spinnerCountry.getSelectedItem().toString().trim().equalsIgnoreCase("Select Country")) {
            Toast.makeText(this, "Select Country ", Toast.LENGTH_SHORT).show();
          //  StyleableToast.makeText(this, "Hello World!", R.style.exampleToast).show();
            return;

        }



        

        if (email.isEmpty()) {
            Email.setError("Email is required");
            Email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Enter a valid email");
            Email.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            Pass.setError("Password required");
            Pass.requestFocus();
            return;
        }

     /*   if (gender.isEmpty()) {
            School.setError("Password required");
            School.requestFocus();
            return;
        }*/









        RequestBody body =   new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .add("name",name)
               .add("gender",gender)
                .build();

        OkHttpClient localClient = OkHttpSingleton.getInstance().getClient();
        OkHttpClient localClient1 = OkHttpSingleton.getInstance().getClient();
        System.out.println(localClient.hashCode()+"papa");

        Request request = new Request.Builder().url(Config.URL_REGISTER).post(body).
                header("Authorization", AUTH).
                header("Content-Type","application/x-www-form-urlencoded").build();
                  System.out.println(request);
        Call call = localClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String resp = response.body().string();
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject (resp);
                            Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG_REGISTER, "Registration error: " + e.getMessage());

            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }





    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}