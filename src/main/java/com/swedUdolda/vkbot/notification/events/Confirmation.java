package com.swedUdolda.vkbot.notification.events;

import com.swedUdolda.vkbot.json.JSONHandler;
import com.swedUdolda.vkbot.notification.Event;

public class Confirmation extends Event {

    public Confirmation(String name) {
        super(name);
    }

    @Override
    public String exec(JSONHandler json) {
        return System.getenv("responseStringVk");
    }
}
