package com.swedUdolda.vkbot.notification.events;

import com.swedUdolda.vkbot.json.JSONHandler;
import com.swedUdolda.vkbot.notification.Event;
import com.swedUdolda.vkbot.senddiscmess.DiscordMessageSender;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WallPostNew extends Event {
    public WallPostNew(String name) {
        super(name);
    }

    @Override
    public String exec(JSONHandler json) {
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new DiscordMessageSender(json.getVkObject()));
        return System.getenv("responseStringDefault");
    }
}
