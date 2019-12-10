package com.swedUdolda.vkbot.notification.events;

import com.swedUdolda.vkbot.json.JSONHandler;
import com.swedUdolda.vkbot.notification.Event;
import org.json.JSONObject;

public class WallPostNew extends Event {
    public WallPostNew(String name) {
        super(name);
    }

    @Override
    public String exec(JSONHandler json) {
        JSONObject vkObject = json.getVkObject();
        return System.getenv("responseStringDefault");
    }
}
