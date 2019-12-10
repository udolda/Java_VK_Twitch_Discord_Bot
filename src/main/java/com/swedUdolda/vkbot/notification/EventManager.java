package com.swedUdolda.vkbot.notification;

import com.swedUdolda.vkbot.notification.events.Confirmation;
import com.swedUdolda.vkbot.notification.events.NewMessage;

import java.util.HashSet;

public class EventManager {
    private static HashSet<Event> events = new HashSet<>();

    static {
        events.add(new NewMessage("message_new"));
        events.add(new Confirmation("confirmation"));
    }

    public static HashSet<Event> getEvents(){
        return events;
    }
}
