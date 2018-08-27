package com.getmate.getmate_final.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.getmate.getmate_final.R;
import com.getmate.getmate_final.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

public class getUniqueUserHandle extends AppCompatActivity {
    RequestQueue queue= AppController.getInstance().getRequestQueue();

    String s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_unique_user_handle);
        final EditText editText = (EditText)findViewById(R.id.unique_);
        Button button = (Button)findViewById(R.id.sel_btn);
        final String URL_GET_UNIQUE="some url here";


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = editText.getText().toString();

                //volley request
                JSONObject jsonObject = new JSONObject();

                try {
                   // jsonObject.put("api_key",api_key);
                    jsonObject.put("unique_handle",s);
                    //jsonObject.put("user_id",user_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, URL_GET_UNIQUE,
                        jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        boolean error=false;
                        String response="";
                        /**
                         *   jsonObject : {error:false,response:"Success"}
                         *   jsonObject :{error:true,response:"User_name already exists"}
                         *   jsonObject:{error:true,response:"Can't process request"}
                         */
                        try {
                            error = jsonObject.getBoolean("error");
                            response =jsonObject.getString("response");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(error){
                            Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                        }
                        else {
                            Intent intent = new Intent();
                             intent.putExtra("handle",s);
                             setResult(RESULT_OK,intent);
                             finish();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });

                queue.add(req); //set top priority
            }
        });
    }


}
