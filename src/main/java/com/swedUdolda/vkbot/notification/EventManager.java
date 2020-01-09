package com.swedUdolda.vkbot.notification;

import com.swedUdolda.vkbot.notification.events.Confirmation;
import com.swedUdolda.vkbot.notification.events.WallPostNew;

import java.util.HashSet;

public class EventManager {
    private static HashSet<Event> events = new HashSet<>();

    static {
        events.add(new Confirmation("confirmation"));
        events.add(new WallPostNew("wall_post_new"));
    }

    public static HashSet<Event> getEvents(){
        return events;
    }
}
