package com.swedUdolda.vkbot.json;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONHandler {
    private JSONObject json;

    public JSONHandler(JSONObject json) {
        this.json = json;
    }

    public JSONHandler(String JSONString) throws JSONException {
        json = new JSONObject(JSONString);
    }

    public JSONObject getJson() {
        return json;
    }

    public String getType() throws JSONException{
        return json.getString("type");
    }

    public JSONObject getVkObject(){
        return json.getJSONObject("object");
    }

    public String get(String keyParam) throws JSONException{
        int index = keyParam.indexOf(".");
        if(index >= 0)return new JSONHandler(json.getJSONObject(keyParam.substring(0,index)))
                .get(keyParam.substring(index+1,keyParam.length()));
        else return json.getString(keyParam);
    }

    @Override
    public String toString(){
        return json.toString();
    }
}