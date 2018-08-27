package com.getmate.getmate_final;

import com.getmate.getmate_final.Class.Person;
import com.getmate.getmate_final.data.FeedItemTimeline;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Dhruv on 6/21/2018.
 */

public  class ParseJsonResponse {



    public void parseJSONEventResponse(JSONObject response){
        //this jsonObject is response from server
        try {
            JSONArray feedArray = (JSONArray)response.getJSONArray("feed");
            for (int i = 0; i<feedArray.length();i++){
                //parse everything here
                JSONObject jsonObject = (JSONObject)feedArray.get(i);
                FeedItemTimeline item = new FeedItemTimeline();
                item.setEvent_id(jsonObject.getInt("event_id"));
                item.setCreator_id(jsonObject.getInt("creator_id"));
                item.setCreator_name(jsonObject.getString("creator_name"));
                item.setTitle(jsonObject.getString("title"));
                item.setImage(jsonObject.getString("image"));
                item.setDescription(jsonObject.getString("description"));
                item.setTime(jsonObject.getString("time"));
                item.setVenue(jsonObject.getString("venue"));
                item.setLat(jsonObject.getLong("lat"));
                //check whether Float or lONG which to use and their typecasting relation
                item.setLon(jsonObject.getLong("lon"));
                item.setUrl(jsonObject.getString("url"));
                item.setGoing_count(jsonObject.getInt("going_count"));
                item.setSaved_count(jsonObject.getInt("saved_count"));
                item.setTimeStamp(jsonObject.getString("timestamp"));

                //to get user_ids of going members
                JSONArray array = jsonObject.getJSONArray("going");
                int[] going = new int[array.length()];
                for (int j = 0;j<array.length();j++){
                    going[j] = array.optInt(j);
                }
                item.setGoing(going);

                //to get tags

                JSONArray array1= jsonObject.getJSONArray("tags");
                String[] tags = new String[array1.length()];
                for (int k =0;k<array1.length();k++){
                    tags[k] = array1.optString(k);
                }
                item.setTags(tags);

            }
//notifyDataChange(); in adapter where these data are going

        } catch (JSONException e) {
            e.printStackTrace();
        }






    }


    public Person parseJsonProfileResponse(JSONObject response){
        Person person = new Person();
        try {
            person.setName(response.getString("name"));
            //  person.setApi_key(response.getString("api_key"));
            person.setHandle(response.getString("unq_handle"));
            person.setProfilePic(response.getString("profilePic"));
            person.setCoverPic(response.getString("coverPic"));
            person.setBio(response.getString("bio"));
            person.setUser_id(response.getInt("user_id"));
            person.setRange(response.getInt("range"));
            person.setR_lat(response.getLong("lat"));
            person.setR_lon(response.getLong("lon"));

            //To set the school
            JSONArray array2 = response.getJSONArray("school");
            // String[] school = new String[array2.length()];
            ArrayList<String> school1 = new ArrayList<>();

            for (int i =0;i<array2.length();i++){
                // school[i] = array2.getString(i);
                school1.add(array2.getString(i));
            }
            person.setSchool(school1);

            //Code to fetch work details
            JSONArray array3 = response.getJSONArray("work");

            HashMap<String,String> work1 = new HashMap<>();
            for (int j =0; j<array3.length();j++){
                JSONObject work = array3.optJSONObject(j);
                //work is of form {position:software developer,company:GetMate}
                //use key value pair here
                work1.put(work.getString("position"),work.getString("company"));
            }
            // person.setWork(work1);

            person.setEmail(response.getString("email"));
            person.setPhone(response.getLong("phone"));
            person.setAddress(response.getString("address"));
            person.setFb_url(response.getString("fb_url"));
            person.setInsta_url(response.getString("insta_url"));
            person.setLink_url(response.getString("link_url"));

            //fetching interests
            JSONArray interestB = response.getJSONArray("interestsB");
            String[] ib=new String[interestB.length()];
            for (int k = 0;k<interestB.length();k++){
                ib[k] = interestB.optString(k);
            }
            // person.setInterestsB(ib);

            JSONArray interestI = response.getJSONArray("interestsI");
            String[] ii=new String[interestI.length()];
            for (int k = 0;k<interestI.length();k++){
                ii[k] = interestI.optString(k);
            }
            // person.setInterestsI(ii);
            JSONArray interestE = response.getJSONArray("interestsE");
            String[] ie=new String[interestE.length()];
            for (int k = 0;k<interestE.length();k++){
                ie[k] = interestE.optString(k);
            }
            //  person.setInterestsE(ie);

            person.setGender(response.getInt("gender"));

            //loading Connections
            JSONArray connections = response.getJSONArray("connections");
            int[] co = new int[connections.length()];
            for (int k =0;k<connections.length();k++){
                co[k] = connections.optInt(k);
            }
            person.setConnections(co);

            //loading recent activities
            JSONArray recent = response.getJSONArray("recent");
            int[] r = new int[recent.length()];
            for (int k =0;k<recent.length();k++){
                r[k]=recent.optInt(k);
            }
            person.setRecentActivities(r);

            //load acheivements
            JSONArray achvmt = response.getJSONArray("acheivements");
            String[] a= new String[achvmt.length()];
            for (int k = 0;k<achvmt.length();k++){
                a[k]=achvmt.optString(k);
            }
            //person.setAcheivemets(a);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return person;

    }

public JSONObject PersonToJson(Person p){
        JSONObject object = new JSONObject();

        //use GSON HERE
        return object;
}

public FeedItemTimeline parseEventJsonResponse(JSONObject jsonObject) throws JSONException {
    FeedItemTimeline item = new FeedItemTimeline();
    item.setEvent_id(jsonObject.getInt("event_id"));
    item.setCreator_id(jsonObject.getInt("creator_id"));
    item.setCreator_name(jsonObject.getString("creator_name"));
    item.setTitle(jsonObject.getString("title"));
    item.setImage(jsonObject.getString("image"));
    item.setDescription(jsonObject.getString("description"));
    item.setTime(jsonObject.getString("time"));
    item.setVenue(jsonObject.getString("venue"));
    item.setLat(jsonObject.getLong("lat"));
    //check whether Float or lONG which to use and their typecasting relation
    item.setLon(jsonObject.getLong("lon"));
    item.setUrl(jsonObject.getString("url"));
    item.setGoing_count(jsonObject.getInt("going_count"));
    item.setSaved_count(jsonObject.getInt("saved_count"));
    item.setTimeStamp(jsonObject.getString("timestamp"));

    //to get user_ids of going members
    JSONArray array = jsonObject.getJSONArray("going");
    int[] going = new int[array.length()];
    for (int j = 0;j<array.length();j++){
        going[j] = array.optInt(j);
    }
    item.setGoing(going);

    //to get tags

    JSONArray array1= jsonObject.getJSONArray("tags");
    String[] tags = new String[array1.length()];
    for (int k =0;k<array1.length();k++){
        tags[k] = array1.optString(k);
    }
    item.setTags(tags);
    return item;

}

}
