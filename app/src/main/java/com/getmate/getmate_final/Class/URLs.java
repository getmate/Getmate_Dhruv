package com.getmate.getmate_final.Class;

/**
 * Created by Dhruv on 6/26/2018.
 */

public class URLs {
    String url_register = "www.myapi.com/register";
    //for registration
    //send:{email:dhruvgajwa008@gmail.com,password:P@$$w0rd#008}
    //receive:{error:false,response:successful,api_key:"f5h6hr6h4ty6ht64bt6th6rn41htntb6f4dzgxfhfgds"}

    String url_login = "www.myapi.com/login";
    //send:{api_key="g2r6tg52r66rg2trgb2",email:dhruvgawaj008@gmail.com,passwod:P@$$w0rd#008}
    //receive:{error:false,response:successful}

    String url_UniqueHandle = "www.myapi.com/UniqueHandle";
    //send:{api_key="e6tg6tr4g6tr4g6rtg6r6r6y2n6",handle:Random_Nerd}
    //receive:{isAvailable:true,response:Doesn't exist}


    String url_events = "www.myapi.com/events";
    //send:{api_key,user_id,lat,lon,range}
    //receive:[{events},{events}]


    String url_user ="www.myapi.com/user";
    //send:{user_id,api_key}
    //receive:{user_data} as per Person class


    String url_recent_events = "www.myapi.com/events";
    //send:{array of event_id}
    //receive:array of events rows

    String url_getMate = "www.myapi.com/getMate";
    //process pending

    }
