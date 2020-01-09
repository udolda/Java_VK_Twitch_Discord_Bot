package com.swedUdolda.vkbot.senddiscmess;

import com.swedUdolda.vkbot.vk.VKManager;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

public class DiscordMessageSender implements Runnable{

    JSONObject vkObject;

    public DiscordMessageSender(JSONObject vkObject) {
        this.vkObject = vkObject;
    }

    public static void exec(String message) throws LoginException, InterruptedException {

        //логинимся за бота на сервере
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = System.getenv("discordBotToken");
        builder.setToken(token);
        JDA jda = builder.build();
        jda.awaitReady();
        jda.getTextChannelsByName("testingbot", true).get(0).sendMessage(message).queue();
    }

    @Override
    public void run() {
        String text = vkObject.getString("text");
        List<String> urlImages = new ArrayList<>();
        try {
            JSONArray jsonArray = vkObject.getJSONArray("attachments");
            System.out.println(jsonArray.length());
            int [] idArray = new int [jsonArray.length()];
            int [] ownerIdArray = new int [jsonArray.length()];
            int i = 0;
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                JSONObject jsonObjectPhoto = jsonObject.getJSONObject("photo");
                idArray[i] = jsonObjectPhoto.getInt("id");
                ownerIdArray[i] = jsonObjectPhoto.getInt("owner_id");
                i++;
                JSONArray jsonArraySizes = jsonObjectPhoto.getJSONArray("sizes");
                JSONObject jsonObjectSizesLast =  (JSONObject) jsonArraySizes.get(jsonArraySizes.length() - 1);
                urlImages.add(jsonObjectSizesLast.getString("url"));
            }
            if(jsonArray.length() > 0){
                new VKManager().sendImageList(text, idArray, ownerIdArray,98604072);
                for(int k = 0; k < idArray.length;k++){
                    System.out.println(ownerIdArray[k] + "_" + idArray[k]);
                    new VKManager().sendImage("",idArray[k],ownerIdArray[k],98604072);
                }
            }
            else
                throw new JSONException("Нет картинок");
        }
        catch(JSONException e){
            e.printStackTrace();
        } catch (ClientException | ApiException e) {
            System.out.println("Не удалось отправить картинки");
            e.printStackTrace();
        }

        for(String urlImage : urlImages){
            text += "\n" + urlImage;
        }

        try {
            exec(text);
        } catch (LoginException | InterruptedException e) {
            System.out.println("Ощибка отправки сообщения в дискорд");
            e.printStackTrace();
    }
    }
}
