package com.getmate.getmate_final.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.getmate.getmate_final.R;
import com.getmate.getmate_final.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//need to show progress bar while loding details and also implemet Asyntask

public class SignupActivity extends AppCompatActivity {
    String email;
    String password;
    String name;
    EditText mEmail,mPassword,mName;
    String URL_REGISTER_IN_USER_TABLE = "www.myapi.com/signUp";
    RequestQueue queue = AppController.getInstance().getRequestQueue();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
          mEmail = (EditText)findViewById(R.id.email_as);
          mPassword = (EditText)findViewById(R.id.password_as);
        Button signUp = (Button)findViewById(R.id.sign_in_as);
         mName = (EditText)findViewById(R.id.name_as);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //stary prgrass bar here
                name = mName.getText().toString();
                email = mEmail.getText().toString();
                password = mPassword.getText().toString();




                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL_REGISTER_IN_USER_TABLE, null,
                        new Response.Listener<JSONObject>() {
                            boolean error = false;
                            String response = "";
                            String api_key = "";

                            @Override
                            public void onResponse(JSONObject jsonObject) {

                                try {
                                    error=jsonObject.getBoolean("error");


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if(error){
                                    try {
                                        response =jsonObject.getString("response");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                                    return;
                                }
                                else {

                                    try {
                                        api_key = jsonObject.getString("api_key");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    Intent i = new Intent(getApplicationContext(),editProfile.class);

                                    SharedPreferences sharedPreferences = getApplicationContext()
                                            .getSharedPreferences("UserLoginData", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                    if(api_key!=null){
                                        editor.putString("api_key",api_key);
                                        editor.apply();
                                    }

                                    i.putExtra("email",email);
                                    i.putExtra("name",name);
                                    getApplicationContext().startActivity(i);
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams()  {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("email", email);
                        params.put("password", password);
                        params.put("name",name);
                        return params;
                    }
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json");
                        return headers;
                    }
                };
                queue.add(jsonObjectRequest);

            }
        });



    }
}
