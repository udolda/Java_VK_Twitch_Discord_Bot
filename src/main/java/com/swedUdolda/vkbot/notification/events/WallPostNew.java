package com.swedUdolda.vkbot.notification.events;

import com.swedUdolda.vkbot.command.Commander;
import com.swedUdolda.vkbot.json.JSONHandler;
import com.swedUdolda.vkbot.notification.Event;
import com.swedUdolda.vkbot.senddiscmess.DiscordMessageSender;
import com.swedUdolda.vkbot.vk.VKManager;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
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
