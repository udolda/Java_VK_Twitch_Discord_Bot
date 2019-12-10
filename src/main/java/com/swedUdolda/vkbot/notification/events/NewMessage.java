package com.swedUdolda.vkbot.notification.events;

import com.swedUdolda.vkbot.command.Commander;
import com.swedUdolda.vkbot.json.JSONHandler;
import com.swedUdolda.vkbot.notification.Event;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewMessage extends Event {
    public NewMessage(String name) {
        super(name);
    }

    @Override
    public String exec(JSONHandler json) {
        int peerId = json.getVkObject().getInt("peer_id");
        String msg = json.getVkObject().getString("text");
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Commander(msg,peerId));
        return System.getenv("responseStringDefault");
    }
}
