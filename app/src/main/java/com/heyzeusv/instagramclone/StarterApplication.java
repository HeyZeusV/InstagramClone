package com.heyzeusv.instagramclone;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class StarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);

        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
        .applicationId("14e96647bb395dd92c5244194589eaca68564f7f")
        .clientKey("35f27bca825457fbb2309806dc1fafe9e13eef10")
        .server("http://18.224.15.183:80/parse/")
        .build());

        ParseObject object = new ParseObject("ExampleObject");
        object.put("myNumber", "123");
        object.put("myString", "HeyZeus");

        object.saveInBackground(new SaveCallback() {

            @Override
            public void done(ParseException e) {
                if (e == null) {

                    Log.i("Parse Result", "Successful!");
                } else {

                    Log.i("Parse Result", "Failed" + e.toString());
                }
            }
        });

        ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicWriteAccess(true);
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
