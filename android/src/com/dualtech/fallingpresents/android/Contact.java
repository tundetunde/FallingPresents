package com.dualtech.fallingpresents.android;

import android.content.Context;

/**
 * Created by tunde_000 on 13/01/2016.
 */
public class Contact {
    String score, name;
    Context context;

    Contact(String n, String score){
        this.score = score;
        name = n;
    }

    Contact(Context c, String name, String score){
        this.score = score;
        this.name = name;
        context = c;
    }

    @Override
    public String toString(){
        return name;
    }
}
