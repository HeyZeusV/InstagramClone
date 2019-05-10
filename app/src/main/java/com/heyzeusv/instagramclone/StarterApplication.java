package com.heyzeusv.instagramclone;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;

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

        // ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicWriteAccess(true);
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
