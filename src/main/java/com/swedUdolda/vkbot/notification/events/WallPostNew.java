package com.swedUdolda.vkbot.notification.events;

import com.swedUdolda.vkbot.json.JSONHandler;
import com.swedUdolda.vkbot.notification.Event;
import com.swedUdolda.vkbot.vk.VKManager;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WallPostNew extends Event {
    public WallPostNew(String name) {
        super(name);
    }

    @Override
    public String exec(JSONHandler json) {
        JSONObject vkObject = json.getVkObject();
        String text = vkObject.getString("text");
        new VKManager().sendMessage("Пришла новая запись\n" + text,98604072);
        JSONArray jsonArray = vkObject.getJSONArray("attachments");
        for(Object obj: jsonArray){
            JSONObject jsonObject = (JSONObject)obj;
            int id = jsonObject.getJSONObject("photo").getInt("id");
            int ownerId = jsonObject.getJSONObject("photo").getInt("owner_id");
            try {
                new VKManager().sendImage("",id,ownerId,98604072);
            } catch (ClientException | ApiException e) {
                e.printStackTrace();
                new VKManager().sendMessage("Не удалось отправить картинку",98604072);
            }
        }

        return System.getenv("responseStringDefault");
    }
}
