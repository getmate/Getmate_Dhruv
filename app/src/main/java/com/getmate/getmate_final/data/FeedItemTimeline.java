package com.getmate.getmate_final.data;

import android.location.Location;

import java.io.Serializable;

/**
 * Created by Dhruv on 5/27/2018.
 */

public class FeedItemTimeline implements Serializable {
    private int id,event_id,creator_id,going_count,saved_count;  //event_id

    private String name, creator_name,status, image, profilePic, timeStamp, url,description,venue,title,time;
    private String tags[];
    private int going[];
    //store the name,profile_pic of going users will be easy to see but later
    private Location location_event;
    private float lat,lon;
    //add going members and interested members
    //replace name by title,status by description,title,description,image_url,venue,c_timestamp,tag,profilePic,url,date

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(int creator_id) {
        this.creator_id = creator_id;
    }

    public int getGoing_count() {
        return going_count;
    }

    public void setGoing_count(int going_count) {
        this.going_count = going_count;
    }

    public int getSaved_count() {
        return saved_count;
    }

    public void setSaved_count(int saved_count) {
        this.saved_count = saved_count;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public int[] getGoing() {
        return going;
    }

    public void setGoing(int[] going) {
        this.going = going;
    }

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
//recreate the constructer
    public FeedItemTimeline(int id, String name, String status, String image, String profilePic, String timeStamp, String url, String description, String venue, Location location_event) {
        this.id = id;
        this.name = name;

        this.status = status;
        this.image = image;
        this.profilePic = profilePic;
        this.timeStamp = timeStamp;
        this.url = url;
        this.description = description;
        this.venue = venue;
        this.location_event = location_event;
    }
    public FeedItemTimeline(){

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getVenue() {
        return venue;
    }

    public Location getLocation_event() {
        return location_event;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setLocation_event(Location location_event) {
        this.location_event = location_event;
    }
}
