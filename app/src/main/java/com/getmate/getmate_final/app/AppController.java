package com.getmate.getmate_final.app;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.getmate.getmate_final.volley.LruBitmapCache;

/**
 * Created by Dhruv on 5/27/2018.
 */

public class AppController extends Application {
    public static final String TAG=AppController.class.getSimpleName();

    private  static AppController mInstance;
    public RequestQueue mRequestQueue;
    public ImageLoader imageLoader;
    LruBitmapCache mLruBitmapCache;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance= this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue==null){
            mRequestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;

    }
    public ImageLoader getImageLoader(){
        getRequestQueue();
        if(imageLoader==null){
            getLruBitmapCache();
            imageLoader= new ImageLoader(this.mRequestQueue,mLruBitmapCache);

        }
        return this.imageLoader;
    }

    public LruBitmapCache getLruBitmapCache() {
        if(mLruBitmapCache==null){
            mLruBitmapCache=new LruBitmapCache();

        }
        return this.mLruBitmapCache;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag){
        req.setTag(TextUtils.isEmpty(tag)?TAG:tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req){
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequest(Object tag){
        if(mRequestQueue!=null){
            mRequestQueue.cancelAll(tag);
        }

    }
}
