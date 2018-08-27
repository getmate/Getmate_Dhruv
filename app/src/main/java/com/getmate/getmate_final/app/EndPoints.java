package com.getmate.getmate_final.app;

/**
 * Created by Dhruv on 7/16/2018.
 */

public class EndPoints {
    public static final String BASE_URL = "http://192.168.0.103:8080/getmate/v1"; //TODO: CHange this shit

    public static final String LOGIN = BASE_URL + "/login";
    public static final String USER = BASE_URL + "/user/_ID_";
    public static final String CHAT_ROOMS = BASE_URL + "/chat_rooms";
    public static final String CHAT_THREAD = BASE_URL + "/chat_rooms/_ID_";
    public static final String CHAT_ROOM_MESSAGE = BASE_URL + "/chat_rooms/_ID_/message";

}
