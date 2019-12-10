package com.swedUdolda.vkbot.notification;

import java.util.Collection;

public class EventDeterminate {

    public static Event getEvent(Collection<Event> events, String type) {
        for (Event event : events) {
            if (event.getName().equals(type))
                return event;
        }
        return null;
    }
}
