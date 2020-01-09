package com.swedUdolda.vkbot.notification;

import com.swedUdolda.vkbot.json.JSONHandler;

public abstract class Event {
    private final String name;

    public String getName() {
        return name;
    }

    protected Event(String name) {
        this.name = name;
    }

    public abstract String exec(JSONHandler json);

    @Override
    public String toString() { return this.name; }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Event){
            if (name.equals(((Event) obj).name)){
                return true;
            }
        }
        return false;
    }
}
