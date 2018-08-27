package com.getmate.getmate_final.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.getmate.getmate_final.Class.Person;
import com.getmate.getmate_final.MainActivity;
import com.getmate.getmate_final.R;
import com.getmate.getmate_final.app.AppController;
import com.getmate.getmate_final.app.EndPoints;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    RequestQueue queue = AppController.getInstance().getRequestQueue();
    Person currentUser = new Person();
    String email;
    String password;
    String URL_LOGIN = EndPoints.LOGIN;
    String URL = "http://192.168.0.103:8080/getmate/v1/login?email=trau0@gmail.com&password=taru48kihogai";

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getApplicationContext().
                getSharedPreferences("UserLoginData",Context.MODE_PRIVATE);

        final String api_key = sharedPreferences.getString("api_key",null);
        final String email_ = sharedPreferences.getString("email",null);
        final String password_ = sharedPreferences.getString("password",null);



        if(api_key!=null){
            login(email_,password_);
        }
        setContentView(R.layout.activity_login);

        final EditText mEmail =(EditText) findViewById(R.id.email_al);
        final EditText mPassword = (EditText)findViewById(R.id.password_al);

        //register
        //
        Button signin= (Button)findViewById(R.id.sign_in_al);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(i);
            }
        });

        //login
        Button login= (Button)findViewById(R.id.login_al);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 email = mEmail.getText().toString().trim();
                 password = mPassword.getText().toString().trim();
                 login(email,password);
               // Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void login(final  String email,final String password){
        Toast.makeText(getApplicationContext(),"Login function called",Toast.LENGTH_LONG).show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Toast.makeText(getApplicationContext(),"responded",Toast.LENGTH_LONG).show();
                                               //this JSON OBJECT
                        //{ error: true/false
                        //response:in case if not successful
                        // api_key = jsdbjdsbfjsdbf,
                        // user_id = 454}
                        try {
                            boolean error = jsonObject.getBoolean("error");

                            if(error){
                                String response =jsonObject.getString("response");
                                Toast.makeText(getApplicationContext(),"login failed",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                String api_key = jsonObject.getString("api_key");
                                int user_id = jsonObject.getInt("user_id");
                                Toast.makeText(getApplicationContext(),"login success",Toast.LENGTH_SHORT).show();


                                SharedPreferences sharedPreferences = getApplicationContext()
                                        .getSharedPreferences("UserLoginData", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("api_key",api_key);
                                editor.putInt("user_id",user_id);
                                editor.apply();

                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                i.putExtra("user_id",user_id);
                                getApplicationContext().startActivity(i);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"Volley error",Toast.LENGTH_LONG).show();

            }

        }){

            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<String, String>();

                params.put("email", email);
                params.put("password", password);
                Log.e("C","getParams Called");
                Toast.makeText(getApplicationContext(),"getParam called",Toast.LENGTH_LONG).show();
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                Log.e("C","getHeaders Called");
               // Toast.makeText(getApplicationContext(),"getHeaders Called",Toast.LENGTH_LONG).show();
                return headers;
            }

        };
        queue.add(request);



    }

    public void showPopup(){

    }
}
