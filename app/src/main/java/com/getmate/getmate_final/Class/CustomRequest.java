package com.getmate.getmate_final.Class;

/**
 * Created by Dhruv on 6/28/2018.
 */
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class CustomRequest extends Request<JSONObject>{

    private Listener<JSONObject> listener;
    private Map<String, String> params;

    public CustomRequest(String url, Map<String, String> params,
                         Listener<JSONObject> reponseListener, ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
    }

    public CustomRequest(int method, String url, Map<String, String> params,
                         Listener<JSONObject> reponseListener, ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
    }

    @Override
    protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
        return params;
    };

    @Override
    protected void deliverResponse(JSONObject response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

}


/*http://localhost/getmate/v1/profile?profile={
"name":"Dhruv Gajwa",
"birthdate":1565655454554,
"handle":"Random_Nerd",
"profilePic":"some_url",
"coverPic":"some_url",
"email":"dhruvg.dakshana16@gmail.com",
"bio":"My life is dull an I don't know what to do so I am doing nothing at all",
"phone":7092541851,
"range":40,"gender":"male",
"c_lat":41.2223,
"c_lon":25.5535,
"r_lat":41.2252,
"r_lon":25.5252,
"school":["jnv shajapur","deepti convetnt school"],
"address":"chennai,India",
"work":["software developer at Cognizant"], "DOB":6466659461,
"email":"dhurvg.dakshana16@gmail.com",
"fb_url":"www.facebook.com/dhruv.gajwa",
"insta_url":"www.instagram.com/random___nerd",
"link_url":"www.linkedin.com/dhruv.gajwa" ,
"interestsB":["php","MySQL","machine learning","calligraphy"],
"interestsI":["Swimming","Running","Cycling","Android"],
"interestsE":["nothing"],"gender":"male","wordpress_url":"www.wordpress.com/randomNerd", "other_url":"www.getmate.in",
"connections":[454,121,54545,78454,5545,8546,4645,46464,8878,54126,7584545,4454754,45545,45544],
"recentActivities":[545,5468,56551,556123,54542,65121,551225,124512,4521221,65323],
"savedItems":[55454,568,6898,98556,985644,5556565,5556555,4545245,155455,455455,12232,5542325,23652],

"achievements":["killed a russel viper","touched 14 ft underwater","cycled 100 km in 4 hours"]

}//this is final JSON object u have to send along with api_key

{
    "event": "inbound",
    "msg": {
        "dkim": {},
        "email": "myemail@addresshere.co.uk",
        "from_email": "example.sender@mandrillapp.com",
        "headers": {},
        "html": "This is an example inbound message.",
        "raw_msg": "Received: from mail115.us4.mandrillapp.com",
        "sender": null,
        "spam_report": {},
        "spf": {},
        "subject": "This is an example webhook message",
        "tags": [ ],
        "template": null,
        "text": "This is an example inbound message.",
        "text_flowed": false,
        "to": [
            [
                "recipient@addresshere.co.uk",
                null
            ]
        ]
    }
}*/