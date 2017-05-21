package com.booking.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Cardlist {

    private String src;
    private String description;
    private String title;
    private String start;
    private JSONObject image;
    private String imageURL;

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getStart() {
        return src;
    }

    public JSONObject getImage() {
        return image;
    }

    public String getImageURL(){


               imageURL="https://visittampere.fi/media/3801af30-cd5b-11e4-b562-49633546a53f.png";

        return imageURL;
    }

}
