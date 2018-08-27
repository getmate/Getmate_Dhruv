package com.getmate.getmate_final.Class;

import org.json.JSONArray;

import java.io.Serializable;

/**
 * Created by Dhruv on 6/19/2018.
 */
/*


{
        "title":"CLAY MODELLING",
        "description":"basic clay modelling intoduction ",
        "image_url":"www.kanekai.com/taimur ki s tesi",
        "date_time":6565646455454,
        "saved_by" :[5454,46,46,46,464,9489],
        "going":[44,46,46,64,4,87,78,78,4],
        "tags":["running","photography","cycling"],
        "venue":"rajamahal bhavan,sudarshan naka chennai",
        "creator_uid":65644,
        "going_count":55,
        "saved_count":44,
        "profilePic":"www.getmate.in/profilePic",
        "url":"www.someRegisteringUrl.com",
        "creator_name":"dhruv gajwa",
        "lat":41.5555,
        "lon":14.5555,
        "email":"dhruvgajwa008@gmail.com",
        "phone":7092541851

        }*/


//this is a POJO class for event data
    //extended to serializable to support data exchange between activities
public class event implements Serializable{
    public void setInstance(event instance) {
        this.instance = instance;
    }

    private event instance;
    Long date;
    float lat,lon;

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public JSONArray getGoing() {
        return going;
    }

    public void setGoing(JSONArray going) {
        this.going = going;
    }

    public JSONArray getSaved_by() {
        return saved_by;
    }

    public void setSaved_by(JSONArray saved_by) {
        this.saved_by = saved_by;
    }

    public JSONArray getTags() {
        return tags;
    }

    public void setTags(JSONArray tags) {
        this.tags = tags;
    }

    JSONArray going,saved_by,tags;
    private int event_id,creator_uid,going_count,saved_counts;

    Long phone;

    private String title,description,image_url,venue,c_timestamp,profilePic,url,creator_name,email; //

    public Long getDate()    {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
//here is single tagging but needed to expand to multiple

    public event(){

    }
    public event getInstance(){
        return this.instance;

    }


    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getCreator_uid() {
        return creator_uid;
    }

    public void setCreator_uid(int creator_uid) {
        this.creator_uid = creator_uid;
    }

    public int getGoing_count() {
        return going_count;
    }

    public void setGoing_count(int going_count) {
        this.going_count = going_count;
    }

    public int getSaved_counts() {
        return saved_counts;
    }

    public void setSaved_counts(int saved_counts) {
        this.saved_counts = saved_counts;
    }

    public String getCreator_name() {
        return creator_name;
    }

    public void setCreator_name(String creator_name) {
        this.creator_name = creator_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getC_timestamp() {
        return c_timestamp;
    }

    public void setC_timestamp(String c_timestamp) {
        this.c_timestamp = c_timestamp;
    }


    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
